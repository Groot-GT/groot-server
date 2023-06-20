package com.groot.mindmap.page.domain;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.project.domain.Project;
import com.groot.mindmap.util.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Page extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String title = "제목 없음";

    @Builder.Default
    @OneToMany(mappedBy = "page",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Node> nodes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public void addNode(final Node node) {
        nodes.add(node);
        if (node.getPage() != this) {
            node.setPage(this);
        }
    }

    public void setProject(final Project project) {
        this.project = project;
        if (!project.getPages().contains(this)) {
            project.getPages().add(this);
        }
    }
}
