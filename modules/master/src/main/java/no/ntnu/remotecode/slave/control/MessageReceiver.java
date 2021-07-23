package no.ntnu.remotecode.slave.control;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import io.smallrye.reactive.messaging.kafka.MessageHeaders;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

public class MessageReceiver {

    private static final Logger LOGGER = Logger.getLogger(MessageReceiver.class.getName());

    @Incoming("kafka")
    public CompletionStage<Void> consume(KafkaMessage<String, String> message) {
        String         payload   = message.getPayload();
        String         key       = message.getKey();
        MessageHeaders headers   = message.getHeaders();
        Integer        partition = message.getPartition();
        Long           timestamp = message.getTimestamp();
        LOGGER.info("received: " + payload + " from topic " + message.getTopic());
        return message.ack();
    }
}
