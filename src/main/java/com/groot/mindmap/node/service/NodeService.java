package com.groot.mindmap.node.service;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.dto.NodeResponse;
import com.groot.mindmap.node.repository.NodeRepository;
import com.groot.mindmap.page.domain.Page;
import com.groot.mindmap.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class NodeService {

    private final NodeRepository repository;
    private final PageService pageService;

    @Transactional
    public void create(final NodeRequest nodeRequest, final Long pageId) {
        Page page = pageService.findById(pageId);
        Node parent = findNodeObject(nodeRequest.parentId());
        Node node = Node.builder()
                .title(nodeRequest.title())
                .content(nodeRequest.content())
                .color(nodeRequest.color())
                .parentId(nodeRequest.parentId())
                .children(nodeRequest.children())
                .build();

        node.setPage(page);
        page.addNode(node);
        parent.getChildren().add(repository.save(node).getId());
    }

    @Transactional(readOnly = true)
    public NodeResponse detail(final Long id) {
        final Node node = findNodeObject(id);
        return new NodeResponse(node.getId(), node.getTitle(), node.getContent(), node.getColor(), node.getParentId(), node.getChildren());
    }

    @Transactional(readOnly = true)
    public List<NodeResponse> list(final Long pageId) {
        Page page = pageService.findById(pageId);
        return repository.findByPage(page).stream()
                .map(this::mapToDto)
                .toList();
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
                .children(nodeRequest.children())
                .build();
        repository.save(node);
    }

    private Node findNodeObject(final Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("해당하는 Node가 없습니다."));
    }

    @Transactional
    public void delete(final Long id) {
        Node parent = findNodeObject(id);
        for (Long childId : parent.getChildren()) {
            delete(childId);
        }
        repository.delete(parent);
    }

    private NodeResponse mapToDto(Node node) {
        return new NodeResponse(node.getId(), node.getTitle(), node.getContent(), node.getColor(), node.getParentId(), node.getChildren());
    }
}
