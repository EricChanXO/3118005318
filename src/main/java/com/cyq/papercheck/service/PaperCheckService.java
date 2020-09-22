package com.cyq.papercheck.service;

import java.io.IOException;

/**
 * @author EricChan
 * @date 2020/9/22
 */
public interface PaperCheckService {

    /**
     * 论文查重接口
     *
     * @param path1 原文文件路径
     * @param path2 抄袭文件路径
     * @return 比对相似度
     */
    double check(String path1, String path2) throws IOException;
}
