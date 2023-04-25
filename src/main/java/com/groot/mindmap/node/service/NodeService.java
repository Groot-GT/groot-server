package com.groot.mindmap.node.service;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    public NodeService(final NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public void save(final NodeRequest nodeRequest) {
        final Node node = Node.builder()
                .title(nodeRequest.title())
                .content(nodeRequest.content())
                .color(nodeRequest.color())
                .parentId(nodeRequest.parentId())
                .build();
        nodeRepository.save(node);
    }
}
