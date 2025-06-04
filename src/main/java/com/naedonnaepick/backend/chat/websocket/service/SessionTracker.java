package com.naedonnaepick.backend.chat.websocket.service;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionTracker {

    private final Map<String, String> sessionToRoomMap = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> roomToSessionMap = new ConcurrentHashMap<>();

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
        return roomToSessionMap.getOrDefault(roomNo, Collections.emptySet()).size();
    }
}
