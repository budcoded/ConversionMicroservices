package com.budcoded.currencyconversionservice.Coltroller;

import com.budcoded.currencyconversionservice.Model.CurrencyConversion;
import com.budcoded.currencyconversionservice.Service.CurrencyConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }


    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion (@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        return currencyConversionService.calculateCurrencyConversion(from, to, quantity);
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign (@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        return currencyConversionService.calculateCurrencyConversionFeign(from, to, quantity);
    }
}
