package pe.exchange.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.exchange.test.security.AuthResponse;
import pe.exchange.test.security.JWTUtil;
import pe.exchange.test.service.IUserService;
import pe.exchange.test.security.AuthRequest;
import pe.exchange.test.security.ErrorLogin;
import reactor.core.publisher.Mono;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private IUserService service;

    @PostMapping
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar){
        return service.searchByUser(ar.getUsername())
                .map((userDetails) -> {

                    if(BCrypt.checkpw(ar.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails);
                        Date expiration = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponse(token, expiration));
                    }else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorLogin("Credenciales incorrectas", new Date()));
                    }
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
