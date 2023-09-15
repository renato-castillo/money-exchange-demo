package pe.exchange.test.repository;

import org.springframework.stereotype.Repository;
import pe.exchange.test.domain.Currency;
import pe.exchange.test.repository.generic.IGenericRepo;

@Repository
public interface ICurrencyRepository extends IGenericRepo<Currency, Long> {
}
