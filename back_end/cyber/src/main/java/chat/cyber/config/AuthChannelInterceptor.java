package chat.cyber.config;

import chat.cyber.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    private JwtService jwtService;

    public AuthChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())){
            System.out.println("HEADERS WS: " + accessor.toNativeHeaderMap());
            List<String> auth = accessor.getNativeHeader("Authorization");

            if (auth != null && !auth.isEmpty()){
                String bearer = auth.get(0);

                if (bearer.startsWith("Bearer ")){
                    String token = bearer.substring(7);
                    Claims userId = jwtService.getClaims(token);

                    if (userId.getSubject() != null){
                        Principal user = new StompPrincipal(userId.getSubject());
                        accessor.setUser(user);

                    }
                }
            }
        }
        return message;
    }
}
