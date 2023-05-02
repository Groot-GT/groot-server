package com.groot.mindmap.node.dto;

public record NodeResponse(Long id, String title, String content, String color, Long parentId) {
}
