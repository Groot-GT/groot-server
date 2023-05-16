package com.groot.mindmap.message.domain;

import com.groot.mindmap.node.domain.Node;
import lombok.Getter;

import java.util.List;

@Getter
public class NodeListMessage extends Message {

    private final List<Node> nodes;

    public NodeListMessage(Message message, List<Node> nodes) {
        super(message.getPageId(), message.getType());
        this.nodes = nodes;
    }
}
