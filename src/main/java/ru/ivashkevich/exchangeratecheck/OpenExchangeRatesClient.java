package ru.ivashkevich.exchangeratecheck;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="OpenExchangeRates", url="${openExchangeRates.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/latest.json")
    CurrencyRateResponse getCurrentCurrencyRate(@RequestParam("app_id") String id, @RequestParam("symbols") String code);

    @GetMapping("/historical/{date}.json")
    CurrencyRateResponse getYesterdayCurrencyRate(
            @RequestParam("app_id") String id, @PathVariable String date, @RequestParam("symbols") String code);
}

