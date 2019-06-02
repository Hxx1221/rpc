package kafka.consumer.SingleConsumer;

import kafka.producer.User;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConsumerThreadHandler {

    private final KafkaConsumer<String, User> kafkaConsumer;
    private ExecutorService executorServices;
    private final Map<TopicPartition, OffsetAndMetadata> offsetAndMetadataMap = new HashMap<TopicPartition, OffsetAndMetadata>();


    public ConsumerThreadHandler(String brokerList, String groupId, String topic) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "100000");
        properties.put("auto.offset.reset", "earliest");//?????¦Ë???????--from-beginning
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("max.poll.records", 20);
        properties.put("value.deserializer", "kafka.consumer.UserDeserializer");
        kafkaConsumer = new KafkaConsumer<String, User>(properties);
        kafkaConsumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                kafkaConsumer.commitSync(offsetAndMetadataMap);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                offsetAndMetadataMap.clear();
            }
        });
    }

    /**
     * ???????????????????????????????????
     */
    public void consume(int threadNumber) {
        executorServices = new ThreadPoolExecutor(threadNumber, threadNumber, 0L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000),
                new ThreadPoolExecutor.CallerRunsPolicy());
        while (true) {
            ConsumerRecords<String, User> records = kafkaConsumer.poll(1000);
            if (!records.isEmpty()) {
                executorServices.submit(new ConsumerWorker(records, offsetAndMetadataMap));
            }
        }
    }

    private void commitOffsets() {
        Map<TopicPartition, OffsetAndMetadata> ummodfiedMap;
        synchronized (offsetAndMetadataMap) {
            if (offsetAndMetadataMap.isEmpty()) {
                return;
            }
            ummodfiedMap = Collections.unmodifiableMap(new HashMap<TopicPartition, OffsetAndMetadata>(offsetAndMetadataMap));
            offsetAndMetadataMap.clear();
        }
        kafkaConsumer.commitSync(ummodfiedMap);
    }

    public void close() {
        kafkaConsumer.wakeup();
        executorServices.shutdown();
    }
}