package com.groot.mindmap.node.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NodeRequest(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 30, message = "제목은 30 글자를 넘을 수 없습니다.")
        String title,
        @Size(max = 255, message = "content는 255 글자를 넘을 수 없습니다.")
        String content,
        @NotBlank(message = "color를 입력해주세요.")
        String color,
        @NotNull(message = "부모 node를 선택해주세요.")
        Long parentId) {
}
