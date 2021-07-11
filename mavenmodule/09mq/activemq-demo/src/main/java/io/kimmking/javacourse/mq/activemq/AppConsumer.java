package io.kimmking.javacourse.mq.activemq;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {
    private static final String url = "tcp://127.0.0.1:61616";
    private static final String queueName = "queue-test";

    public static void main(String[] args) throws JMSException, InterruptedException {
        //1. 创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2. 创建Connection
        Connection connection = connectionFactory.createConnection();
        //3. 启动连接
        connection.start();
        //4. 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建一个目标
        Destination destination = session.createQueue(queueName);
        //6. 创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7. 创建一个监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("接收消息  = [" + ((TextMessage) message).getText() + "]");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(2000);
        //8.关闭连接（消费者的连接不允许关闭的，因为消息的接收是异步的，会导致消息不能被消费）
        connection.close();
    }
}

