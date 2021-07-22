package no.ntnu.remotecode.slave.entity;

import no.ntnu.remotecode.model.DTO.kafkamessage.ContainerTask;
import org.apache.kafka.common.serialization.Serializer;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.nio.charset.StandardCharsets;

public class MasterContainerTask extends ContainerTask {

    private static final Jsonb jsonb = JsonbBuilder.create();


    public static class ContainerTaskSerializer implements Serializer<Object> {

        /**
         * Convert {@code data} into a byte array.
         *
         * @param topic topic associated with data
         * @param data  typed data
         *
         * @return serialized bytes
         */
        @Override
        public byte[] serialize(String topic, Object data) {
            return jsonb.toJson(super).getBytes(StandardCharsets.UTF_8);
        }
    }
}
