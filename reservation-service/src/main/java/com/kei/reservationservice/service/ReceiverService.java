package com.kei.reservationservice.service;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiverService {

    @RabbitListener(queues = "reserve.queue")
    public void consume(Response message) {
        log.info("Consume Message: {}", message.toString());
    }
}
