package com.groot.mindmap.message.domain;

import java.util.List;

public class ExitMessage extends Message{

    private final List<String> users;
    private ExitMessage(String pageId, List<String> users) {
        super(pageId, "EXIT");
        this.users = users;
    }

    public static ExitMessage create(String pageId, List<String> users) {
        // TODO validate logic 추가

        return new ExitMessage(pageId, users);
    }
}
