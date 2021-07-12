package no.ntnu.remotecode.slave.messaging.message;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface RemoteCodeMessage {

    ProducerRecord<String, String> getAsRecord();

}
