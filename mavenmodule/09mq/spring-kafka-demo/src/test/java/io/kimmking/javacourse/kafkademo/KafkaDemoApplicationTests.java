package io.kimmking.javacourse.kafkademo;

import io.kimmking.javacourse.kafkademo.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class KafkaDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private KafkaProducerService producerService;

    @Test
    public void sendMessageSync() throws InterruptedException, ExecutionException, TimeoutException {
        producerService.sendMessageSync("test","同步发送消息测试");
    }

    @Test
    public void sendMessageAsync() {
        producerService.sendMessageAsync("test","异步发送消息测试");
    }
}
