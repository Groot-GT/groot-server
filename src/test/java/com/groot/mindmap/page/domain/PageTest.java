package com.groot.mindmap.page.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.groot.mindmap.node.domain.Node;
import com.groot.mindmap.project.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageTest {

    private Page page;

    @BeforeEach
    void setUp() {
        page = Page.builder().build();
    }

    @Test
    @DisplayName("페이지를 생성한다.")
    void create() {
        assertThat(page.getTitle()).isEqualTo("제목 없음");
    }

    @Test
    @DisplayName("페이지가 속해있는 프로젝트를 설정한다.")
    void setProject() {
        // given
        final Project project = Project.builder()
                .build();

        // when
        page.setProject(project);

        // then
        assertThat(page.getProject()).isEqualTo(project);
        assertThat(project.getPages()).contains(page);
    }

    @Test
    @DisplayName("페이지에 노드를 생성하고, 해당 노드를 페이지의 노드 리스트에 추가한다.")
    void addNode() {
        // given
        final Node node = Node.defaultNode();

        // when
        page.addNode(node);

        // then
        assertThat(page.getNodes()).contains(node);
    }
}
