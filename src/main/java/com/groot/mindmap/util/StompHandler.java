package com.groot.mindmap.util;

import com.groot.mindmap.message.domain.EntryMessage;
import com.groot.mindmap.message.repository.RedisRepository;
import com.groot.mindmap.message.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final RedisService service;
    private final RedisRepository repository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT -> {
                // TODO 유저가 페이지에 접속할 때의 로직 추가 / JWT 권한 확인
            }
            case SUBSCRIBE -> {
                String sessionId = Objects.requireNonNull(message.getHeaders().get("simpSessionId")).toString();
                String destination = Objects.requireNonNull(message.getHeaders().get("simpDestination")).toString();
                String pageId = service.getPageId(destination);

                // 유저의 접속 정보를 저장하고 현재 접속 중인 유저 리스트에 새로운 유저를 추가
                repository.setEnterInfo(sessionId, pageId);
                repository.addUser(pageId, "NO_NAME");

                // 현재 접속 중인 유저 목록에 대한 메세지를 pub
                List<String> users = repository.getUsers(pageId);
                service.sendMessage(EntryMessage.create(pageId, users));
            }
            case DISCONNECT -> {
                // TODO 유저가 페이지를 나갈 때의 로직 추가 / 현재 접속중인 유저 리스트에서 제거
            }
        }

        return message;
    }
}
