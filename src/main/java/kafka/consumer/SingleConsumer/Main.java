package kafka.consumer.SingleConsumer;

public class Main {

    public static void main(String[] args) {
        String brokerList = "106.12.29.156:9092,106.12.33.235:9092,106.12.14.189:9092";
        String groupId = "consumer-single";
        String topic = "topic-hxx";
        final ConsumerThreadHandler consumerThreadHandler = new ConsumerThreadHandler(brokerList, topic, groupId);
        final int i = Runtime.getRuntime().availableProcessors();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                consumerThreadHandler.consume(i);
            }
        };

        new Thread(runnable).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Starting to close the consumer ...");
        consumerThreadHandler.close();
    }

}