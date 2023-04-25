package com.groot.mindmap.node.dto;

public record NodeRequest(String title, String content, String color, Long parentId) {
}
