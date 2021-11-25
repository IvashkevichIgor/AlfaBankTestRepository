package ru.ivashkevich.exchangeratecheck;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangeRateCheckApplicationTests {

    @Autowired
    private CurrencyController currencyController;

    @Test
    void contextLoads() throws Exception {
        assertThat(currencyController).isNotNull();
    }
}
