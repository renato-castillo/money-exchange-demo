package pe.exchange.test.service;

import pe.exchange.test.util.ICRUD;
import pe.exchange.test.domain.Exchange;
import pe.exchange.test.domain.custom.ExchangeCurrencyQuery;
import reactor.core.publisher.Mono;

public interface IExchangeService extends ICRUD<Exchange, Long> {

    Mono<ExchangeCurrencyQuery> getExchanges(Long currencyOriginId, Long currencyTargetId);

    Mono<Exchange> findExistChange(Long currencyOriginId,
                                   Long currencyTargetId);

}
