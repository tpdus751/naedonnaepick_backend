package com.naedonnaepick.backend.chat.websocket.service;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionTracker {

    // 기존: 세션 ID ↔ 방 번호 추적
    private final Map<String, String> sessionToRoomMap = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> roomToSessionMap = new ConcurrentHashMap<>();

    // ✅ 추가: 방 번호 ↔ 이메일 추적
    private final Map<String, Set<String>> roomToUsers = new ConcurrentHashMap<>();

    // 세션 기반 추적
    public void addSession(String sessionId, String roomNo) {
        sessionToRoomMap.put(sessionId, roomNo);
        roomToSessionMap.computeIfAbsent(roomNo, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
    }

    public void removeSession(String sessionId) {
        String roomNo = sessionToRoomMap.remove(sessionId);
        if (roomNo != null) {
            Set<String> sessions = roomToSessionMap.get(roomNo);
            if (sessions != null) {
                sessions.remove(sessionId);
                if (sessions.isEmpty()) {
                    roomToSessionMap.remove(roomNo);
                }
            }
        }
    }

    public int getUserCount(String roomNo) {
        return roomToUsers.getOrDefault(roomNo, Collections.emptySet()).size();
    }

    // ✅ email 기반 사용자 추가
    public void addUser(String roomNo, String email) {
        roomToUsers.computeIfAbsent(roomNo, k -> ConcurrentHashMap.newKeySet()).add(email);
    }

    // ✅ email 기반 사용자 제거
    public void removeUser(String roomNo, String email) {
        if (roomToUsers.containsKey(roomNo)) {
            roomToUsers.get(roomNo).remove(email);
            if (roomToUsers.get(roomNo).isEmpty()) {
                roomToUsers.remove(roomNo);
            }
        }
    }
}
