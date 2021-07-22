package no.ntnu.remotecode.model.DTO.kafkamessage;

import lombok.Data;

@Data
public class TaskAck {
    int id;
    boolean success;
}
