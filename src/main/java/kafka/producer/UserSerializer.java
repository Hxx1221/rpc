package kafka.producer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.serialization.Serializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;
//自定义序列化
public class UserSerializer implements Serializer<User>{
    private ObjectMapper objectMapper;

    @Override
    public void configure(Map configs, boolean isKey) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, User data) {
        byte[] ret = null;

        try {
            ret= JSON.toJSONBytes(data);
          //  ret = objectMapper.writeValueAsString(data).getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void close() {

    }
}