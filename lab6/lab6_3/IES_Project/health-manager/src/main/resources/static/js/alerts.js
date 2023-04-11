$(function() {
    "use strict";

    Array.prototype.remove = function() {
        let what, a = arguments, L = a.length, ax;
        while (L && this.length) {
            what = a[--L];
            while ((ax = this.indexOf(what)) !== -1) {
                this.splice(ax, 1);
            }
        }
        return this;
    };

    let stompClient = null;

    let currentNotifications = []

    let waitingQueue = {}

    const getSecondsDiff = (startDate, endDate) => {
        const msInSecond = 1000;

        return Math.round(
            Math.abs(endDate.getTime() - startDate.getTime()) / msInSecond,
        );
    }

    const sleep = s => new Promise(r => setTimeout(r, s * 1000));

    const IMMEDIATE = 0
    const WAIT = 1
    const WAIT_INTERVAL = 10
    const NEVER = 2

    const PULL_MODE = WAIT

    const waitedEnough = (date1, date2) => date1 === undefined || getSecondsDiff(date2, date1) > WAIT_INTERVAL;

    const waitForNextNotification = (patientId) => {

        if (PULL_MODE === IMMEDIATE)
            currentNotifications.remove(patientId)
        if (PULL_MODE === WAIT) {
            waitingQueue[patientId] = new Date()
            currentNotifications.remove(patientId)
        }
        if (NEVER){}
    }

    $(document).on('click', ".alert .close", function() {
        $(this).closest(".alert-container").hide();

        let patientId = $(this).attr('data-patient-id')

        waitForNextNotification(patientId)
    });

    (function connect() {
        let socket = new SockJS(`${window.location.origin}/stomp-endpoint`);
        stompClient = Stomp.over(socket);
        stompClient.debug = null
        stompClient.connect({}, function (frame) {
            // console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/alerts', function (notification) {
                let jsonNotification = JSON.parse(notification.body)
                let id = jsonNotification.id
                if (!currentNotifications.includes(id) && waitedEnough(waitingQueue[id], new Date(jsonNotification.time))) {
                    currentNotifications.push(id)
                    let notificationBlock = `
                       <div class="alert-container">
                           <div class="alert danger-alert">
                               <h3>Patient ${id} ${jsonNotification.name} in critical condition!</h3>
                               <div class="danger-btn-group">
                                   <a href="${window.location.origin}/patients/${id}" class="view-patient"></a>
                                   <a class="close" data-patient-id="${id}"></a>
                               </div>
                           </div>
                       </div>
                       `
                    delete waitingQueue[id];
                    $('.alert-holder').append(notificationBlock)
                }
            });
        });
    })()
})

