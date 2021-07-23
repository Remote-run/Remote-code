package no.ntnu.remotecode.slave.control;

import no.ntnu.remotecode.model.ComputeNode;
import no.ntnu.remotecode.model.ContainerTask;
import no.ntnu.remotecode.model.DTO.kafkamessage.TaskAck;
import no.ntnu.remotecode.model.enums.TaskStatus;
import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@KafkaConfig(bootstrapServers = "#{KAFKA_SERVICE_HOST}:#{KAFKA_SERVICE_PORT}")
public class MasterMessagingService {

    @PersistenceContext
    EntityManager entityManager;

    @Producer
    private SimpleKafkaProducer<String, String> kafkaProducer;

    public void sendTask(ContainerTask containerTask) {
        Jsonb jsonb = JsonbBuilder.create();
        kafkaProducer.send("tasks", String.valueOf(containerTask.getReceiverId()), jsonb.toJson(containerTask));
    }


    @Consumer(topics = "myTopic", groupId = "master", keyType = Integer.class)
    public void taskResponse(TaskAck taskAck) {
        int        taskId     = taskAck.getId();
        TaskStatus taskStatus = taskAck.getTaskStatus();

        Optional<ContainerTask> containerTask = Optional.ofNullable(entityManager.find(ContainerTask.class, taskId));

        containerTask.ifPresent(task -> {
            task.setTaskStatus(taskStatus);
            entityManager.persist(task);
        });
    }

    @Consumer(topics = "myTopic", groupId = "master", keyType = Integer.class)
    public void registerNewComputeNode(ComputeNode computeNode) {

    }

    @Consumer(topics = "myTopic", groupId = "master", keyType = Integer.class)
    public void registerResourceUsage() {

    }

}
