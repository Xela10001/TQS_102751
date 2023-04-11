package ies.healthmanager.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketsController {

    @MessageMapping("/notify")
    @SendTo("/topic/alerts")
    public NotificationMessage notify(@Payload final NotificationMessage notificationMessage) {
        return notificationMessage;
    }

}
