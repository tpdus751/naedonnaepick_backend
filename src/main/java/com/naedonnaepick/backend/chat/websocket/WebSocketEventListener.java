package com.naedonnaepick.backend.chat.websocket;

import com.naedonnaepick.backend.chat.websocket.service.SessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private SessionTracker sessionTracker;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        String nativeHeaders = accessor.getNativeHeader("roomNo") != null ?
                accessor.getNativeHeader("roomNo").get(0) : "unknown";
        sessionTracker.addSession(sessionId, nativeHeaders);
        System.out.println("[접속] sessionId=" + sessionId + " → room=" + nativeHeaders);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
        sessionTracker.removeSession(sessionId);
        System.out.println("[퇴장] sessionId=" + sessionId);
    }
}
