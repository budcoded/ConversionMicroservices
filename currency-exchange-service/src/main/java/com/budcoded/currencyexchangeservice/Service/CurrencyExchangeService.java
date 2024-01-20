package com.budcoded.currencyexchangeservice.Service;

import com.budcoded.currencyexchangeservice.Model.CurrencyExchange;
import com.budcoded.currencyexchangeservice.Repository.CurrencyExchangeRepository;
import io.opentelemetry.api.logs.LoggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeService {
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final Environment environment;
    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    public CurrencyExchangeService(CurrencyExchangeRepository currencyExchangeRepository, Environment environment) {
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.environment = environment;
    }

    public CurrencyExchange retrieveExchange(String from, String to) {
        logger.info("Retrieve Exchange value from {} to {}.", from, to);
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        String port = environment.getProperty("local.server.port");
        String host = environment.getProperty("HOSTNAME");
        String version = "V1";
        currencyExchange.setEnvironment(port + " " + version + " " + host);
        return currencyExchange;
    }
}
