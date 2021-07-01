package no.ntnu.remotecode.slave;

//import util.properties packages

import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

//import simple producer packages
import no.ntnu.remotecode.slave.messaging.KFConsumer;
import no.ntnu.remotecode.slave.messaging.KFProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        List<String>             topics   = List.of("test1", "lalais", "eyeyye");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> KFProducer.run(topics), 10, TimeUnit.SECONDS);
        KFConsumer.run(topics);

        KFProducer.run(topics);
        //        System.out.println(ConsumerConfig.configDef().toEnrichedRst());
        //
        //        AdminClient
    }
}


