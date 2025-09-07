package chat.cyber.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class socketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthChannelInterceptor authChannelInterceptor;

    public socketConfig(AuthChannelInterceptor authChannelInterceptor) {
        this.authChannelInterceptor = authChannelInterceptor;
    }

    @Override
     public void configureMessageBroker(MessageBrokerRegistry config){

         config.enableSimpleBroker("/topic", "/queue");
         config.setApplicationDestinationPrefixes("/app");
         config.setUserDestinationPrefix("/user"); // necessário p/ enviar msg privada

     }

     @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         // Endpoint para se conectar ao WebSocket
         registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*");

     }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }
}
