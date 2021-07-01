package no.ntnu.remotecode.slave.messaging;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KFConsumer {
    public static void run(List<String> topics) {

        String topicName = "abc-test2";

        Properties props = new Properties();

        //Assign localhost id
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "testistest-1");
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "lalalalalalalaaalala");

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        props.put("auto.commit.interval.ms", "1000");
        //props.put("session.timeout.ms", "30000");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(topics);
        int runnaway = 0;
        while (true) {
            //            System.out.println("lala");
            runnaway += 1;
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(record -> {
                System.out.printf("offset = %d, key = %s, value = %s, topic = %s, rest = %s\n",
                                  record.offset(),
                                  record.key(),
                                  record.value(),
                                  record.topic(),
                                  record.headers().toString()
                );
            });
            consumer.commitAsync();

            if (runnaway > 200) {
                break;
            }
        }
    }
}
