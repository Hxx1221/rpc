package kafka.producer.interceptor;

import kafka.producer.User;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CounterInterceptor implements ProducerInterceptor<String, User> {

    private int errorCounter = 0;
    private int successCounter = 0;

    @Override
    public ProducerRecord<String, User> onSend(ProducerRecord<String, User> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            successCounter++;
        }else{
            errorCounter++;
        }
        System.out.println("CounterInterceptor ===onAcknowledgement");
    }

    @Override
    public void close() {
        System.out.println("errorCounter:"+errorCounter);
        System.out.println("successCounter:"+successCounter);
        System.out.println("CounterInterceptor===kafka拦截器==configure");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}