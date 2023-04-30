package com.groot.mindmap.node.controller;

import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.service.NodeService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(final NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping()
    public ResponseEntity<Void> createNode(@RequestBody final NodeRequest nodeRequest) {
        final Long id = nodeService.save(nodeRequest);
        return ResponseEntity.created(URI.create("/api/nodes/" + id)).build();
    }
}
