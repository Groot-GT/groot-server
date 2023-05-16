package com.groot.mindmap.message.domain;

import com.groot.mindmap.node.domain.Node;

import java.util.List;

public class NodeListMessage extends Message {

    private final List<Node> nodes;

    private NodeListMessage(Long pageId, String type, List<Node> nodes) {
        super(pageId, type);
        this.nodes = nodes;
    }

    public static NodeListMessage create(Long pageId, String type, List<Node> nodes) {
        // TODO validate 로직 추가
        return new NodeListMessage(pageId, type, nodes);
    }
}
