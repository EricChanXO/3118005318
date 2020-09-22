package com.cyq.papercheck;

import com.cyq.papercheck.exception.CommonException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 主程序入口
 *
 * @author cyq
 * @date 2020/9/22
 */
public class PaperCheckMain {

    public static void main(String[] args) {
        double answer;
        try {
            // args[0]:原文文件 args[1]:抄袭版文件
            answer = Unit.ans(args[0], args[1]);
        } catch (IOException e) {
            throw new CommonException("IO异常");
        }

        //答案文件
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
            throw new CommonException("写入答案文件过程发生错误");
        }
    }

}