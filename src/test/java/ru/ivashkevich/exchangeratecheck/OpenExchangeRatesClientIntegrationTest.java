package ru.ivashkevich.exchangeratecheck;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class OpenExchangeRatesClientIntegrationTest {

    @Value("${openExchangeRates.app_id}")
    private String openExchangeRatesAppId;
    @Autowired
    private WireMockServer mockOpenExchangeRatesService;
    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @BeforeEach
    void setUp() throws IOException {
        OpenExchangeRatesMocks.setupMockLatestResponse(mockOpenExchangeRatesService);
    }

    @Test
    public void whenGetLatest_thenCurrencyRateDTOShouldBeReturned() {
        CurrencyRateDTO currencyRateDTO = openExchangeRatesClient.getCurrentCurrencyRate(openExchangeRatesAppId, "RUB");
        Assertions.assertFalse(currencyRateDTO.getRates().isEmpty());
        Assertions.assertTrue(currencyRateDTO.getRates().containsKey("RUB"));
        Assertions.assertFalse(currencyRateDTO.getRates().get("RUB").isNaN());
        Assertions.assertEquals(currencyRateDTO.getTimestamp(), "1637852399");
    }

    @Test
    public void whenGetHistorical_thenCurrencyRateDTOShouldBeReturned(){
        CurrencyRateDTO currencyRateDTO = openExchangeRatesClient
                .getYesterdayCurrencyRate(openExchangeRatesAppId, "2021-11-24", "RUB");
        Assertions.assertFalse(currencyRateDTO.getRates().isEmpty());
        Assertions.assertTrue(currencyRateDTO.getRates().containsKey("RUB"));
        Assertions.assertFalse(currencyRateDTO.getRates().get("RUB").isNaN());
        Assertions.assertEquals(currencyRateDTO.getTimestamp(), "1637798388");
    }
}
