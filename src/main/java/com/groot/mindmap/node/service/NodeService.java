package com.groot.mindmap.node.service;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.dto.NodeResponse;
import com.groot.mindmap.node.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    public NodeService(final NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public Long create(final NodeRequest nodeRequest) {
        final Node node = Node.builder()
                .title(nodeRequest.title())
                .content(nodeRequest.content())
                .color(nodeRequest.color())
                .parentId(nodeRequest.parentId())
                .build();
        return nodeRepository.save(node).getId();
    }

    @Transactional(readOnly = true)
    public NodeResponse detail(final Long id) {
        final Node node = findNodeObject(id);
        return new NodeResponse(node.getId(), node.getTitle(), node.getContent(), node.getColor(), node.getParentId());
    }

    @Transactional(readOnly = true)
    public List<Node> list() {
        // TODO pageID 를 통해 리스트를 받도록 변경
        return nodeRepository.findAll();
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

    @Transactional
    public void delete(final Long id) {
        findNodeObject(id);
        nodeRepository.deleteById(id);
    }
}
