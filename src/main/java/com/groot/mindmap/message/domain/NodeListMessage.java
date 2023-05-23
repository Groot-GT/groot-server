package com.groot.mindmap.message.domain;

import com.groot.mindmap.node.domain.Node;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class NodeListMessage extends Message {

    private final Map<Long, Node> nodes;

    public NodeListMessage(Message message, List<Node> nodes) {
        super(message.getPageId(), message.getType());
        this.nodes = nodes.stream().collect(Collectors.toMap(Node::getId, Function.identity()));
    }
}
