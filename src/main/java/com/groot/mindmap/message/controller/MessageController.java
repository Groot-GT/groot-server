package com.groot.mindmap.message.controller;

import com.groot.mindmap.message.service.MessageService;
import com.groot.mindmap.message.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@MessageMapping("/node")
@Controller
public class MessageController {

    private final MessageService messageService;
    private final RedisService redisService;

    @MessageMapping("/add")
    public void nodeAdd(String message) {
        redisService.sendMessage(messageService.create(message));
    }

    @MessageMapping("/delete")
    public void nodeDelete(String message) {
        redisService.sendMessage(messageService.delete(message));
    }
}
