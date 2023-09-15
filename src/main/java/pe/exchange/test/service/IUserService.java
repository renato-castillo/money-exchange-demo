package pe.exchange.test.service;

import pe.exchange.test.security.User;
import pe.exchange.test.util.ICRUD;
import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<User, Long> {

    Mono<User> saveHash(User user);
    Mono<User> searchByUser(String user);

}
