package com.budcoded.currencyconversionservice.Service;

import com.budcoded.currencyconversionservice.Config.CurrencyExchangeProxy;
import com.budcoded.currencyconversionservice.Model.CurrencyConversion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {
    private final CurrencyExchangeProxy currencyExchangeProxy;
    private final RestTemplate restTemplate;

    public CurrencyConversionService(CurrencyExchangeProxy currencyExchangeProxy, RestTemplate restTemplate) {
        this.currencyExchangeProxy = currencyExchangeProxy;
        this.restTemplate = restTemplate;
    }

    public CurrencyConversion calculateCurrencyConversion(String from, String to, BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return new CurrencyConversion(currencyConversion.getId(), from, to, currencyConversion.getConversionMultiple(), quantity, quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " rest template.");
    }

    public CurrencyConversion calculateCurrencyConversionFeign(String from, String to, BigDecimal quantity) {
        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
        return new CurrencyConversion(currencyConversion.getId(), from, to, currencyConversion.getConversionMultiple(), quantity, quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " feign.");
    }
}
