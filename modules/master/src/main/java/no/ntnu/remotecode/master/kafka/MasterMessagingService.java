package no.ntnu.remotecode.master.kafka;

import no.ntnu.remotecode.model.ComputeNode;
import no.ntnu.remotecode.model.ContainerTask;
import no.ntnu.remotecode.model.DTO.kafkamessage.TaskAck;
import no.ntnu.remotecode.model.enums.KafkaTopic;
import no.ntnu.remotecode.model.enums.TaskStatus;


import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


public class MasterMessagingService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    MessageSender messageSender;


    public void sendTask(ContainerTask containerTask) {
        Jsonb jsonb = JsonbBuilder.create();

        String topic = KafkaTopic.CONTAINER_TASK.name();
        String key   = String.valueOf(containerTask.getReceiverId());
        String value = jsonb.toJson(containerTask);


        messageSender.submitMessage(topic, key, value);
    }


    public void reciveTaskResponse(TaskAck taskAck) {
        int        taskId     = taskAck.getId();
        TaskStatus taskStatus = taskAck.getTaskStatus();

        Optional<ContainerTask> containerTask = Optional.ofNullable(entityManager.find(ContainerTask.class, taskId));

        containerTask.ifPresent(task -> {
            task.setTaskStatus(taskStatus);
            entityManager.persist(task);
        });
    }

    public void registerNewComputeNode(ComputeNode computeNode) {

    }

    public void registerResourceUsage() {

    }

}
