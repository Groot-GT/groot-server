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

    /**
     * 목적지 정보에서 Page ID를 추출하는 메서드
     * @param destination 헤더에 포함된 destination(Page ID를 포함하고 있음)
     * @return Page ID
     */
    public String getPageId(String destination) {
        int lastIndex = destination.lastIndexOf("/");
        return lastIndex == -1 ? "" : destination.substring(lastIndex + 1);
    }

    public void sendMessage(Message message) {
        template.convertAndSend(topic.getTopic(), message);
    }
}
