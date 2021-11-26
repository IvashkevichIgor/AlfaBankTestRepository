package ru.ivashkevich.exchangeratecheck;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class GiphyMocks {

    public static void setupMockGiphyResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/random?api_key=qYhdz6oltdGKsPek5nE0zG61v0YFnfgA&tag=rich"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GiphyMocks.class.getClassLoader()
                                                .getResourceAsStream("get-giphy-response.json"),
                                        defaultCharset()))));
    }
}
