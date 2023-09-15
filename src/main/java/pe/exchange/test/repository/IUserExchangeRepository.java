package pe.exchange.test.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import pe.exchange.test.domain.UserExchange;
import pe.exchange.test.domain.custom.UserExchangeQuery;
import pe.exchange.test.repository.generic.IGenericRepo;
import reactor.core.publisher.Mono;

@Repository
public interface IUserExchangeRepository extends IGenericRepo<UserExchange, Long> {

    @Query("SELECT cOrg.id as currencyOriginId, " +
            "cOrg.name as currencyOriginName, " +
            "cDest.id as currencyTargetId, " +
            "cDest.name as currencyTargetName, " +
            "ue.amount as amount, " +
            "ue.exchange as exchange, " +
            "ue.exchange_rate as exchangeRate " +
            "FROM user_exchanges ue " +
            "JOIN exchanges e on e.id = ue.exchange_id " +
            "JOIN currencies cOrg on cOrg.id = e.currency_origin_id " +
            "JOIN currencies cDest on cDest.id = e.currency_target_id  " +
            "where ue.id = :id")
    Mono<UserExchangeQuery> findUserExchangeById(Long id);

}