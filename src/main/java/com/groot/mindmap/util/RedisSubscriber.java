package com.groot.mindmap.util;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final SimpMessageSendingOperations operations;

    public void sendMessage(String m) {
        // TODO 메세지를 구독자들에게 전송하는 로직 추가
    }
}
