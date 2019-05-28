package kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerDemo {


    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "106.12.33.235:9092");

        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      //  properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks","-1");
        properties.put("retries",1);
        properties.put("batch.size",323840);
        properties.put("linger.ms",10);
        properties.put("buffer.memory",33554432);
        properties.put("max.block.ms",3000);
        properties.put("partitioner.class","kafka.producer.MyPartitioner");//用自定义分区策略
        properties.put("value.serializer","kafka.producer.UserSerializer");//用自定义序列化

        KafkaProducer<String, User> stringStringKafkaProducer = new KafkaProducer<String, User>(properties);
        for (int i = 0; i <100 ; i++) {
            User user = new User("h" + i, "xx" + i, i + 10, "wuhu" + i);

            Future<RecordMetadata> send = stringStringKafkaProducer.send(new ProducerRecord<String, User>("test-topic-demo", Integer.toString(100 + i), user));
            try {
                RecordMetadata recordMetadata = send.get();
                int partition = recordMetadata.partition();




            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        stringStringKafkaProducer.close();
    }

}