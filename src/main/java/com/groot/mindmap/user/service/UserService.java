package com.groot.mindmap.user.service;

import com.groot.mindmap.user.domain.AuthInformation;
import com.groot.mindmap.user.domain.User;
import com.groot.mindmap.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    @Transactional
    public Long create(final AuthInformation information) {
        User user = User.builder()
                .name(information.name())
                .email(information.email())
                .profileImage(information.profileImage())
                .platform(information.platform())
                .build();
        return repository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public Optional<User> findByPlatformAndEmail(final String platform, final String email) {
        return repository.findByPlatformAndEmail(platform, email);
    }
}
