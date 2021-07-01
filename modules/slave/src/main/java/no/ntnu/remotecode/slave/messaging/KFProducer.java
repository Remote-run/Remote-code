package no.ntnu.remotecode.slave.messaging;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class KFProducer {
    public static void run(List<String> topics) {
        // Check arguments length value
        //        if (args.length == 0) {
        //            System.out.println("Enter topic name");
        //            return;
        //        }

        //        Thread.currentThread().setContextClassLoader(null);
        //Assign topicName to string variable
        String topicName = "abc-test2";

        // create instance for properties to access producer configs
        Properties props = new Properties();

        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9093");

        //Set acknowledgements for producer requests.
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client1");

        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        //Specify buffer size in config
        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //        props.put("key.serializer",
        //                  Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        //        );
        //
        //        props.put("value.serializer",
        //                  Class.forName("org.apache.kafka.common.serialization.StringSerializer")
        //        );
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer
                <String, String>(props);

        topics.forEach(s -> {
            for (int i = 0; i < 10; i++) {
                producer.send(new ProducerRecord<String, String>(s,
                                                                 "ts" + UUID.randomUUID().toString(),
                                                                 Instant.now().toString()
                ), (metadata, exception) -> {System.out.println("sucsess " + metadata.toString());});
            }
        });
        System.out.println("Message sent successfully");
        producer.close();
    }
}
