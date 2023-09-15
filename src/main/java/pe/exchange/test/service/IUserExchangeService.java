package pe.exchange.test.service;

import pe.exchange.test.domain.UserExchange;
import pe.exchange.test.domain.custom.UserExchangeQuery;
import pe.exchange.test.util.ICRUD;
import reactor.core.publisher.Mono;

public interface IUserExchangeService extends ICRUD<UserExchange, Long> {

    Mono<UserExchangeQuery> findUserExchangeById(Long id);

}
