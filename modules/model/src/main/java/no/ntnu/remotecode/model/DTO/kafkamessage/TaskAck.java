package no.ntnu.remotecode.model.DTO.kafkamessage;

import lombok.Data;
import no.ntnu.remotecode.model.enums.TaskStatus;

@Data
public class TaskAck {

    long id;
    TaskStatus taskStatus;
}
