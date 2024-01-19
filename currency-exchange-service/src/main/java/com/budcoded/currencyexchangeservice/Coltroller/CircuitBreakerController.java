package com.budcoded.currencyexchangeservice.Coltroller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodedResponse")
    @RateLimiter(name = "sample-api")
    // setting different rate limits for different API's
    @Bulkhead(name = "sample-api")
    public String sampleApi() {
        logger.info("Sample API call received.");
//        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/some-url", String.class);
//        return entity.getBody();
        return "sample API";
    }

    private String hardcodedResponse (Exception exception) {
        return "fallback-response";
    }
}
