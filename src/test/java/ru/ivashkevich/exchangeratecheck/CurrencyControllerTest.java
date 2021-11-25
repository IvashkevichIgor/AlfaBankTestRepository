package ru.ivashkevich.exchangeratecheck;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CurrencyController controller;

    @Test
    public void controllerTest() throws Exception {
        this.mockMvc.perform(get("/currency/RUB"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("via GIPHY")));
    }
}
