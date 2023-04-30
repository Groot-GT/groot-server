package com.groot.mindmap.node.controller;

import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.dto.NodeResponse;
import com.groot.mindmap.node.service.NodeService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // TODO: @Valid 검증
    @PostMapping()
    public ResponseEntity<Void> createNode(@RequestBody final NodeRequest nodeRequest) {
        final Long id = nodeService.save(nodeRequest);
        return ResponseEntity.created(URI.create("/api/nodes/" + id)).build();
    }

    // TODO: findNodesByPage 구현
    @GetMapping("/{id}")
    public ResponseEntity<NodeResponse> findNode(@PathVariable Long id) {
        final NodeResponse nodeResponse = nodeService.findNode(id);
        return ResponseEntity.ok(nodeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNode(@PathVariable Long id, @RequestBody final NodeRequest nodeRequest) {
        nodeService.update(id, nodeRequest);
        return ResponseEntity.ok().build();
    }
}
