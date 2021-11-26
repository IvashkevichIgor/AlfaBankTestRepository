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
public class GiphyClientIntegrationTest {

    @Value("${giphy.api_key}")
    private String key;
    @Autowired
    private WireMockServer mockGiphyService;
    @Autowired
    private GiphyClient giphyClient;

    @BeforeEach
    void setUp() throws IOException {
        GiphyMocks.setupMockGiphyResponse(mockGiphyService);
    }

    @Test
    public void whenGetLatest_thenGiphyDTOShouldBeReturned() {
        GiphyDTO giphyDTO = giphyClient.getGif(key, "rich");
        Assertions.assertFalse(giphyDTO.getData().isEmpty());

        Assertions.assertTrue(giphyDTO.getData().containsKey("url"));
        Assertions.assertTrue(giphyDTO.getData().containsKey("embed_url"));

        Assertions.assertFalse(((String)(giphyDTO.getData().get("url"))).isEmpty());
        Assertions.assertFalse(((String)(giphyDTO.getData().get("embed_url"))).isEmpty());
    }
}
