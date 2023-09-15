package pe.exchange.test.handler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.exchange.test.domain.UserExchange;
import pe.exchange.test.model.UserExchangeDto;
import pe.exchange.test.service.IExchangeService;
import pe.exchange.test.domain.Exchange;
import pe.exchange.test.model.ExchangeDto;
import pe.exchange.test.service.IUserExchangeService;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ExchangeHandler {

    @Autowired
    private IExchangeService exchangeService;

    @Autowired
    private IUserExchangeService userExchangeService;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<Exchange> exchangeMono = req.bodyToMono(Exchange.class);

        return exchangeMono
                .map(x -> {
                    x.setId(null);
                    x.setCreatedAt(LocalDateTime.now());
                    return x;
                })
                .flatMap(x -> exchangeService.findExistChange(x.getCurrencyOriginId(), x.getCurrencyTargetId())
                        .flatMap(err -> Mono.error(new RuntimeException("Ya existe un tipo de cambio"))) // Generar un error si se encuentra un resultado
                        .switchIfEmpty(exchangeService.save(x)) // Si no se encuentra un resultado, crear uno nuevo
                )
                .flatMap(entity -> {
                    Exchange exchange = (Exchange) entity;
                    ExchangeDto body = modelMapper.map(exchange, ExchangeDto.class);
                    return ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(exchange.getId().toString())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(body));
                })
                .onErrorResume(RuntimeException.class, ex -> {
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(ex.getMessage()));
                });
    }

    public Mono<ServerResponse> update(ServerRequest req) {

        Long id = Long.valueOf(req.pathVariable("id"));

        Mono<Exchange> monoClient = req.bodyToMono(Exchange.class);
        Mono<Exchange> monoDB = exchangeService.findById(id);

        return req.principal().flatMap(pr -> monoDB
                .zipWith(monoClient, (db, cl) -> {
                    db.setExchange(cl.getExchange());
                    db.setModifiedBy(pr.getName());
                    db.setModifiedAt(LocalDateTime.now());
                    return db;
                })
                .flatMap(existingCurrency -> {
                    existingCurrency.setId(existingCurrency.getId());
                    existingCurrency.setModifiedAt(LocalDateTime.now());

                    return exchangeService.save(existingCurrency)
                            .flatMap(c -> {
                                ExchangeDto exchangeDto = modelMapper.map(c, ExchangeDto.class);

                                return ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(c.getId().toString())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(fromValue(exchangeDto));
                            });
                })
                .switchIfEmpty(ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(exchangeService.findAll(), Exchange.class);
    }

    public Mono<ServerResponse> findUserExchanges(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userExchangeService.findAll(), UserExchange.class);
    }

    public Mono<ServerResponse> findExchanges(ServerRequest req) {
        Mono<ExchangeDto> exchangeDtoMono = req.bodyToMono(ExchangeDto.class);

        return exchangeDtoMono.flatMap(x -> exchangeService.getExchanges(x.getCurrencyOriginId(), x.getCurrencyTargetId()))
                .flatMap(c -> {

                    ExchangeDto exchangeDto = modelMapper.map(c, ExchangeDto.class);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(exchangeDto));
                }).switchIfEmpty(ServerResponse.notFound().build());

    }

    @Transactional
    public Mono<ServerResponse> doExchange(ServerRequest req) {

        Mono<UserExchangeDto> exchangeRequestDtoMono = req.bodyToMono(UserExchangeDto.class);

        return req.principal().flatMap(pr -> exchangeRequestDtoMono.flatMap(er -> {

            UserExchange entity = new UserExchange();
            entity.setCreatedAt(LocalDateTime.now());
            entity.setCreatedBy(pr.getName());
            entity.setAmount(er.getAmount());

            return exchangeService.findExistChange(er.getCurrencyOriginId(), er.getCurrencyTargetId())
                    .flatMap(exist -> {
                        entity.setExchangeId(exist.getId());

                        if(!Objects.equals(exist.getExchange(), er.getExchangeRate())) {
                            return Mono.error(new Exception("El tipo de cambio ha variado para las monedas solicitadas"));
                        }

                        BigDecimal finalExchange = er.getExchangeRate().multiply(entity.getAmount());

                        entity.setExchange(finalExchange);
                        entity.setExchangeRate(exist.getExchange());

                        return userExchangeService.save(entity)
                                .flatMap(save -> {
                                    System.out.println(save.getId());
                                   return userExchangeService.findUserExchangeById(save.getId())
                                            .flatMap(dto -> {
                                                UserExchangeDto userExchangeDto = modelMapper.map(dto, UserExchangeDto.class);

                                                return ServerResponse.ok()
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .body(fromValue(userExchangeDto));
                                            });
                                });
                    }).switchIfEmpty(Mono.error(new Exception("No se encontró un tipo de cambio para las monedas solicitadas")));



        }));

    }

}
