package no.ntnu.remotecode.slave.messaging;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import no.ntnu.remotecode.master.model.enums.ContainerAction;
import no.ntnu.remotecode.master.model.enums.ContainerStatus;
import no.ntnu.remotecode.slave.DebugLogger;
import no.ntnu.remotecode.master.model.ContainerTask;
import no.ntnu.remotecode.slave.ContainerManager;
import no.ntnu.remotecode.master.model.enums.KafkaTopic;
import no.ntnu.remotecode.master.model.enums.TaskStatus;
import no.ntnu.remotecode.slave.docker.DockerContainerStatusWatcher;
import no.ntnu.remotecode.slave.docker.container.DockerContainerState;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutorService;

public class MessageReceiver extends KafkaConsumerBase {

    ContainerManager containerManager = new ContainerManager();
    DockerContainerStatusWatcher containerStatusWatcher = DockerContainerStatusWatcher.defaultInstanceInstance();

    MessageProducer messageProducer;

    public static DebugLogger dbl = new DebugLogger(true);

    private final static String producerId = "jksdhfkjsh";

    public MessageReceiver(
            ExecutorService consumerTaskThreadPool) {
        super(consumerTaskThreadPool);

        this.messageProducer = new MessageProducer(consumerTaskThreadPool);
        super.registerTopicHandler(KafkaTopic.CONTAINER_TASK, this::containerTaskMessage);

    }


    public void containerTaskMessage(ConsumerRecord<String, String> record) {
        dbl.log("New task message");
        String value = record.value();

        long taskId = -1;

        boolean suc = false;

        try {
            //Gson          gson = new Gson();
            Jsonb jsonb = JsonbBuilder.create();
            ContainerTask task = jsonb.fromJson(value, ContainerTask.class);//gson.fromJson(value, ContainerTask.class);
            taskId = task.getId();
            messageProducer.addAckMessage(taskId, TaskStatus.WORKING);

            DockerContainerState wantedState = switch (task.getAction()) {
                case START -> DockerContainerState.RUNNING;
                case STOP -> DockerContainerState.OFF;
                case DELETE -> DockerContainerState.NOT_FOUND;
            };

            suc = switch (task.getAction()) {
                case START -> containerManager.startContainer(task.getProject());
                case STOP -> containerManager.stopContainer(task.getProject());
                case DELETE -> containerManager.deleteContainer(task.getProject());
            };


            long finalTaskId = taskId;
            containerStatusWatcher.onStateChange(task.getProject().getContainerName(), dockerContainerState -> {
                dbl.log("status change");
                if (dockerContainerState == wantedState) {
                    // if the new state is running send ack saying the task is complete and stop watching
                    dbl.log("is ok sending container running");
                    messageProducer.addAckMessage(finalTaskId, TaskStatus.COMPLETE);
                    return false;
                } else {
                    return true;

                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
            dbl.log("Task message error");
        }


        if (!suc) {
            messageProducer.addAckMessage(taskId, TaskStatus.ERROR);
        }

    }
}
