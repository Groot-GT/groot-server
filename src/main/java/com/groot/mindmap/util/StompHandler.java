package com.groot.mindmap.util;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT -> {
                // TODO 유저가 페이지에 접속할 때의 로직 추가 / JWT 권한 확인
            }
            case SUBSCRIBE -> {
                // TODO 페이지 구독에 따른 로직 추가 / 현재 접속중인 유저 리스트에 추가
            }
            case DISCONNECT -> {
                // TODO 유저가 페이지를 나갈 때의 로직 추가 / 현재 접속중인 유저 리스트에서 제거
            }
        }

        return message;
    }
}
