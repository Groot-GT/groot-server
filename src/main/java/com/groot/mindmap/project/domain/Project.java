package com.groot.mindmap.project.domain;

import com.groot.mindmap.page.domain.Page;
import com.groot.mindmap.user.domain.User;
import com.groot.mindmap.util.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String title = "제목 없음";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Builder.Default
    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<Page> pages = new ArrayList<>();

    public void addParticipant(final Participant participant) {
        participants.add(participant);
        if (participant.getProject() != this) {
            participant.setProject(this);
        }
    }

    public void addPage(final Page page) {
        pages.add(page);
        if (page.getProject() != this) {
            page.setProject(this);
        }
    }

    public void setOwner(final User user) {
        this.owner = user;
        if (!user.getProjects().contains(this)) {
            user.getProjects().add(this);
        }
    }
}
