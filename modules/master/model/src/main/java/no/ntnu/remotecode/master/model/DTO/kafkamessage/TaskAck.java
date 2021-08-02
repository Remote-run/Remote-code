package no.ntnu.remotecode.master.model.DTO.kafkamessage;

import lombok.Data;
import no.ntnu.remotecode.master.model.enums.TaskStatus;

@Data
public class TaskAck {

    long id;
    TaskStatus taskStatus;
}
