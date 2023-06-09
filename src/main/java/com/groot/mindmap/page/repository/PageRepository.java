package com.groot.mindmap.page.repository;

import com.groot.mindmap.page.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
}
