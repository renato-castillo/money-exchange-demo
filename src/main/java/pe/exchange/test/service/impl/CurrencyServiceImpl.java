package pe.exchange.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.exchange.test.domain.Currency;
import pe.exchange.test.repository.generic.IGenericRepo;
import pe.exchange.test.util.impl.CRUDImpl;
import pe.exchange.test.repository.ICurrencyRepository;
import pe.exchange.test.service.ICurrencyService;

@Service
public class CurrencyServiceImpl extends CRUDImpl<Currency, Long> implements ICurrencyService {

    @Autowired
    private ICurrencyRepository currencyRepository;

    @Override
    protected IGenericRepo<Currency, Long> getRepo() {
        return currencyRepository;
    }
}
