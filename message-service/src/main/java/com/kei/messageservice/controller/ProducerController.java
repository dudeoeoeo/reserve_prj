package com.kei.messageservice.controller;

import com.kei.messageservice.service.ProducerService;
import com.kei.messageservice.vo.MessageReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/message")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public ResponseEntity send(@Valid @RequestBody MessageReq req) {
        producerService.sendMessage(req.getMqInfo(), req.getMessage());
        return ResponseEntity.ok(null);
    }
}
