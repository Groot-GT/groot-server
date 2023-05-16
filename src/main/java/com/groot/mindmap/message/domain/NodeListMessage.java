package com.groot.mindmap.message.domain;

import com.groot.mindmap.node.domain.Node;
import lombok.Getter;

import java.util.List;

@Getter
public class NodeListMessage extends Message {

    private final List<Node> nodes;

    private NodeListMessage(Long pageId, String type, List<Node> nodes) {
        super(pageId, type);
        this.nodes = nodes;
    }

    public static NodeListMessage create(Message message, List<Node> nodes) {
        // TODO validate 로직 추가
        return new NodeListMessage(message.getPageId(), message.getType(), nodes);
    }
}
