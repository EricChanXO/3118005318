package com.cyq.papercheck.service;

/**
 * @author EricChan
 * @date 2020/9/22
 */
public interface SimHashService {
    /**
     * SiMHash算法计算相似度
     *
     * @param x 海明距离
     * @return 相似度
     */
    double getSimliar(int x);
}
