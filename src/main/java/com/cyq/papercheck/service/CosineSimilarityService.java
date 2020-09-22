package com.cyq.papercheck.service;

import java.io.IOException;

/**
 * 余弦相似度算法
 *
 * @author EricChan
 * @date 2020/9/22
 */
public interface CosineSimilarityService {
    /**
     * 通过余弦相似度算法计算相似度
     *
     * @param text1 原文文本
     * @param text2 抄袭文本
     * @return 相似度
     */
    double getSimilarity(String text1, String text2) throws IOException;
}
