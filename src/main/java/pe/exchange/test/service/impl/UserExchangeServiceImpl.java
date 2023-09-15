package pe.exchange.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.exchange.test.domain.UserExchange;
import pe.exchange.test.domain.custom.UserExchangeQuery;
import pe.exchange.test.repository.IUserExchangeRepository;
import pe.exchange.test.repository.generic.IGenericRepo;
import pe.exchange.test.security.User;
import pe.exchange.test.service.IUserExchangeService;
import pe.exchange.test.service.IUserService;
import pe.exchange.test.util.impl.CRUDImpl;
import reactor.core.publisher.Mono;

@Service
public class UserExchangeServiceImpl extends CRUDImpl<UserExchange, Long> implements IUserExchangeService {

    @Autowired
    private IUserExchangeRepository userExchangeRepository;

    @Override
    protected IGenericRepo<UserExchange, Long> getRepo() {
        return userExchangeRepository;
    }

    @Override
    public Mono<UserExchangeQuery> findUserExchangeById(Long id) {
        return userExchangeRepository.findUserExchangeById(id);
    }
}
