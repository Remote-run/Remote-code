package no.ntnu.remotecode.slave.messaging;

import no.ntnu.remotecode.slave.messaging.message.RemoteCodeMessage;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


/**
 * Singelton base for the cafka producer.
 */
public abstract class KafkaProducerBase {

    private KafkaProducerBase instance;

    public KafkaProducerBase getInstance() {
        if (instance == null) {
            instance = createInstance();
            instance.generateProducer();
        }
        return instance;
    }


    private String kafkaHostAddress = getKafkaHostAddress();
    private String producerId = getProducerId();

    private Producer<String, String> producer;

    abstract protected KafkaProducerBase createInstance();


    abstract protected String getKafkaHostAddress();

    abstract protected String getProducerId();


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

        //Specify buffer size in config
        //        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        //        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        //        props.put("buffer.memory", 33554432);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);
    }

    public synchronized void submitMessage(RemoteCodeMessage message) {
        this.producer.send(message.getAsRecord());
    }

    public synchronized void submitMessage(String topic, String key, String message) {
        ProducerRecord<String, String> newRecord = new ProducerRecord<String, String>(topic, key, message);
        this.producer.send(newRecord);
    }
}
