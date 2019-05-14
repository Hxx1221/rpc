package activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class CustomerTopic {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://106.12.29.156:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        //创建目的地
        Destination destination=session.createTopic("myQueue");
        //创建发送者
        MessageConsumer consumer=session.createConsumer(destination);
        TextMessage textMessage = (TextMessage) consumer.receive();
        System.out.println(textMessage.getText());
        textMessage.acknowledge();
        //  session.commit();//表示消息被自动确认
        session.close();
    }


}