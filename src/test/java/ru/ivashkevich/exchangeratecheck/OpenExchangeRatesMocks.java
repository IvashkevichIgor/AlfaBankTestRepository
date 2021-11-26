package ru.ivashkevich.exchangeratecheck;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class OpenExchangeRatesMocks {

    public static void setupMockOpenExchangeRatesResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/latest.json?app_id=8aaa3e083460483e905320992d569147&symbols=RUB"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OpenExchangeRatesMocks.class.getClassLoader()
                                                .getResourceAsStream("get-latest-response.json"),
                                        defaultCharset()))));

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/historical/2021-11-24.json?app_id=8aaa3e083460483e905320992d569147&symbols=RUB"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OpenExchangeRatesMocks.class.getClassLoader()
                                                .getResourceAsStream("get-historical-response.json"),
                                        defaultCharset()))));
    }
}
