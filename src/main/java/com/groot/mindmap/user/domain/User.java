package com.groot.mindmap.user.domain;

import com.groot.mindmap.project.domain.Participant;
import com.groot.mindmap.project.domain.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String profileImage;

    private String platform;

    @Builder.Default
    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    public void addProject(final Project project) {
        projects.add(project);
        if (project.getOwner() != this) {
            project.setOwner(this);
        }
    }

    public void addParticipant(final Participant participant) {
        participants.add(participant);
        if (participant.getUser() != this) {
            participant.setUser(this);
        }
    }
}
