package pe.exchange.test.handler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.exchange.test.domain.Currency;
import pe.exchange.test.model.CurrencyDto;
import pe.exchange.test.service.ICurrencyService;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class CurrencyHandler {

    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<Currency> currencyMono = req.bodyToMono(Currency.class);

        return req.principal().map(pr -> pr).flatMap(y -> currencyMono
                .map(x -> {
                    x.setId(null);
                    x.setCreatedAt(LocalDateTime.now());
                    x.setCreatedBy(y.getName());
                    return x;
                })
                .flatMap(currencyService::save)
                .flatMap(c -> {
                    CurrencyDto body = modelMapper.map(c, CurrencyDto.class);
                    return ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(c.getId().toString())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(body));
                }));


    }

    public Mono<ServerResponse> update(ServerRequest req) {

        Long id = Long.valueOf(req.pathVariable("id"));

        Mono<Currency> monoClient = req.bodyToMono(Currency.class);
        Mono<Currency> monoDB = currencyService.findById(id);

        return monoDB
                .zipWith(monoClient, (db, cl) -> {
                    db.setId(cl.getId());
                    db.setName(cl.getName());
                    db.setSymbol(cl.getSymbol());
                    db.setModifiedBy(cl.getModifiedBy());
                    db.setModifiedAt(LocalDateTime.now());
                    return db;
                })
                .flatMap(existingCurrency -> req.principal().flatMap(principal -> {
                    existingCurrency.setId(id);
                    existingCurrency.setModifiedAt(LocalDateTime.now());

                    return currencyService.save(existingCurrency)
                            .flatMap(c -> {
                                CurrencyDto currencyDto = modelMapper.map(c, CurrencyDto.class);

                                return ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(c.getId().toString())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(fromValue(currencyDto));
                            });
                })).switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(currencyService.findAll(), Currency.class);
    }



}
