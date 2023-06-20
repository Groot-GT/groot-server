package com.groot.mindmap.project.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.groot.mindmap.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParticipantTest {

    private Participant participant;

    @BeforeEach
    void setUp() {
        participant = Participant.builder().build();
    }

    @Test
    @DisplayName("Participant를 생성한다.")
    void create() {
        assertNotNull(participant);
    }

    @Test
    @DisplayName("사용자를 설정한다.")
    void setUser() {
        // given
        final User user = User.builder()
                .name("테스트")
                .email("test@gmail.com")
                .profileImage("test.png")
                .platform("google")
                .build();

        // when
        participant.setUser(user);

        // then
        assertThat(participant.getUser()).isEqualTo(user);
        assertThat(user.getParticipants()).contains(participant);
    }

    @Test
    @DisplayName("프로젝트를 설정한다.")
    void setProject() {
        // given
        final Project project = Project.builder()
                .build();

        // when
        participant.setProject(project);

        // then
        assertThat(participant.getProject()).isEqualTo(project);
        assertThat(project.getParticipants()).contains(participant);
    }
}
