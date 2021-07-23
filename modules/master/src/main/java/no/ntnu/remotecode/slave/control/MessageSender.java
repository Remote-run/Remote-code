package no.ntnu.remotecode.slave.control;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageSender {

    private BlockingQueue<String> messages = new LinkedBlockingQueue<>();

    public void add(String message) {
        messages.add(message);
    }

    @Outgoing("data")
    public CompletionStage<KafkaMessage<String, String>> send() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String message = messages.take();
                return KafkaMessage.of("data", "key", message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
