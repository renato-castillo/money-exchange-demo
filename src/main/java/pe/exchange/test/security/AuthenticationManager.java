package pe.exchange.test.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

//Clase S5
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        String user;
        try {
            user = jwtUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            user = null;
        }

        if (user != null && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);

            List<String> rolesMap = claims.get("roles", List.class);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            return Mono.just(auth);
        }else {
            //return Mono.empty();
            return Mono.error(new InterruptedException("Token no válido o ha expirado"));
        }
    }
}
