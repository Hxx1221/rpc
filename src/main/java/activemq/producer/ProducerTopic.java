package activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProducerTopic {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory("tcp://106.12.29.156:61616");


        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

        Destination myQueue = session.createTopic("myQueue");
        MessageProducer producer = session.createProducer(myQueue);

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