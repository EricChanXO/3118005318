package com.cyq.papercheck.service;

import com.cyq.papercheck.service.impl.CosineSimilarityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CosineSimilarityServiceTest {

    @Test
    public void getSimilarity() {
        CosineSimilarityService cosineSimilarityService = new CosineSimilarityServiceImpl();
        String str1 = "今天是星期天，天气晴，今天晚上我要去看电影。";
        String str2 = "今天是周天，天气晴朗，我晚上要去看电影。";
        String str3 = "";
        try {
            Assertions.assertNotEquals(cosineSimilarityService.getSimilarity(str1, str2), 1.0);
            Assertions.assertNotEquals(cosineSimilarityService.getSimilarity(str2, str3), 1.0);
            Assertions.assertEquals(cosineSimilarityService.getSimilarity(str2, str3), 0.0);
            Assertions.assertEquals(cosineSimilarityService.getSimilarity(str1, str3), 0.0);
        } catch (IOException e) {
            Assertions.assertEquals(e.getClass(), IOException.class);
        }
    }
}