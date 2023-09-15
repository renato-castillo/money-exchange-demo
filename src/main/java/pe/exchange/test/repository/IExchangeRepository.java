package pe.exchange.test.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import pe.exchange.test.domain.Exchange;
import pe.exchange.test.domain.custom.ExchangeCurrencyQuery;
import pe.exchange.test.repository.generic.IGenericRepo;
import reactor.core.publisher.Mono;

@Repository
public interface IExchangeRepository extends IGenericRepo<Exchange, Long> {

    @Query("SELECT e.id as id, " +
            "e.currency_origin_id as currencyOriginId, " +
            "e.currency_target_id as currencyTargetId, " +
            "cOrg.name as currencyOriginName, " +
            "cDest.name as currencyTargetName, " +
            "e.exchange as exchange " +
            "FROM EXCHANGES e " +
            "JOIN CURRENCIES cOrg on e.currency_origin_id = cOrg.id " +
            "JOIN CURRENCIES cDest on e.currency_target_id = cDest.id " +
            "WHERE e.currency_origin_id = :currencyOriginId " +
            "and e.currency_target_id = :currencyTargetId")
    Mono<ExchangeCurrencyQuery> findExchanges(Long currencyOriginId,
                                              Long currencyTargetId);

    @Query("SELECT e.* FROM EXCHANGES e " +
            "WHERE e.currency_origin_id = :currencyOriginId " +
            "and e.currency_target_id = :currencyTargetId")
    Mono<Exchange> findExistChange(Long currencyOriginId,
                                              Long currencyTargetId);

}
