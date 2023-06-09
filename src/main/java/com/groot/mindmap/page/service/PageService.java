package com.groot.mindmap.page.service;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.page.domain.Page;
import com.groot.mindmap.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PageService {

    private final PageRepository repository;

    @Transactional
    public Long create() {
        Page page = Page.builder().build();
        Node defaultNode = Node.defaultNode();
        defaultNode.setPage(page);
        page.addNode(defaultNode);
        return repository.save(page).getId();
    }

    @Transactional(readOnly = true)
    public Page findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 페이지가 존재하지 않습니다."));
    }
}
