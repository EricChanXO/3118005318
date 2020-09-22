package com.cyq.papercheck.sim;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

/**
 * SimHash�㷨
 *
 * @author cyq
 * @date 2020/9/22
 */
public class SimHash {

    private final String token;

    public BigInteger intSimHash;

    private final int hashbits;

    public SimHash(String token, int hashbits) throws IOException {
        this.token = token;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }

    public BigInteger simHash() throws IOException {
        // ������������/����
        int[] v = new int[this.hashbits];
        // 1�����ı�ȥ����ʽ��, �ִ�.
        StringReader sr = new StringReader(token);
        IKSegmenter ik = new IKSegmenter(sr, true);
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            // 2����ÿһ���ִ�hashΪһ��̶����ȵ�����.���� 64bit ��һ������.
            BigInteger t = this.hash(lex.getLexemeText());
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3������һ������Ϊ64����������(����Ҫ����64λ������ָ��,Ҳ��������������),
                // ��ÿһ���ִ�hash������н����ж�,�����1000...1,��ô����ĵ�һλ��ĩβһλ��1,
                // �м��62λ��һ,Ҳ����˵,��1��1,��0��1.һֱ�������еķִ�hash����ȫ���ж����.
                if (t.and(bitmask).signum() != 0) {
                    // �����Ǽ��������ĵ�������������������
                    // ����ʵ��ʹ������Ҫ +- Ȩ�أ������Ǽ򵥵� +1/-1��
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        BigInteger fingerprint = new BigInteger("0");
        StringBuilder simHashBuilder = new StringBuilder();
        for (int i = 0; i < this.hashbits; i++) {
            // 4��������������ж�,����0�ļ�Ϊ1,С�ڵ���0�ļ�Ϊ0,�õ�һ�� 64bit ������ָ��/ǩ��.
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuilder.append("1");
            } else {
                simHashBuilder.append("0");
            }
        }
        return fingerprint;
    }

    public BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    // ��������
    public int hammingDistance(SimHash other) {
        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int total = 0;

        // ͳ��x�ж�����λ��Ϊ1�ĸ���
        while (x.signum() != 0) {
            total += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return total;
    }
}
