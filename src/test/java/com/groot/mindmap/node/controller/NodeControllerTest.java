package com.groot.mindmap.node.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.dto.NodeResponse;
import com.groot.mindmap.node.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WithMockUser
@SpringBootTest
class NodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NodeService nodeService;

    @Test
    void findNode() throws Exception {
        // given
        final NodeResponse response = new NodeResponse(1L, "노드1", "노드입니다.", "#FFFFFF", null, Collections.emptyList());
        given(nodeService.detail(anyLong())).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/nodes/{id}", response.id())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader()))
                .andDo(document("node/findNodeById",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Node ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Node ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Node 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Node 내용"),
                                fieldWithPath("color").type(JsonFieldType.STRING).description("Node 색깔"),
                                fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("부모 Node ID")
                                        .optional(),
                                fieldWithPath("children[]").type(JsonFieldType.ARRAY).description("자식 Node 목록")
                        )
                )).andExpect(status().isOk());
    }

    @Test
    void findNodes() throws Exception {
        // given
        final Long pageId = 1L;
        final NodeResponse nodeResponse1 = new NodeResponse(1L, "노드1", "노드1입니다.", "#FFFFFF", null, List.of(2L));
        final NodeResponse nodeResponse2 = new NodeResponse(2L, "노드2", "노드2입니다.", "#000000", 1L,
                Collections.emptyList());
        final Map<Long, NodeResponse> response = Map.of(nodeResponse1.id(), nodeResponse1, nodeResponse2.id(), nodeResponse2);
        given(nodeService.list(pageId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/nodes/list/{pageId}", pageId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader()))
                .andDo(document("node/findNodesByPageId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("pageId").description("Page ID")
                        ),
                        responseFields(
                                subsectionWithPath("1").description("Node ID"),
                                fieldWithPath("1.id").type(JsonFieldType.NUMBER).description("Node ID"),
                                fieldWithPath("1.title").type(JsonFieldType.STRING).description("Node 제목"),
                                fieldWithPath("1.content").type(JsonFieldType.STRING).description("Node 내용"),
                                fieldWithPath("1.color").type(JsonFieldType.STRING).description("Node 색깔"),
                                fieldWithPath("1.parentId").type(JsonFieldType.NUMBER).description("부모 Node ID")
                                        .optional(),
                                fieldWithPath("1.children").type(JsonFieldType.ARRAY).description("자식 Node 목록"),
                                subsectionWithPath("2").description("Node ID"),
                                fieldWithPath("2.id").type(JsonFieldType.NUMBER).description("Node ID"),
                                fieldWithPath("2.title").type(JsonFieldType.STRING).description("Node 제목"),
                                fieldWithPath("2.content").type(JsonFieldType.STRING).description("Node 내용"),
                                fieldWithPath("2.color").type(JsonFieldType.STRING).description("Node 색깔"),
                                fieldWithPath("2.parentId").type(JsonFieldType.NUMBER).description("부모 Node ID")
                                        .optional(),
                                fieldWithPath("2.children").type(JsonFieldType.ARRAY).description("자식 Node 목록")
                        )
                )).andExpect(status().isOk());
    }

    @Test
    void updateNode() throws Exception {
        // given
        final Long id = 2L;
        final NodeRequest request = new NodeRequest("수정 노드", "수정 완료", "#ABCDEF", 1L, Collections.emptyList());
        willDoNothing()
                .given(nodeService)
                .update(anyLong(), any());

        // when & then
        mockMvc.perform(put("/api/nodes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf().asHeader()))
                .andDo(document("node/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Node ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Node 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Node 내용"),
                                fieldWithPath("color").type(JsonFieldType.STRING).description("Node 색깔"),
                                fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("부모 Node ID")
                                        .optional(),
                                fieldWithPath("children").type(JsonFieldType.ARRAY).description("자식 Node 목록")
                        )
                )).andExpect(status().isOk());
    }

    @Test
    void deleteNode() throws Exception {
        // given
        final Long id = 1L;
        willDoNothing()
                .given(nodeService)
                .delete(anyLong());

        // when & then
        mockMvc.perform(delete("/api/nodes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf().asHeader()))
                .andDo(document("node/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Node ID")
                        )
                )).andExpect(status().isNoContent());
    }
}
