package ru.ivashkevich.exchangeratecheck;

import java.util.Map;

public class GiphyResponse {
    private Map<String, Object> data;
    private Map<String, String> meta;

    public GiphyResponse(Map<String, Object> data, Map<String, String> meta) {
        this.data = data;
        this.meta = meta;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
}
