(function(window, document, Chartist) {
    'use strict';

    var defaultOptions = {
        labelClass: 'ct-label',
        labelOffset: {
            x: 0,
            y: -10
        },
        textAnchor: 'middle',
        align: 'center',
        labelInterpolationFnc: Chartist.noop
    };

    var labelPositionCalculation = {
        point: function(data) {
            return {
                x: data.x,
                y: data.y
            };
        },
        bar: {
            left: function(data) {
                return {
                    x: data.x1,
                    y: data.y1
                };
            },
            center: function(data) {
                return {
                    x: data.x1 + (data.x2 - data.x1) / 2,
                    y: data.y1
                };
            },
            right: function(data) {
                return {
                    x: data.x2,
                    y: data.y1
                };
            }
        }
    };

    Chartist.plugins = Chartist.plugins || {};
    Chartist.plugins.ctPointLabels = function(options) {

        options = Chartist.extend({}, defaultOptions, options);

        function addLabel(position, data) {
            // if x and y exist concat them otherwise output only the existing value
            var value = data.value.x !== undefined && data.value.y ?
                (data.value.x + ', ' + data.value.y) :
                data.value.y || data.value.x;

            data.group.elem('text', {
                x: position.x + options.labelOffset.x,
                y: position.y + options.labelOffset.y,
                style: 'text-anchor: ' + options.textAnchor
            }, options.labelClass).text(options.labelInterpolationFnc(value));
        }

        return function ctPointLabels(chart) {
            if (chart instanceof Chartist.Line || chart instanceof Chartist.Bar) {
                chart.on('draw', function(data) {
                    var positonCalculator = labelPositionCalculation[data.type] && labelPositionCalculation[data.type][options.align] || labelPositionCalculation[data.type];
                    if (positonCalculator) {
                        addLabel(positonCalculator(data), data);
                    }
                });
            }
        };
    };

}(window, document, Chartist));

