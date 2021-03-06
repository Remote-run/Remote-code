package no.ntnu.remotecode.slave.messaging;

import no.ntnu.remotecode.slave.AppConfig;
import no.ntnu.remotecode.slave.DebugLogger;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * Singelton base for the cafka producer.
 */
public class KafkaProducerBase {

    public static DebugLogger dbl = new DebugLogger(true);

    private String kafkaHostAddress = AppConfig.getInstance().getMasterUrl();
    private String producerId = AppConfig.getInstance().getWorkerID();


    public KafkaProducerBase(ExecutorService executorService) {

        executorService.submit(this::initProducer);
    }

    private final BlockingQueue<ProducerRecord<String, String>> queue = new LinkedBlockingDeque<>();


    private void initProducer() {

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

        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);

        try {
            while (true) {
                dbl.log("waiting for new message to send");
                ProducerRecord<String, String> record = queue.take();
                dbl.log("Message taken from que to send");
                producer.send(record);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected synchronized void submitMessage(String topic, String key, String message) {
        ProducerRecord<String, String> newRecord = new ProducerRecord<String, String>(topic, key, message);
        this.queue.add(newRecord);
    }
}
