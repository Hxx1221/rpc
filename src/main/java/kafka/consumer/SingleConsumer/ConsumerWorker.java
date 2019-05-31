package kafka.consumer.SingleConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Map;

public class ConsumerWorker<K, V> implements Runnable {
    private final ConsumerRecords<K, V> records;

    private final Map<TopicPartition, OffsetAndMetadata> offsets;

    public ConsumerWorker(ConsumerRecords<K, V> records, Map<TopicPartition, OffsetAndMetadata> offsets) {
        this.records = records;
        this.offsets = offsets;
    }

    @Override
    public void run() {
        for (TopicPartition partition : records.partitions()) {

            List<ConsumerRecord<K, V>> partitionRecords = records.records(partition);
            for (ConsumerRecord<K, V> consumerRecord : partitionRecords) {
                System.out.println(String.format("topic=%s,partition=%d,offset=%d,value=%d", consumerRecord.topic(),
                        consumerRecord.offset(), consumerRecord.topic(), consumerRecord.value()));
            }
            long offset = partitionRecords.get(partitionRecords.size() - 1).offset();
            synchronized (offsets){
                if (!offsets.containsKey(partition)){
                    offsets.put(partition,new OffsetAndMetadata(offset+1));
                }else {
                    long offset1 = offsets.get(partition).offset();
                    if (offset1<=offset){
                        offsets.put(partition,new OffsetAndMetadata(offset+1));
                    }


                }

            }


        }

    }
}