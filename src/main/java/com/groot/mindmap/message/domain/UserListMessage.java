package com.groot.mindmap.message.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class UserListMessage extends Message{

    private final List<String> users;

    public UserListMessage(Long pageId, String type, List<String> users) {
        super(pageId, type);
        this.users = users;
    }
}
