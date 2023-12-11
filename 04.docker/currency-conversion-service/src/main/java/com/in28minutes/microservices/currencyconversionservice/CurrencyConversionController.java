package com.in28minutes.microservices.currencyconversionservice;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;


@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {

    @Value("${currency-exchange.url}")
    private String defaultCurrencyExchangeUrl;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyExchangeProxy proxy;
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity
            ) {

        String currencyExchangeUrl = System.getenv("CURRENCY_EXCHANGE_URL");
        //  COMMENT: Fallback for currencyExchangeUrl null
        if (StringUtils.isBlank(currencyExchangeUrl)) {

            currencyExchangeUrl = defaultCurrencyExchangeUrl;
        }
        String url = currencyExchangeUrl + "/currency-exchange/from/{from}/to/{to}";

        HashMap<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

//        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
//                CurrencyConversion.class, uriVariables);
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity(url, CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
                currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " rest template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
                currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " feign");
    }
}
