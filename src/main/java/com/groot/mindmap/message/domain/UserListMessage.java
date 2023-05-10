package com.groot.mindmap.message.domain;

import java.util.List;

public class UserListMessage extends Message{

    private final List<String> users;

    private UserListMessage(String pageId, String type, List<String> users) {
        super(pageId, type);
        this.users = users;
    }

    public static UserListMessage create(String pageId, String type, List<String> users) {
        // TODO validate 로직 추가
        return new UserListMessage(pageId, type, users);
    }
}
