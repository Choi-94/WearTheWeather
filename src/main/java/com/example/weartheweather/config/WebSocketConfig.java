package com.example.weartheweather.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*클라이언트가 웹 소켓 서버에 연결하는데 사용할 웹 소켓 엔드포인트 등록
	  withSockJS를 통해 웹 소켓을 지원하지 않는 브라우저에 대해 웹 소켓을 대체한다.
	  +)메소드명에 STOMP가 들어가는 경우 통신 프로토콜인 STOMP구현에서 작동된다. */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /*한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //roomname으로 시작되는 요청을 구독한 모든 사용자들에게 메시지를 broadcast한다.
        registry.enableSimpleBroker("/roomname");
        // message로 시작되는 메시지는 message-handling methods로 라우팅된다.
        registry.setApplicationDestinationPrefixes("/message");
    }
}