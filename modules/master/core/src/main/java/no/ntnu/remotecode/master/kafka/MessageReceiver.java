package no.ntnu.remotecode.master.kafka;


import no.ntnu.remotecode.master.control.KafkaReceiverService;
import no.ntnu.remotecode.master.model.ContainerTask;
import no.ntnu.remotecode.master.model.DTO.kafkamessage.TaskAck;
import no.ntnu.remotecode.master.model.enums.KafkaTopic;
import no.ntnu.remotecode.master.model.enums.TaskStatus;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Singleton
@Startup
public class MessageReceiver {

    @ConfigProperty(name = "remote.code.kafka.adress")
    private String kafkaHostAddress;

    @ConfigProperty(name = "remote.code.kafka.consumer.id")
    private String consumerId;

    @Resource
    ManagedExecutorService executorService;


    @Inject
    KafkaReceiverService receiverService;


    private final Map<KafkaTopic, Consumer<ConsumerRecord<String, String>>> topicHandlers = new HashMap<>();


    public void startListening() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaHostAddress);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerId);
        //        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());

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
                if (this.topicHandlers.containsKey(recordTopic)) {
                    this.executorService.submit(() -> this.topicHandlers.get(recordTopic).accept(record));
                } else {
                    // dont think this can happen
                    System.out.print("\n\nKafaka consumer is probably misconfigured\n\n");
                }
            });

            consumer.commitAsync();

        }
    }

    @PostConstruct
    public void init() {
        topicHandlers.put(KafkaTopic.TASK_ACK, this::handleAckMessage);


        executorService.submit(this::startListening);

    }

    public void handleAckMessage(ConsumerRecord<String, String> record) {
        String value = record.value();

        Jsonb   jsonb   = JsonbBuilder.create();
        TaskAck taskAck = jsonb.fromJson(value, TaskAck.class);

        receiverService.handleAckMessage(taskAck);
    }


}
