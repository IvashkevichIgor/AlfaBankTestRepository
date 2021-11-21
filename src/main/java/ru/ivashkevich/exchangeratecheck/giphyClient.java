package ru.ivashkevich.exchangeratecheck;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "giphy", url = "${giphy.url}")
public interface giphyClient {
    @GetMapping("/")
    Response getResponse();
}
