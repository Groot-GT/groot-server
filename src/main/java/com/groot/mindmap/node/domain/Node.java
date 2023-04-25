package com.groot.mindmap.node.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String color;
    private Long parentId;

    public Node(final Long id, final String title, final String content, final String color, final Long parentId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.color = color;
        this.parentId = parentId;
    }
}
