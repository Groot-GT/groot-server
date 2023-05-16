package com.groot.mindmap.message.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class UserListMessage extends Message{

    private final List<String> users;

    private UserListMessage(Long pageId, String type, List<String> users) {
        super(pageId, type);
        this.users = users;
    }

    public static UserListMessage create(Long pageId, String type, List<String> users) {
        // TODO validate 로직 추가
        return new UserListMessage(pageId, type, users);
    }
}
