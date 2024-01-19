package com.budcoded.currencyexchangeservice.Coltroller;

import com.budcoded.currencyexchangeservice.Model.CurrencyExchange;
import com.budcoded.currencyexchangeservice.Service.CurrencyExchangeService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyExchangeController {
    private final CurrencyExchangeService currencyExchangeService;
    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
//    @Retry(name = "currency-exchange", fallbackMethod = "fallbackResponse")
    @CircuitBreaker(name = "currency-exchange", fallbackMethod = "fallbackResponse")
    @RateLimiter(name = "currency-exchange")
// setting different rate limits for different API's
    @Bulkhead(name = "currency-exchange")
    public CurrencyExchange retrieveExchange(@PathVariable String from, @PathVariable String to) {
        logger.info("Currency Exchange API call received.");
        CurrencyExchange currencyExchange = currencyExchangeService.retrieveExchange(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to + ".");
        }
        return currencyExchange;
    }

    private CurrencyExchange fallbackResponse (Exception exception) {
        throw new RuntimeException("This API is currently not working. Please try after some time.");
//        return new CurrencyExchange();
    }
}
