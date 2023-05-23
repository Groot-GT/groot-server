package com.groot.mindmap.node.dto;

import java.util.List;

public record NodeResponse(Long id, String title, String content, String color, Long parentId, List<Long> children) {
}
