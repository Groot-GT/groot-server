package com.groot.mindmap.user.repository;

import com.groot.mindmap.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPlatformAndEmail(String platform, String email);
}
