package com.groot.mindmap.message.repository;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisRepository {

    private static final String USER_LIST = "USER_LIST";
    private static final String ENTER_INFO = "ENTER_INFO";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> enterInfos;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> userLists;

    /**
     * 유저의 입장 정보를 Redis에 저장하는 메서드
     * @param sessionId 유저의 세션 ID
     * @param pageId 페이지의 ID
     */
    public void setEnterInfo(String sessionId, String pageId) {
        enterInfos.put(ENTER_INFO, sessionId, pageId);
    }

    /**
     * 유저의 입장 정보를 Redis에서 삭제하는 메서드
     * @param sessionId 유저의 세션 ID
     */
    public void removeEnterInfo(String sessionId) {
        enterInfos.delete(ENTER_INFO, sessionId);
    }

    /**
     * 유저 세션으로 입장해 있는 채팅방 ID 조회하는 메서드
     * @param sessionId 유저의 세션 ID
     * @return Page ID
     */
    public String getEnteredPageId(String sessionId) {
        return enterInfos.get(ENTER_INFO, sessionId);
    }

    /**
     * 현재 입장해 있는 유저 리스트에 새로운 유저를 추가하는 메서드
     * @param pageId Page ID
     * @param name 유저의 이름(또는 ID)
     */
    public void addUser(String pageId, String name) {
        userLists.leftPush(USER_LIST + "_" + pageId, name);
    }

    /**
     * 현재 접속중인 유저 목록을 반환하는 메서드
     * @param pageId Page ID
     * @return names 유저들의 이름 리스트(또는 유저 ID 리스트)
     */
    public List<String> getUsers(String pageId) {
        return userLists.range(USER_LIST + "_" + pageId, 0, -1);
    }

    public void removeUser(String pageId, String name) {
        userLists.remove(USER_LIST + "_" + pageId, 1, name);
    }
}
