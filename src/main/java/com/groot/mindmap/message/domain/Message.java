package com.groot.mindmap.message.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public abstract class Message {

    @JsonProperty("pageId")
    private final Long pageId;

    @JsonProperty("type")
    private final String type;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Message(@JsonProperty("pageId") Long pageId,
                   @JsonProperty("type") String type) {
        this.pageId = pageId;
        this.type = type;
    }
}
