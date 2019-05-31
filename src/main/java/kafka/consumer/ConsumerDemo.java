package kafka.consumer;

import kafka.producer.User;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.internals.NoOpConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.regex.Pattern;

public class ConsumerDemo {

    public static void main(String[] args) {


        String topicName = "topic-hxx";

        String groupId = "test-groups";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "106.12.29.156:9092,106.12.33.235:9092,106.12.14.189:9092");
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "latest");//从到到位的消费和--from-beginning
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
       // properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("max.poll.records", 20);
        properties.put("value.deserializer", "kafka.consumer.UserDeserializer");
        KafkaConsumer<String, User> objectObjectKafkaConsumer = new KafkaConsumer<String, User>(properties);
        objectObjectKafkaConsumer.subscribe(Arrays.asList(topicName));

        final List<ConsumerRecord<String, User>> arrayList = new ArrayList();

        try {
            while (true) {
                /**
                 * //当consumer拿到足够多的数据时就会返回，若没有就会阻塞
                 * 而这个时间就是控制阻塞的时间
                 *
                 * */
                ConsumerRecords<String, User> polls = objectObjectKafkaConsumer.poll(1000);

//                for (TopicPartition topicPartition : polls.partitions()) {
//
//                    final List<ConsumerRecord<String, User>> records = polls.records(topicPartition);
//
//                    for (ConsumerRecord<String, User> record : records) {
//
//                        System.out.printf("offset = %d, key = %s , value = %s%n", record.offset(), record.key(), record.value());
//                    }
//
//                    final long offset = records.get(records.size() - 1).offset();
//
//                    objectObjectKafkaConsumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(offset + 1)));
//                    System.out.printf("topic ="+topicPartition.topic()+" partition = "+ topicPartition.partition());
//
//                    System.out.println("=====");
//
//                }


                for (ConsumerRecord<String, User> poll : polls) {
                    System.out.printf("offset = %d, key = %s , value = %s%n", poll.offset(), poll.key(), poll.value());
                    arrayList.add(poll);
                }

                if (arrayList.size()>99){
                    System.out.println("arrayList===:"+arrayList.size());
                    //objectObjectKafkaConsumer.commitAsync();//异步
                    objectObjectKafkaConsumer.commitSync();//同步
                    arrayList.clear();
                }
       //         System.out.println("=====");
            }
        } finally {
            objectObjectKafkaConsumer.close();
        }

    }
}