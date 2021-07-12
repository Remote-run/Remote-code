package no.ntnu.remotecode.slave.messaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class KafkaManager {

    private String nodeId;

    private String kafkaHostAddress;

    private ExecutorService executor = Executors.newFixedThreadPool(5);

}
