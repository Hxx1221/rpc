package kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.NoOpConsumerRebalanceListener;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

public class ConsumerDemo {

    public static void main(String[] args) {

        String topicName = "test-topic-string";

        String groupId = "test-group";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "106.12.33.235:9092");
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");//从到到位的消费和--from-beginning
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("max.poll.records", 1000);
        KafkaConsumer<String, String> objectObjectKafkaConsumer = new KafkaConsumer<String, String>(properties);

        objectObjectKafkaConsumer.subscribe(Arrays.asList(topicName));
        //objectObjectKafkaConsumer.subscribe(Pattern.compile("test-topic-strin*"),new NoOpConsumerRebalanceListener());

        try {
            while (true) {
                /**
                 * //当consumer拿到足够多的数据时就会返回，若没有就会阻塞
                 * 而这个时间就是控制阻塞的时间
                 *
                 * */
                ConsumerRecords<String, String> polls = objectObjectKafkaConsumer.poll(1000);


                for (ConsumerRecord<String, String> poll : polls) {
                    System.out.printf("offset = %d, key = %s , value = %s%n", poll.offset(), poll.key(), poll.value());
                }
            }
        } finally {
            objectObjectKafkaConsumer.close();
        }

    }
}