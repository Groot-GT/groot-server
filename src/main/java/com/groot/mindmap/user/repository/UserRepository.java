package com.groot.mindmap.user.repository;

import com.groot.mindmap.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
