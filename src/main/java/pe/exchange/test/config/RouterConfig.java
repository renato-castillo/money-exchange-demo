package pe.exchange.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.exchange.test.handler.ExchangeHandler;
import pe.exchange.test.handler.CurrencyHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routesCurrencies(CurrencyHandler currencyHandler) {
        return route(POST("/currencies"), currencyHandler::create)
                .andRoute(PUT("/currencies/{id}"), currencyHandler::update)
                .andRoute(GET("/currencies"), currencyHandler::findAll);
    }

    @Bean
    public RouterFunction<ServerResponse> routesExchanges(ExchangeHandler exchangeHandler) {
        return route(POST("/exchanges"), exchangeHandler::create)
                .andRoute(GET("/exchanges"), exchangeHandler::findAll)
                .andRoute(GET("/exchanges/userExchanges"), exchangeHandler::findUserExchanges)
                .andRoute(PUT("/exchanges/{id}"), exchangeHandler::update)
                .andRoute(POST("/exchanges/getExchanges"), exchangeHandler::findExchanges)
                .andRoute(POST("/exchanges/doExchange"), exchangeHandler::doExchange);
    }

}
