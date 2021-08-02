package no.ntnu.remotecode.master.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Properties;


@Singleton
public class MessageSender {

    @Inject
    @ConfigProperty(name = "remote.code.kafka.adress")
    private String kafkaHostAddress;

    @Inject
    @ConfigProperty(name = "remote.code.kafka.producer.id")
    private String producerId;

    private Producer<String, String> producer;

    @PostConstruct
    private void generateProducer() {

        // create instance for properties to access producer configs
        Properties props = new Properties();

        //Assign localhost id
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaHostAddress);

        //Set acknowledgements for producer requests.
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        props.put(ProducerConfig.CLIENT_ID_CONFIG, this.producerId);

        //If the request fails, the producer can automatically retry,
        props.put(ProducerConfig.RETRIES_CONFIG, 0);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);
    }

    //    public synchronized void submitMessage(RemoteCodeMessage message) {
    //        this.producer.send(message.getAsRecord());
    //    }

    public synchronized void submitMessage(String topic, String key, String message) {
        ProducerRecord<String, String> newRecord = new ProducerRecord<String, String>(topic, key, message);
        this.producer.send(newRecord);

    }
}
