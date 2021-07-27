package no.ntnu.remotecode.master.messaging;

import com.google.gson.Gson;
import no.ntnu.remotecode.model.DTO.kafkamessage.TaskAck;
import no.ntnu.remotecode.model.enums.KafkaTopic;
import no.ntnu.remotecode.model.enums.TaskStatus;

import java.util.concurrent.BlockingQueue;

public class MessageProducer extends KafkaProducerBase {

    private Gson gson;

    public MessageProducer() {
        this.gson = new Gson();
    }


    public synchronized void addAckMessage(long taskId, TaskStatus taskStatus) {
        TaskAck taskAck = new TaskAck();

        taskAck.setId(taskId);
        taskAck.setTaskStatus(taskStatus);


        super.submitMessage(KafkaTopic.TASK_ACK.name(), "-1", gson.toJson(taskAck));
    }
}
