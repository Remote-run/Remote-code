package no.ntnu.remotecode.slave.messaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaManager {

    private String nodeId;

    private String kafkaHostAddress;

    private ExecutorService executor = Executors.newFixedThreadPool(5);

}
