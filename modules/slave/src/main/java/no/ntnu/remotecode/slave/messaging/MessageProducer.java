package no.ntnu.remotecode.slave.messaging;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import no.ntnu.remotecode.master.model.DTO.kafkamessage.TaskAck;
import no.ntnu.remotecode.master.model.enums.KafkaTopic;
import no.ntnu.remotecode.master.model.enums.TaskStatus;

import java.util.concurrent.ExecutorService;

public class MessageProducer extends KafkaProducerBase {

    private Jsonb jsonb;

    public MessageProducer(ExecutorService executorService) {
        super(executorService);
        this.jsonb = JsonbBuilder.create();
    }


    public synchronized void addAckMessage(long taskId, TaskStatus taskStatus) {
        TaskAck taskAck = new TaskAck();

        taskAck.setId(taskId);
        taskAck.setTaskStatus(taskStatus);


        super.submitMessage(KafkaTopic.TASK_ACK.name(), "-1", jsonb.toJson(taskAck));
    }
}
