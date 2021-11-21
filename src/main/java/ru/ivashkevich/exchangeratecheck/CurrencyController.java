package ru.ivashkevich.exchangeratecheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
public class CurrencyController {

    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    public CurrencyController(OpenExchangeRatesClient openExchangeRatesClient) {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    @GetMapping("/currency/{code}")
    public String exchangeRate(@PathVariable(value = "code") String code){
        CurrencyRateResponse currentResponse = openExchangeRatesClient.getCurrentCurrencyRate(code);
        Double currentCurrencyRate = currentResponse.getRates().get(code);

        String yesterdayDateString = getYesterdayDateString();
        CurrencyRateResponse yesterdayResponse = openExchangeRatesClient.getYesterdayCurrencyRate(yesterdayDateString, code);
        Double yesterdayCurrencyRate = yesterdayResponse.getRates().get(code);

        String result = "current: " + currentCurrencyRate + "\n" + "yesterday: " + yesterdayCurrencyRate + "\n";

        if (currentCurrencyRate > yesterdayCurrencyRate){
            result += "You're rich!";
        }
        else
            result += "You're broke";
        return result;
    }

    private String getYesterdayDateString() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayDate = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(yesterdayDate);
    }

    public OpenExchangeRatesClient getOpenExchangeRatesClient() {
        return openExchangeRatesClient;
    }

    public void setOpenExchangeRatesClient(OpenExchangeRatesClient openExchangeRatesClient) {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }
}
