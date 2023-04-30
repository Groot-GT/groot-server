package com.groot.mindmap.node.service;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.dto.NodeResponse;
import com.groot.mindmap.node.repository.NodeRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    public NodeService(final NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public Long save(final NodeRequest nodeRequest) {
        final Node node = Node.builder()
                .title(nodeRequest.title())
                .content(nodeRequest.content())
                .color(nodeRequest.color())
                .parentId(nodeRequest.parentId())
                .build();
        return nodeRepository.save(node).getId();
    }

    public NodeResponse findNode(final Long id) {
        final Node node = findNodeObject(id);
        return new NodeResponse(node.getId(), node.getTitle(), node.getContent(), node.getColor(), node.getParentId());
    }

    @Transactional
    public void update(final Long id, final NodeRequest nodeRequest) {
        findNodeObject(id);
        final Node node = Node.builder()
                .id(id)
                .title(nodeRequest.title())
                .content(nodeRequest.content())
                .color(nodeRequest.color())
                .parentId(nodeRequest.parentId())
                .build();
        nodeRepository.save(node);
    }

    private Node findNodeObject(final Long id) {
        return nodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당하는 Node가 없습니다."));
    }
}
