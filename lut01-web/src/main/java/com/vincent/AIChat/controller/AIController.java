package com.vincent.AIChat.controller;

import com.vincent.common.Result;
import com.vincent.enums.ResultCodeEnum;
import com.vincent.AIChat.service.ZhipuAiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Resource
    private ZhipuAiService zhipuAiService;

    /** 大模型问答 */
    @PostMapping("/ask")
    public Result ask(@RequestBody Map<String, String> body) {
        String question = body.get("question");
        if (question == null || question.trim().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        try {
            String reply = zhipuAiService.ask(question);
            return Result.success(reply);   // ✅ data 字段就是回答文本
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
