package ru.ivashkevich.exchangeratecheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
public class CurrencyController {

    private OpenExchangeRatesClient openExchangeRatesClient;
    private GiphyClient giphyClient;
    @Value("${openExchangeRates.app_id}")
    private String openExchangeRatesAppId;
    @Value("${giphy.api_key}")
    private String giphyAPIKey;

    @Autowired
    public CurrencyController(OpenExchangeRatesClient openExchangeRatesClient, GiphyClient giphyClient) {
        this.openExchangeRatesClient = openExchangeRatesClient;
        this.giphyClient = giphyClient;
    }

    @GetMapping("/currency/{code}")
    public String exchangeRate(@PathVariable String code, Model model){
        CurrencyRateResponse currentResponse =
                openExchangeRatesClient.getCurrentCurrencyRate(openExchangeRatesAppId, code);
        double currentCurrencyRate = currentResponse.getRates().get(code);

        String yesterdayDateString = getYesterdayDateString();
        CurrencyRateResponse yesterdayResponse =
                openExchangeRatesClient.getYesterdayCurrencyRate(openExchangeRatesAppId,yesterdayDateString, code);
        double yesterdayCurrencyRate = yesterdayResponse.getRates().get(code);

        GiphyResponse response = giphyClient.getGif(giphyAPIKey, getTag(currentCurrencyRate, yesterdayCurrencyRate));
        String URL = (String) response.getData().get("url");
        String embedURL = (String) response.getData().get("embed_url");
        model.addAttribute("giphyUrl", URL);
        model.addAttribute("giphyEmbedUrl", embedURL);
        return "exchangeRateCheck";
    }

    private String getYesterdayDateString() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayDate = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(yesterdayDate);
    }

    private String getTag(double currentCurrencyRate, double yesterdayCurrencyRate){
        String tag;
        if (currentCurrencyRate > yesterdayCurrencyRate){
            tag = "rich";
        }
        else if(currentCurrencyRate < yesterdayCurrencyRate){
            tag = "broke";
        }
        else tag = "nothing";
        return tag;
    }

    public OpenExchangeRatesClient getOpenExchangeRatesClient() {
        return openExchangeRatesClient;
    }

    public void setOpenExchangeRatesClient(OpenExchangeRatesClient openExchangeRatesClient) {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }
}
