package pe.exchange.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.exchange.test.domain.Exchange;
import pe.exchange.test.domain.custom.ExchangeCurrencyQuery;
import pe.exchange.test.repository.IExchangeRepository;
import pe.exchange.test.repository.generic.IGenericRepo;
import pe.exchange.test.service.IExchangeService;
import pe.exchange.test.util.impl.CRUDImpl;
import reactor.core.publisher.Mono;

@Service
public class ExchangeServiceImpl extends CRUDImpl<Exchange, Long> implements IExchangeService {

    @Autowired
    private IExchangeRepository exchangeRepository;

    @Override
    protected IGenericRepo<Exchange, Long> getRepo() {
        return exchangeRepository;
    }

    @Override
    public Mono<ExchangeCurrencyQuery> getExchanges(Long currencyOriginId, Long currencyTargetId) {
        return exchangeRepository.findExchanges(currencyOriginId, currencyTargetId);
    }

    @Override
    public Mono<Exchange> findExistChange(Long currencyOriginId, Long currencyTargetId) {
        return exchangeRepository.findExistChange(currencyOriginId, currencyTargetId);
    }
}
