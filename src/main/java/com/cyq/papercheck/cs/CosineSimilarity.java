package com.cyq.papercheck.cs;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * 余弦相似度算法
 *
 * @author cyq
 * @date 2020/9/21
 */
public class CosineSimilarity {

    private static String news;

    static public double getSimilarity(String t1, String t2) throws IOException {
        //词出现的位置
        Map<String, Vector<Integer>> offset1 = new TreeMap<String, Vector<Integer>>();
        //按键值排序
        Map<String, Vector<Integer>> offset2 = new TreeMap<String, Vector<Integer>>();
        int pos = 0;
        StringReader sr1 = new StringReader(t1);
        IKSegmenter ik1 = new IKSegmenter(sr1, true);
        Lexeme lex;
        while ((lex = ik1.next()) != null) {
            component(lex);
            if (offset1.get(news) == null) {
                Vector<Integer> off = new Vector<Integer>(100);
                off.add(pos);
                offset1.put(news, off);
            }
            offset1.get(news).add(pos);
            pos++;
        }

        StringReader sr2 = new StringReader(t2);
        IKSegmenter ik2 = new IKSegmenter(sr2, true);
        pos = 0;
        while ((lex = ik2.next()) != null) {
            component(lex);
            if (offset2.get(news) == null) {
                Vector<Integer> off = new Vector<Integer>(100);
                off.add(pos);
                offset2.put(news, off);
            }
            offset2.get(news).add(pos);
            pos++;
        }

        int cnt = 0;
        double up, down1, down2, sum = 0;
        for (String key : offset1.keySet()) {
            Vector<Integer> off1 = offset1.get(key);
            Vector<Integer> off2 = offset2.get(key);
            if (off2 != null) {
                up = down1 = down2 = 0;
                for (int i = 0; i < off1.size() && i < off2.size(); i++) {
                    up += off1.elementAt(i) * off2.elementAt(i);
                    down1 += off1.elementAt(i) * off1.elementAt(i);
                    down2 += off2.elementAt(i) * off2.elementAt(i);
                }
                down1 = Math.sqrt(down1);
                down2 = Math.sqrt(down2);
                double down = down1 * down2;
                if (down != 0) {
                    sum += up / down;
                }
            }
            cnt++;
        }
        return sum / cnt;
    }

    private static void component(Lexeme lex) {
        String s = lex.getLexemeText();
        news = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (String.valueOf(c).matches("[\u4e00-\u9fa5]")) {
                news += c;
            }
        }
    }
}