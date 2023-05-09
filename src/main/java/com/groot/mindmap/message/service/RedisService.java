package com.groot.mindmap.message.service;

import com.groot.mindmap.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final ChannelTopic topic;
    private final RedisTemplate<String, Object> template;

    public void sendMessage(Message message) {
        template.convertAndSend(topic.getTopic(), message);
    }
}
