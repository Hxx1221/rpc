package kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

//自定义分区
public class MyPartitioner implements Partitioner {


    private Random random;

    /**
     * 计算给定消息要被发送到哪个分区
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        System.out.println("MyPartitioner");

        String keyObj = (String) key;
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(topic);

        int size = partitionInfos.size();
        int auditPartition = size - 1;
        if (size == 1) {
            return 0;
        }
        return keyObj == null || keyObj.isEmpty() || !keyObj.contains("audit") ? random.nextInt(size - 1) : auditPartition;
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        System.out.println("MyPartitioner configure");

        random = new Random();
    }
}