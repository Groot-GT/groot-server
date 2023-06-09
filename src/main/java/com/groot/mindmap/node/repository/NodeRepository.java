package com.groot.mindmap.node.repository;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.page.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Long> {

    List<Node> findByPage(Page page);
}
