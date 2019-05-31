package kafka.consumer.ThreadKafkaConsumer;

public class ConsumerMain {

    public static void main(String[] args) {
        String brokerList = "106.12.29.156:9092,106.12.33.235:9092,106.12.14.189:9092";
        String groupId = "thread-group-new";
        String topic = "topic-hxx";

        int consumerNum = 3;

        ConsumerGroup consumerGroup = new ConsumerGroup(consumerNum, groupId, topic, brokerList);
        consumerGroup.execute();
    }
}