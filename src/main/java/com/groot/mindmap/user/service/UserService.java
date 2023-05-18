package com.groot.mindmap.user.service;

import com.groot.mindmap.user.domain.User;
import com.groot.mindmap.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    @Transactional
    public Long create(final String name, final String email, final String profileImage) {
        User user = User.builder()
                .name(name)
                .email(email)
                .profileImage(profileImage)
                .build();
        return repository.save(user).getId();
    }
}