$(function() {
    "use strict";

    const fetchData = (url) => {
        return fetch(url, {
            method: 'GET',
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(data) {
                return data
            })
    }

    const getStateValue = (state) => {
        switch (state) {
            case 'VERY_HEALTHY':
                return 5;
            case 'HEALTHY':
                return 4;
            case 'NEUTRAL':
                return 3;
            case 'UNHEALTHY':
                return 2;
            case 'VERY_UNHEALTHY':
                return 1
        }
    }

    /*
    * ----------------
    * MINIMAL STATS
    * ----------------
    */

    (async function MinimalStats() {
        const PiePatientUrl = `${window.location.origin}/api/v1/patients/`

        let PatientData = await fetchData(PiePatientUrl)

        let numPatients = 0;
        let numHighRiskPatients = 0;
        let oldestPatientAge = 0;
        let youngestPatientAge = Number.MAX_VALUE; // initialize youngestPatientAge to the highest possible number
        let numMalePatients = 0;
        let numFemalePatients = 0;
        let sumPatientAge = 0;

        for (let i = 0; i < PatientData.length; i++) {
            const user = PatientData[i];
            numPatients++; // increment the number of patients by 1

            if (user.age > oldestPatientAge) {
                oldestPatientAge = user.age; // update the oldest patient age if necessary
            }
            if (user.age < youngestPatientAge) {
                youngestPatientAge = user.age; // update the youngest patient age if necessary
            }
            if (user.age >= 65) {
                numHighRiskPatients++; // increment the number of high risk patients if necessary
            }
            if (user.gender === "MALE") {
                numMalePatients++; // increment the number of male patients if necessary
            }
            if (user.gender === "FEMALE") {
                numFemalePatients++; // increment the number of female patients if necessary
            }
            sumPatientAge += user.age
        }

        const averagePatientAge = (sumPatientAge / numPatients).toFixed(1)
        const maleToFemaleRatio = numMalePatients / numFemalePatients; // calculate the ratio of males to females

        $('#patient-number').text(numPatients)
        $('#high-risk-patient-number').text(numHighRiskPatients)
        $('#average-patient-age').text(averagePatientAge)
        $('#male-female-ratio').text(maleToFemaleRatio)

    })()

    /*
    * ----------------
    * PIE CHART
    * ----------------
    */

    async function PieFilter() {

        const PieUrl = `${window.location.origin}/api/v1/patients/healthStates?latestOnly=true`

        const PiePatientUrl = `${window.location.origin}/api/v1/patients/`

        let PatientData = await fetchData(PiePatientUrl)
        let HealthData = await fetchData(PieUrl)

        const values = HealthData.map(o => getStateValue(o.state))

        let undetermined = PatientData.length - values.length

        return [
            undetermined,
            values.filter(x => x < 3).length,
            values.filter(x => x === 3).length,
            values.filter(x => x > 3).length,
        ]
    }

    (() => {
        let PieElement = '#piehealth';

        PieFilter().then(response => {

            let PieData = {
                series: response
            };

            const PieColors = ['#eceff1', '#FF4848', '#FFD371', '#24d2b5'];

            let sum = function (a, b) {
                return a + b
            };

            let PieChart = new Chartist.Pie(PieElement, PieData, {
                labelInterpolationFnc: function (value) {
                    if (value > 0)
                        return Math.round(value / PieData.series.reduce(sum) * 100) + '%';
                }
            }).on('draw', function (data) {
                if (data.type !== 'slice') return;
                data.element._node.setAttribute('style', 'fill:' + PieColors[data.index]);
            });
        });

    })();


    /*
    * ----------------
    * LINE CHART
    * ----------------
    */

    let LineChart;

    const MINUTE = 0
    const HOUR = 1
    const DAY = 2
    const MONTH = 3
    const YEAR = 4

    async function LineFilter(mode = MINUTE) {

        const [lineLabels, lineDates] = getLineLabels(mode)

        const lineValues = getAggregatedLineValues(await getLineValues(lineDates))

        return [lineLabels, lineValues]
    }

    const groupValuesByPatient = (values) => {
        return values.reduce((group, state) => {
            const { patientId } = state;
            group[patientId] = group[patientId] ?? [];
            group[patientId].push(state);
            return group;
        }, {});
    }

    const getFullLineAverage = (values) => {
        let dayPatientsAverages = Object.values(values)
            .map(ObjectsByPatient => ObjectsByPatient.map(ObjectByPatient => getStateValue(ObjectByPatient.state)))
            .map(StateValuesByPatient => StateValuesByPatient.reduce((a, b) => a + b, 0) / StateValuesByPatient.length)

        return dayPatientsAverages.reduce((a, b) => a + b, 0) / dayPatientsAverages.length
    }

    Number.prototype.round = function(places) {
        return +(Math.round(parseFloat(this + "e+" + places))  + "e-" + places);
    }

    const getAggregatedLineValues = (values) => {
        return values.map(dayValues => getFullLineAverage(groupValuesByPatient(dayValues)).round(2))
    }

    const getLineValues = (jsonDates) => {
        return Promise.all(getLineUrls(jsonDates).map(async url => await fetchData(url)))
    }

    const getLineUrls = (jsonDates) => {
        let urls = []

        const LineUrl = `${window.location.origin}/api/v1/patients/healthStates?startDateTime=`
        const endDateTime = '&endDateTime='

        for (let i = 0; i < jsonDates.length; i++) {
            let startDate = jsonDates[i]
            let endDate = jsonDates?.[i + 1]

            let thisUrl = LineUrl + startDate + endDateTime + (endDate !== undefined? endDate : '')

            urls.push(thisUrl)
        }

        return urls
    }

    const getLabel = (date, mode) => {

        switch (mode) {
            case  MINUTE:
                return `${date.getHours().toString().replace(/^(\d)$/, '0$1')}h${date.getMinutes().toString().replace(/^(\d)$/, '0$1')}`
            case HOUR:
                return `${date.getHours().toString().replace(/^(\d)$/, '0$1')}h`
            case DAY:
                return date.toLocaleString(navigator.language, {
                    weekday: 'narrow',
                });
            case MONTH: {
                const SHORT_MONTHS = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

                return `${SHORT_MONTHS[date.getMonth()]} ${date.getFullYear()}`
            }
            case YEAR:
                return date.getFullYear().toString()
        }
    }

    const getPrevious = (number, date = new Date(), mode) => {
        const dateCopy = new Date(date.getTime());

        if (mode === MINUTE)
            dateCopy.setMinutes(dateCopy.getMinutes() - number);
        if (mode === HOUR)
            dateCopy.setHours(dateCopy.getHours() - number);
        if (mode === DAY)
            dateCopy.setDate(dateCopy.getDate() - number);
        if (mode === MONTH)
            dateCopy.setMonth(dateCopy.getMonth() - number);
        if (mode === YEAR)
            dateCopy.setFullYear(dateCopy.getFullYear() - number);

        return dateCopy
    }

    const dateToJSON = (date) => {
        return date.toJSON().replace(/\.\d+Z/, '');
    }

    const getLineLabels = (mode, date = new Date()) => {
        let lineLabels = []
        let lineDates = []

        if (mode === MINUTE)
            date.setSeconds(0, 0);
        if (mode === HOUR)
            date.setMinutes(0, 0, 0)
        if (mode === DAY)
            date.setHours(0, 0, 0, 0);
        if (mode === MONTH) {
            date.setHours(0, 0, 0, 0);
            date.setDate(1);
        }
        if (mode === YEAR) {
            date.setHours(0, 0, 0, 0);
            date.setMonth(0, 1)
        }

        for (let i = 5; i--;) {
            let previousDate = getPrevious(i, date, mode)
            lineLabels.push(getLabel(previousDate, mode))
            lineDates.push(dateToJSON(previousDate))
        }

        return [lineLabels, lineDates]
    }

    const adjustLastLabel = () => {
        let lastLabel = $('#linehealth').find('.ct-horizontal').last()
        let lastLabelBlock = lastLabel.parent()

        let contentWidth = lastLabel.width()
        let gridWidth = lastLabelBlock.closest('svg').width()

        lastLabelBlock.width(contentWidth)

        let currentX = lastLabelBlock.attr('x')

        let newX = gridWidth - contentWidth

        if (newX < currentX)
            lastLabelBlock.attr('x', `${newX}px`)
    }

    const updateLineData = (mode) => {

        LineFilter(mode).then(response => {

            const [LineLabels, LineValues] = response

            updateLineChart(LineLabels, LineValues)

        });

    }

    const updateLineChart = (labels, values) => {

        const data = {
            labels: labels,
            series: [values]
        }

        LineChart.update(data)
    }

    const createLineChart = (element, labels, values) => {

        const LineColor = '#20aee3'

        LineChart = new Chartist.Line(element, {
                labels: labels,
                series: [values]
            },
            {
                low: 1,
                high: 5,
                fullWidth: true,
                plugins: [
                    Chartist.plugins.ctPointLabels({
                        textAnchor: 'middle'
                    })
                ]
            })
            .on('draw', function (data) {
                data.element._node.setAttribute('style', 'stroke:' + LineColor);
            })
            .on('created', function (data) {
                adjustLastLabel()
            });
    }


    (() => {
        let LineElement = '#linehealth'

        let mode = parseInt($('.aggregate-format').val())

        LineFilter(mode).then(response => {

            const [LineLabels, LineValues] = response

            createLineChart(LineElement, LineLabels, LineValues)
        })

    })();


    $('.aggregate-format').on('change', function() {
        let mode = parseInt(this.value)
        updateLineData(mode)
    });

})