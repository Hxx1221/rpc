package kafka.producer.interceptor;

import kafka.producer.User;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
/**
 * kafka拦截器
 * */
public class TimeStampPrependerIntcepor implements ProducerInterceptor<String, User> {


    /**
     * 封装在send方法中，也就是说运行在用户主线程中，
     * 他会在消息被序列化，和计算分区前被进行调用。
     */
    @Override
    public ProducerRecord<String, User> onSend(ProducerRecord<String, User> record) {
        User value = record.value();
        value.setAddress(System.currentTimeMillis()+":"+value.getAddress());
        return new ProducerRecord<String, User>(record.key(),value);
    }


    /**
     *
     *会在消息被应答之前或者消息发送失败时调用。通常都是回调逻辑之前触发。
     *
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {
        System.out.println("TimeStampPrependerIntcepor===kafka拦截器==configure");

    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}