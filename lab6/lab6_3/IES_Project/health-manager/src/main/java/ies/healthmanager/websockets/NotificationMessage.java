package ies.healthmanager.websockets;

import lombok.Builder;
import lombok.Getter;

@Builder
public class NotificationMessage {

    @Getter
    protected MessageType type;

    @Getter
    protected String name;

    @Getter
    private Integer id;

    @Getter
    private String state;

    @Getter
    private String time;
}
