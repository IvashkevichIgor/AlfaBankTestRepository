package ru.ivashkevich.exchangeratecheck;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="OpenExchangeRates", url="${openExchangeRates.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/latest${openExchangeRates.request}={code}")
    CurrencyRateResponse getCurrentCurrencyRate(@PathVariable String code);

    @GetMapping("/historical/{date}${openExchangeRates.request}={code}")
    CurrencyRateResponse getYesterdayCurrencyRate(@PathVariable String date, @PathVariable String code);
}

