package com.groot.mindmap.page.domain;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Page extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private String title = "제목 없음";

    @Builder.Default
    @OneToMany(mappedBy = "page",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Node> nodes = new ArrayList<>();

    public void addNode(Node node) {
        this.nodes.add(node);
        if (node.getPage() != this) {
            node.setPage(this);
        }
    }
}
