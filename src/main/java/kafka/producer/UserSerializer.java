package kafka.producer;

import org.apache.kafka.common.serialization.Serializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;
//自定义序列化
public class UserSerializer implements Serializer {
    private ObjectMapper objectMapper;

    @Override
    public void configure(Map configs, boolean isKey) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        byte[] ret = null;

        try {
            ret = objectMapper.writeValueAsString(data).getBytes("UTF-8");
        } catch (IOException e) {

            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void close() {

    }
}