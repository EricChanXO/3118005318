package com.cyq.papercheck.service;

import java.io.IOException;

/**
 * @author EricChan
 * @date 2020/9/22
 */
public interface PaperCheckService {

    /**
     * ���Ĳ��ؽӿ�
     *
     * @param path1 ԭ���ļ�·��
     * @param path2 ��Ϯ�ļ�·��
     * @return �ȶ����ƶ�
     */
    double check(String path1, String path2) throws IOException;
}
