package kafka.consumer.ThreadKafkaConsumer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerGroup {

    private List<ConsumerRunnable> consumers;

    public ConsumerGroup(int consumerNum, String groupId, String topic, String brokerList) {
        consumers = new ArrayList<ConsumerRunnable>();
        for (int i = 0; i < consumerNum; i++) {

            ConsumerRunnable consumerRunnable = new ConsumerRunnable(brokerList, groupId, topic);
            consumers.add(consumerRunnable);
        }
    }

    public void execute() {
        for (ConsumerRunnable task : consumers) {

            Thread thread = new Thread(task);
            thread.start();

        }


    }
}