package ru.ivashkevich.exchangeratecheck;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy", url = "${giphy.url}")
public interface GiphyClient {
    @GetMapping("/random")
    GiphyDTO getGif(@RequestParam("api_key") String key, @RequestParam("tag") String tag);
}
