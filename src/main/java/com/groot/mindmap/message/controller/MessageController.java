package com.groot.mindmap.message.controller;

import com.groot.mindmap.message.service.MessageService;
import com.groot.mindmap.message.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {

    private final MessageService messageService;
    private final RedisService redisService;

    @MessageMapping("/node/add")
    public void nodeAdd(String message) {
        redisService.sendMessage(messageService.create(message));
    }

    @MessageMapping("/node/delete")
    public void nodeDelete(String message) {
        redisService.sendMessage(messageService.delete(message));
    }

}
