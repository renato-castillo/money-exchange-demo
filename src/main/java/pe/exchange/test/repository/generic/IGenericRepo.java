package pe.exchange.test.repository.generic;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepo<T, ID> extends R2dbcRepository<T, ID> {
}

