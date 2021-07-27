package no.ntnu.remotecode.master.messaging;

import no.ntnu.remotecode.master.AppConfig;
import no.ntnu.remotecode.master.DebugLogger;
import no.ntnu.remotecode.model.enums.KafkaTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class KafkaConsumerBase {

    public static DebugLogger dbl = new DebugLogger(true);

    private ExecutorService executor;
    private Map<KafkaTopic, Consumer<ConsumerRecord<String, String>>> topicHandlers;

    private String kafkaHostAddress = AppConfig.getInstance().getMasterUrl();
    private String consumerId = AppConfig.getInstance().getWorkerID();

    public KafkaConsumerBase(ExecutorService consumerTaskThreadPool) {
        this.executor = consumerTaskThreadPool;
        this.topicHandlers = new HashMap<>();

    }

    public void registerTopicHandler(KafkaTopic topic, Consumer<ConsumerRecord<String, String>> handler) {
        topicHandlers.put(topic, handler);
    }

    public void run() {
        dbl.log("Started running consumer");
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaHostAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerId);
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, consumerId);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(this.topicHandlers.keySet().stream().map(Enum::toString).collect(Collectors.toList()));
        while (true) {
            //            dbl.log("consume loop");

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            records.forEach(record -> {
                KafkaTopic recordTopic = KafkaTopic.valueOf(record.topic());
                dbl.log("Consumed topic", recordTopic);
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
