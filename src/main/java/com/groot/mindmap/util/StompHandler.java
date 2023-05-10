package com.groot.mindmap.util;

import com.groot.mindmap.message.domain.UserListMessage;
import com.groot.mindmap.message.repository.RedisRepository;
import com.groot.mindmap.message.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                String pageId = getPageId(destination);

                // 유저의 접속 정보를 저장하고 현재 접속 중인 유저 리스트에 새로운 유저를 추가
                repository.setEnterInfo(sessionId, pageId);
                repository.addUser(pageId, "NO_NAME");

                // 현재 접속 중인 유저 목록에 대한 메세지를 pub
                List<String> users = repository.getUsers(pageId);
                service.sendMessage(UserListMessage.create(pageId, "ENTRY", users));
            }
            case DISCONNECT -> {
                String sessionId = Objects.requireNonNull(message.getHeaders().get("simpSessionId")).toString();
                String pageId = repository.getEnteredPageId(sessionId);
                String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser"))
                        .map(Principal::getName)
                        .orElseThrow();

                // 유저의 접속 정보를 삭제하고 현재 접속 중인 유저 리스트에서 유저를 제거
                repository.removeUser(pageId, name);
                repository.removeEnterInfo(sessionId);

                // 현재 접속 중인 유저 목록에 대한 메세지를 pub
                List<String> users = repository.getUsers(pageId);
                service.sendMessage(UserListMessage.create(pageId, "EXIT", users));
            }
        }
        return message;
    }

    /**
     * 목적지 정보에서 Page ID를 추출하는 메서드
     * @param destination 헤더에 포함된 destination(Page ID를 포함하고 있음)
     * @return Page ID
     */
    private String getPageId(String destination) {
        int lastIndex = destination.lastIndexOf("/");
        return lastIndex == -1 ? "" : destination.substring(lastIndex + 1);
    }
}
