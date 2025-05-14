package com.naedonnaepick.backend.chat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 수 있는 WebSocket 엔드포인트 지정
        // SockJS 지원으로 브라우저 호환성 보장
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버가 메시지를 브로드캐스팅할 때 사용하는 주제 접두어 설정
        registry.enableSimpleBroker("/topic");

        // 클라이언트가 서버로 보낼 때 사용하는 접두어 설정
        registry.setApplicationDestinationPrefixes("/app");
    }
}
