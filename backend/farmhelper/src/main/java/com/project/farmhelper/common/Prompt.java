package com.project.farmhelper.common;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Getter
@Component
public class Prompt {

    @Value("${openai.api.key}")
    private String gptApiKey;

    @Value("${openai.api.url}")
    private String gptUrl;

    public List<String> getBestCropsFromGPT(List<String> cropNames) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String cropList = String.join(", ", cropNames);
        String prompt = "다음 작물들 중 대한민국 날씨와 계절에 알맞는 10개의 작물을 추천해줘 이유는 " +
                "필요 없어 그냥 작물 이름만 알려줘 " +
                "(딸기,  블루베리,  사과,  애호박,  오이,  옥수수,  토마토,  포도,  호박,  시금치) 이런 형태로: " + cropList;

        MediaType mediaType = MediaType.parse("application/json");
        String json = "{" +
                "\"model\": \"gpt-3.5-turbo\"," +
                "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]," +
                "\"max_tokens\": 150" +
                "}";

        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(gptUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + gptApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            String content = jsonObject.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            return Arrays.asList(content.split(","));
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}