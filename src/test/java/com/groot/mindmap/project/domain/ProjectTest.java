package com.groot.mindmap.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.groot.mindmap.page.domain.Page;
import com.groot.mindmap.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProjectTest {

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder().build();
    }

    @Test
    @DisplayName("프로젝트를 생성한다.")
    void create() {
        assertThat(project.getTitle()).isEqualTo("제목 없음");
    }

    @Test
    @DisplayName("프로젝트의 주인을 설정한다.")
    void setOwner() {
        // given
        final User owner = User.builder()
                .name("팀장")
                .email("leader@gmail.com")
                .profileImage("leader.png")
                .platform("google")
                .build();

        // when
        project.setOwner(owner);

        // then
        assertThat(project.getOwner()).isEqualTo(owner);
        assertThat(owner.getProjects()).contains(project);
    }

    @Test
    @DisplayName("사용자가 프로젝트에 참여하고, 프로젝트의 참여 리스트에 추가한다.")
    void addParticipant() {
        // given
        final User user = User.builder()
                .name("테스트")
                .email("test@gmail.com")
                .profileImage("test.png")
                .platform("google")
                .build();
        final Participant participant = Participant.builder()
                .user(user)
                .project(project)
                .build();

        // when
        project.addParticipant(participant);

        // then
        assertThat(project.getParticipants()).contains(participant);
    }

    @Test
    @DisplayName("프로젝트에 페이지를 생성하고, 해당 페이지를 프로젝트의 페이지 리스트에 추가한다.")
    void addPage() {
        // given
        final Page page = Page.builder()
                .project(project)
                .build();

        // when
        project.addPage(page);

        // then
        assertThat(project.getPages()).contains(page);
    }
}
