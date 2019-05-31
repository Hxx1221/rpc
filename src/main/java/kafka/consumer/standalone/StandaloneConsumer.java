package kafka.consumer.standalone;

import kafka.producer.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StandaloneConsumer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("auto.offset.reset", "latest");//从到到位的消费和--from-beginning
        String topicName = "topic-hxx";
        String groupId = "test-groups";
        properties.put("bootstrap.servers", "106.12.29.156:9092,106.12.33.235:9092,106.12.14.189:9092");
        //    properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("max.poll.records", 20);
        properties.put("value.deserializer", "kafka.consumer.UserDeserializer");

        KafkaConsumer<String, User> stringUserKafkaConsumers = new KafkaConsumer<String, User>(properties);

        List<TopicPartition> topicPartition = new ArrayList<TopicPartition>();

        List<PartitionInfo> partitionInfos = stringUserKafkaConsumers.partitionsFor(topicName);
        if (partitionInfos != null && !partitionInfos.isEmpty()) {
            for (PartitionInfo partitionInfo : partitionInfos) {

                topicPartition.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));

            }
        }
        stringUserKafkaConsumers.assign(topicPartition);
        while (true) {
            ConsumerRecords<String, User> polls = stringUserKafkaConsumers.poll(100);
            for (ConsumerRecord<String, User> poll : polls) {
                System.out.println(String.format("topic=%s,partition=%d,offset=%d,value=%d", poll.topic(), poll.partition(), poll.offset(), poll.value()));
            }
            stringUserKafkaConsumers.commitSync();
        }
    }
}