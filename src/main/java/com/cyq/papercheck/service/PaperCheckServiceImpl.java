package com.cyq.papercheck.service;

import com.cyq.papercheck.exception.CommonException;
import com.cyq.papercheck.sim.SimHash;

import java.io.*;

public class PaperCheckServiceImpl implements PaperCheckService{

    @Override
    public double check(String path1, String path2) throws IOException {
        // ���Ƚ��ı���Ƚ��ı�
        StringBuilder text1 = new StringBuilder();
        StringBuilder text2 = new StringBuilder();

        // �ı����� path1:ԭ��·�� path2:��Ϯ��·��
        BufferedReader in1 = new BufferedReader(new InputStreamReader(new FileInputStream(path1), "UTF8"));
        BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(path2), "UTF8"));
        String str;
        while ((str = in1.readLine()) != null) {
            text1.append(str);
        }
        while ((str = in2.readLine()) != null) {
            text2.append(str);
        }
        in1.close();
        in2.close();

        if (text1.length() == 0 || text2.length() == 0) {
            throw new CommonException("�ı�Ϊ��");
        }

        double answer;
        // ����ı����ȴ���3000�֣�ʹ��simhash�㷨; ��������������ƶ��㷨
        if (text1.length() > 3000) {
            SimHash hash1 = new SimHash(text1.toString(), 64);
            SimHash hash2 = new SimHash(text2.toString(), 64);
            // ��������
            int hammingDistance = hash1.hammingDistance(hash2);
            // SimHash�㷨
            SimHashService simHashService = new SimHashServiceImpl();
            answer = simHashService.getSimliar(hammingDistance);
        } else {
            // �������ƶ��㷨
            CosineSimilarityService cosineSimilarityService = new CosineSimilarityServiceImpl();
            answer = cosineSimilarityService.getSimilarity(text1.toString(), text2.toString());
        }
        System.out.println("ԭ���ļ���������ļ������ƶ�Ϊ:" + answer);
        return answer;
    }
}
