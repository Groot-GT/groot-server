package com.groot.mindmap.message.domain;

import java.util.List;

public class EntryMessage extends Message {

    private final List<String> users;

    private EntryMessage(String pageId, List<String> users) {
        super(pageId,"ENTRY");
        this.users = users;
    }

    public static EntryMessage create(String pageId, List<String> users) {
        // TODO validate 로직 추가
        return new EntryMessage(pageId, users);
    }
}
