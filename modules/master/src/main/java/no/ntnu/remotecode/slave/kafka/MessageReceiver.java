package no.ntnu.remotecode.slave.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

@Singleton
@Startup
public class MessageReceiver {

    private final String kafkaHostAddress = "kafka:9092";
    private final String consumerId = "mbmrmkmgdf";

    private final List<String> topics = List.of("test");
    @Resource
    ManagedExecutorService executorService;

    @PostConstruct
    public void makeListener() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaHostAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerId);
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, consumerId);


        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(topics);

        executorService.submit(() -> {
            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                records.forEach(record -> {
                    String recordTopic = record.topic();
                    System.out.printf("topic: %s message %s", recordTopic, record.value());
                });

                consumer.commitAsync();

            }
        });
    }
}
