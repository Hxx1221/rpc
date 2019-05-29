package kafka.consumer;

import com.alibaba.fastjson.JSON;
import kafka.producer.User;
import org.apache.kafka.common.serialization.Deserializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @Author:He_xixiang
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @return ${return_type}    返回类型
 * @throws
 */
public class UserDeserializer implements Deserializer<User> {
private ObjectMapper objectMapper;

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
        objectMapper=new ObjectMapper();

    }

    @Override
    public User deserialize( String topic,  byte[] data) {
        User user=null;
        try {
           // final String s = new String(data, "utf-8");
            user=  JSON.parseObject(data,User.class);
            //user=objectMapper.readValue(data,User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void close() {

    }
}
