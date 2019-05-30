package kafka.consumer.ThreadKafkaConsumer;

public class ConsumerMain {

    public static void main(String[] args) {
        String brokerList = "106.12.33.235:9092";
        String groupId = "thread-group";
        String topic = "test-thread";

        int consumerNum = 3;

        ConsumerGroup consumerGroup = new ConsumerGroup(consumerNum, groupId, topic, brokerList);
        consumerGroup.execute();
    }
}