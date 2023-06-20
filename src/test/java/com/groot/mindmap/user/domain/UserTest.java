package com.groot.mindmap.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.groot.mindmap.project.domain.Participant;
import com.groot.mindmap.project.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("테스트")
                .email("test@gmail.com")
                .profileImage("test.png")
                .platform("google")
                .build();
    }

    @Test
    @DisplayName("User를 생성한다.")
    void create() {
        assertThat(user.getName()).isEqualTo("테스트");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getProfileImage()).isEqualTo("test.png");
        assertThat(user.getPlatform()).isEqualTo("google");
    }

    @Test
    @DisplayName("사용자가 프로젝트를 생성하고, 해당 프로젝트를 사용자의 프로젝트 리스트에 추가한다.")
    void addProject() {
        // given
        final Project project = Project.builder()
                .owner(user)
                .build();

        // when
        user.addProject(project);

        // then
        assertThat(user.getProjects()).contains(project);
    }

    @Test
    @DisplayName("사용자가 프로젝트에 참여하고, 사용자의 참여 리스트에 추가한다.")
    void addParticipant() {
        // given
        final User owner = User.builder()
                .name("팀장")
                .email("leader@gmail.com")
                .profileImage("leader.png")
                .platform("google")
                .build();
        final Project project = Project.builder()
                .owner(owner)
                .build();
        final Participant participant = Participant.builder()
                .user(user)
                .project(project)
                .build();

        // when
        user.addParticipant(participant);

        // then
        assertThat(user.getParticipants()).contains(participant);
    }
}
