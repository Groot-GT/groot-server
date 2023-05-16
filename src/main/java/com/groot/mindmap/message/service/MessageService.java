package com.groot.mindmap.message.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.mindmap.message.domain.AddMessage;
import com.groot.mindmap.message.domain.DeleteMessage;
import com.groot.mindmap.message.domain.Message;
import com.groot.mindmap.message.domain.NodeListMessage;
import com.groot.mindmap.node.dto.NodeRequest;
import com.groot.mindmap.node.service.NodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final ObjectMapper mapper;
    private final NodeService nodeService;

    @Transactional
    public Message create(String message) {
        // TODO 임의로 NodeRequest 를 생성하고, Pub 할 메세지를 생성해 반환
        try {
            AddMessage addMessage = mapper.readValue(message, AddMessage.class);
            NodeRequest request = new NodeRequest("제목 없음", "내용 없음", "black", addMessage.parentId());
            nodeService.create(request);
            return new NodeListMessage(addMessage, nodeService.list());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Message delete(String message) {
        try {
            DeleteMessage deleteMessage = mapper.readValue(message, DeleteMessage.class);
            nodeService.delete(deleteMessage.nodeId());
            return new NodeListMessage(deleteMessage, nodeService.list());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
