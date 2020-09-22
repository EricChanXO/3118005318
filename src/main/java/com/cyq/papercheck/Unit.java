package com.cyq.papercheck;

import com.cyq.papercheck.cs.CosineSimilarity;
import com.cyq.papercheck.exception.CommonException;
import com.cyq.papercheck.sim.Calculate;
import com.cyq.papercheck.sim.SimHash;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Unit {
    static double ans(String path1, String path2) throws IOException {
        // 被比较文本与比较文本
        StringBuilder text1 = new StringBuilder();
        StringBuilder text2 = new StringBuilder();

        // 文本输入 path1:原文路径 path2:抄袭版路径
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
            throw new CommonException("文本为空");
        }

        double answer;
        // 如果文本长度大于3000字，使用simhash算法; 否则采用余弦相似度算法
        if (text1.length() > 3000) {
            SimHash hash1 = new SimHash(text1.toString(), 64);
            SimHash hash2 = new SimHash(text2.toString(), 64);
            // 海明距离
            int hammingDistance = hash1.hammingDistance(hash2);
            answer = Calculate.getSimliar(hammingDistance);
        } else {
            // 余弦相似度算法
            answer = CosineSimilarity.getSimilarity(text1.toString(), text2.toString());
        }
        System.out.println("原文文件与需查重文件的相似度为:" + answer);
        return answer;
    }
}
