package no.ntnu.remotecode.master.messaging;

import no.ntnu.remotecode.master.DebugLogger;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public abstract class KafkaConsumerBase {

    public static DebugLogger dbl = new DebugLogger(true);

    private ExecutorService executor;
    private Map<String, Consumer<ConsumerRecord<String, String>>> topicHandlers;

    private String kafkaHostAddress;
    private String consumerId;

    public KafkaConsumerBase(ThreadPoolExecutor consumerTaskThreadPool, String kafkaHostAddress, String consumerId) {
        this.executor = consumerTaskThreadPool;
        this.kafkaHostAddress = kafkaHostAddress;
        this.consumerId = consumerId;
    }


    public void run() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaHostAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerId);
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, consumerId);


        //Assign localhost id
        //        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        //        props.put(ConsumerConfig.GROUP_ID_CONFIG, "testistest-1");
        //        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "lalalalalalalaaalala");

        //        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //        props.put("auto.commit.interval.ms", "1000");
        //props.put("session.timeout.ms", "30000");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(this.topicHandlers.keySet());
        while (true) {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            records.forEach(record -> {
                String recordTopic = record.topic();
                if (this.topicHandlers.containsKey(recordTopic)) {
                    this.executor.submit(() -> this.topicHandlers.get(recordTopic).accept(record));
                } else {
                    // dont think this can happen
                    System.out.print("\n\nKafaka consumer is probably misconfigured\n\n");
                }
            });

            consumer.commitAsync();

        }
    }
}
