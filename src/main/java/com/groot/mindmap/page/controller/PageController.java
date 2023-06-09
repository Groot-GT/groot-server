package com.groot.mindmap.page.controller;

import com.groot.mindmap.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/pages")
@RestController
public class PageController {

    private final PageService service;

    @GetMapping
    public ResponseEntity<Void> pageAdd() {
        final Long id = service.create();
        return ResponseEntity.created(URI.create("/api/pages/" + id)).build();
    }
}
