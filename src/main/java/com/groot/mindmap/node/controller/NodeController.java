package com.groot.mindmap.node.controller;

import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.dto.NodeResponse;
import com.groot.mindmap.node.service.NodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeService nodeService;

    @GetMapping("/{id}")
    public ResponseEntity<NodeResponse> findNode(@PathVariable Long id) {
        final NodeResponse nodeResponse = nodeService.detail(id);
        return ResponseEntity.ok(nodeResponse);
    }

    @GetMapping("/list/{pageId}")
    public ResponseEntity<Map<Long, NodeResponse>> findNodes(@PathVariable Long pageId) {
        return ResponseEntity.ok(nodeService.list(pageId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNode(@PathVariable Long id, @Valid @RequestBody final NodeRequest nodeRequest) {
        nodeService.update(id, nodeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        nodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
