package no.ntnu.remotecode.slave.control;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import no.ntnu.remotecode.model.DTO.kafkamessage.ContainerTask;
import no.ntnu.remotecode.model.DTO.kafkamessage.TaskAck;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

public class MasterMessagingService {

    @Outgoing("aaa")
    public KafkaRecord<String, ContainerTask> sendContainerTask(String key, ContainerTask task) {
        return KafkaRecord.of(key, task);
    }


    public TaskAck taskResponse() {

    }
}
