package com.groot.mindmap.node.repository;

import com.groot.mindmap.node.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Long> {
}
