package activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class CustomerQueueMain {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://106.12.29.156:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //创建目的地
        Destination destination=session.createQueue("myQueue");
        //创建发送者




        MessageConsumer consumer=session.createConsumer(destination);




//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                TextMessage ms = (TextMessage)  message;
//                try {
//                    System.out.println(ms.getText());
//                   // ms.acknowledge();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });











//            TextMessage textMessage = (TextMessage) consumer.receive();
//            System.out.println(textMessage.getText());
//                textMessage.acknowledge();
        //  session.commit();//表示消息被自动确认
     //   session.close();




    }
}