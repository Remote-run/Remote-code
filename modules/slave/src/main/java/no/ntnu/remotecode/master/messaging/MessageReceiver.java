package no.ntnu.remotecode.master.messaging;

import com.google.gson.Gson;
import no.ntnu.remotecode.master.DebugLogger;
import no.ntnu.remotecode.model.ContainerTask;
import no.ntnu.remotecode.master.ContainerManager;
import no.ntnu.remotecode.model.enums.KafkaTopic;
import no.ntnu.remotecode.model.enums.TaskStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class MessageReceiver extends KafkaConsumerBase {

    ContainerManager containerManager = new ContainerManager();

    MessageProducer messageProducer;

    public static DebugLogger dbl = new DebugLogger(true);

    private final static String producerId = "jksdhfkjsh";

    public MessageReceiver(
            ExecutorService consumerTaskThreadPool) {
        super(consumerTaskThreadPool);

        this.messageProducer = new MessageProducer();
        super.registerTopicHandler(KafkaTopic.CONTAINER_TASK, this::containerTaskMessage);

    }


    public void containerTaskMessage(ConsumerRecord<String, String> record) {
        dbl.log("New task message");
        String value = record.value();

        long taskId = -1;

        boolean suc = false;

        try {
            Gson          gson = new Gson();
            ContainerTask task = gson.fromJson(value, ContainerTask.class);
            taskId = task.getId();
            messageProducer.addAckMessage(taskId, TaskStatus.WORKING);


            suc = switch (task.getAction()) {
                case START -> containerManager.startContainer(task.getProject());
                case STOP -> containerManager.stopContainer(task.getProject());
                case DELETE -> containerManager.deleteContainer(task.getProject());
            };
        } catch (Exception ignore) {
            ignore.printStackTrace();
            dbl.log("Task message error");
        }


        if (suc) {
            messageProducer.addAckMessage(taskId, TaskStatus.COMPLETE);
        } else {
            messageProducer.addAckMessage(taskId, TaskStatus.ERROR);
        }

    }
}
