package kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerDemo {

    //    private final static String topic="test-topic-string";
    private final static String topic = "test-thread";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "106.12.33.235:9092");

        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //   properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "-1");
        properties.put("retries", 1);
        properties.put("batch.size", 323840);
        properties.put("linger.ms", 10);
        properties.put("buffer.memory", 33554432);
        properties.put("max.block.ms", 3000);
        properties.put("min.insync.replicas", 3000);

        properties.put("partitioner.class", "kafka.producer.MyPartitioner");//用自定义分区策略
        properties.put("value.serializer", "kafka.producer.UserSerializer");//用自定义序列化
        ArrayList<String> strings = new ArrayList<String>();               //添加拦截器
        strings.add("kafka.producer.interceptor.TimeStampPrependerIntcepor");
        strings.add("kafka.producer.interceptor.CounterInterceptor");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, strings);

        // properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");//使用压缩

        final KafkaProducer<String, User> stringStringKafkaProducer = new KafkaProducer<String, User>(properties);
        for (int i = 0; i < 100; i++) {
            User user = new User("h" + i, "xx" + i, i + 10, "wuhu" + i);
            System.out.println("=====================" + i);
            stringStringKafkaProducer.send(new ProducerRecord<String, User>(topic, "audit" + i, user), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println("metadata:" + metadata);
                    System.out.println("exception:" + exception);
                    //stringStringKafkaProducer.close(0);
                }
            });
//            Future<RecordMetadata> send =
//            send.get();

        }
        stringStringKafkaProducer.close();
    }

}