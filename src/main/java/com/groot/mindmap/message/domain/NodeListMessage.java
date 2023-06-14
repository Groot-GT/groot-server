package com.groot.mindmap.message.domain;

import com.groot.mindmap.node.dto.NodeResponse;
import lombok.Getter;

import java.util.Map;

@Getter
public class NodeListMessage extends Message {

    private final Map<Long, NodeResponse> nodes;

    public NodeListMessage(Message message, Map<Long, NodeResponse> nodes) {
        super(message.getPageId(), message.getType());
        this.nodes = nodes;
    }
}
