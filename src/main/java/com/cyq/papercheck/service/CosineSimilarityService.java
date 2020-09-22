package com.cyq.papercheck.service;

import java.io.IOException;

/**
 * �������ƶ��㷨
 *
 * @author EricChan
 * @date 2020/9/22
 */
public interface CosineSimilarityService {
    /**
     * ͨ���������ƶ��㷨�������ƶ�
     *
     * @param text1 ԭ���ı�
     * @param text2 ��Ϯ�ı�
     * @return ���ƶ�
     */
    double getSimilarity(String text1, String text2) throws IOException;
}
