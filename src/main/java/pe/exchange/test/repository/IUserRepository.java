package pe.exchange.test.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import pe.exchange.test.repository.generic.IGenericRepo;
import pe.exchange.test.security.User;
import reactor.core.publisher.Mono;

@Repository
public interface IUserRepository extends IGenericRepo<User, Long> {

    @Query("SELECT u.* FROM users u where u.username = :username")
    Mono<User> findByUsername(String username);

}
