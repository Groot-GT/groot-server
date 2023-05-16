package com.groot.mindmap.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.mindmap.message.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessagePublisher {

    private final ObjectMapper mapper;
    private final SimpMessageSendingOperations operations;

    /**
     * Redis 에서 메시지가 발행(publish)되면 대기하고 있던 Redis Publisher가 해당 메시지를 받아 Subscribers에게 처리한다
     * @param message 발행되는 메세지
     */
    public void sendMessage(String message) {
        operations.convertAndSend("/sub/page/" + getPageId(message), message);
    }

    private Long getPageId(String message) {
        try {
            return mapper.readValue(message, Message.class).pageId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
