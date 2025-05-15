package com.vincent.AIChat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 与智谱大模型对接
 */
@Service
public class ZhipuAiService {

    private static final String API_URL =
            "https://open.bigmodel.cn/api/paas/v4/chat/completions";

    /** 从 application.yml 读取，避免硬编码泄漏 **/
    @Value("${zhipu.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 向大模型提问
     * @return 大模型回复内容
     */
    public String ask(String contextText) {
        List<Map<String, String>> messages = new ArrayList<>();

        // 简单处理：把文本按“用户：xxx\nAI：yyy”交替拆分
        String[] lines = contextText.split("\n");
        for (String line : lines) {
            if (line.startsWith("用户：")) {
                messages.add(Map.of("role", "user", "content", line.substring(3)));
            } else if (line.startsWith("AI：")) {
                messages.add(Map.of("role", "assistant", "content", line.substring(3)));
            }
        }

        // 最后一句是新提问的内容
        if (!messages.isEmpty()) {
            String last = messages.get(messages.size() - 1).get("content");
            if (last != null) {
                messages.add(Map.of("role", "user", "content", last));
            }
        }

        Map<String, Object> body = Map.of(
                "model", "glm-4",
                "messages", messages
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> resp = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) resp.getBody().get("choices");
        Map messageData = (Map) choices.get(0).get("message");
        return (String) messageData.get("content");
    }

}
