package kafka.consumer.ThreadKafkaConsumer;

import kafka.producer.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerRunnable implements Runnable {

    private final KafkaConsumer<String, User> consumers;


    public ConsumerRunnable(String brokerList, String groupId, String topic) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "100000");
        properties.put("auto.offset.reset", "earliest");//从到到位的消费和--from-beginning
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("max.poll.records", 20);
        properties.put("value.deserializer", "kafka.consumer.UserDeserializer");
        consumers = new KafkaConsumer<String, User>(properties);
        consumers.subscribe(Arrays.asList(topic));
    }


    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, User> polls = consumers.poll(1000);
            for (ConsumerRecord<String, User> poll : polls) {
                System.out.println(Thread.currentThread().getName() + " consumed: " + poll.partition() + " offset: " + poll.offset());

            }


        }


    }
}