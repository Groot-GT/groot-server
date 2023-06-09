package com.groot.mindmap.page.service;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.page.domain.Page;
import com.groot.mindmap.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PageService {

    private final PageRepository repository;

    public Long create() {
        Page page = Page.builder().build();
        Node defaultNode = Node.defaultNode();
        defaultNode.setPage(page);
        page.addNode(defaultNode);
        return repository.save(page).getId();
    }
}
