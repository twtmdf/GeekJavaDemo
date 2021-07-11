package io.kimmking.javacourse.kafkademo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = {"test"},groupId = "group1", containerFactory="kafkaListenerContainerFactory")
    public void kafkaListener(String message){
        System.out.println(message);
    }

}
