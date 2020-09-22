package com.cyq.papercheck;

import com.cyq.papercheck.exception.CommonException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ���������
 *
 * @author cyq
 * @date 2020/9/22
 */
public class PaperCheckMain {

    public static void main(String[] args) {
        double answer;
        try {
            // args[0]:ԭ���ļ� args[1]:��Ϯ���ļ�
            answer = Unit.ans(args[0], args[1]);
        } catch (IOException e) {
            throw new CommonException("IO�쳣");
        }

        //���ļ�
        String answerText = args[2];
        File file = new File(answerText);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            fw.write(String.valueOf(answer));
            fw.close();
        } catch (IOException e) {
            throw new CommonException("д����ļ����̷�������");
        }
    }

}