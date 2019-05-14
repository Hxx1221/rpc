package activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProducerQueueMain {

    public static void main(String[] args) throws JMSException {


        ConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory("tcp://106.12.29.156:61616");


        Connection connection = activeMQConnectionFactory.createConnection();

        connection.start();

        /**
         *
         * 第一个参数是否开始起事务
         * 第二个参数表示acknowledgment
         * 有四个参数：
         *      AUTO_ACKNOWLEDGE：为自动确认，客户端发送和接受不需要额外的工作
         *      CLIENT_ACKNOWLEDGE：为客户端确认，表示客户端接受信息后，
         *                          必须调用acknowledge方法，jms服务器才会删除消息
         *      DUPS_OK_ACKNOWLEDGE：自动批量确认
         *      SESSION_TRANSACTED：事务提交并确认
         *
         *     如果支持事务  那么第二个参数也就没有意义
         *     true不支持事务
         *     false 支持事务
         *
         *
         *
         *
         * */
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        Destination myQueue = session.createQueue("myQueue");


        MessageProducer producer = session.createProducer(myQueue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化



        for(int i=0;i<1;i++) {
            //创建需要发送的消息
            TextMessage message = session.createTextMessage("Hello World:"+i);
            //Text   Map  Bytes  Stream  Object
            producer.send(message);
        }

        //    session.commit();
        session.close();

    }
}