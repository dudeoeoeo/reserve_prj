package com.kei.messageservice.service;

import com.kei.messageservice.constant.MQInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MQInfo mqInfo, String message) {
        rabbitTemplate.convertAndSend(mqInfo.exchange, mqInfo.key, message);
    }
}
