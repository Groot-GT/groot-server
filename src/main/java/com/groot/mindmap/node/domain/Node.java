package com.groot.mindmap.node.domain;

import com.groot.mindmap.page.domain.Page;
import com.groot.mindmap.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private String title = "제목 없음";
    private String content;
    private String color;
    private Long parentId;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> children;

    @ManyToOne(fetch = FetchType.LAZY)
    private Page page;

    public static Node defaultNode() {
        return Node.builder()
                .content("")
                .color("#ffffff")
                .parentId(null)
                .children(List.of())
                .build();
    }

    public void setPage(Page page) {
        this.page = page;
        if (!page.getNodes().contains(this)) {
            page.getNodes().add(this);
        }
    }
}
