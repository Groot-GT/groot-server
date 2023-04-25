package com.groot.mindmap.node.controller;

import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.service.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/node")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(final NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final NodeRequest nodeRequest) {
        nodeService.save(nodeRequest);
    }
}
