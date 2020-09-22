package com.cyq.papercheck.sim;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

/**
 * SimHash算法
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
        // 定义特征向量/数组
        int[] v = new int[this.hashbits];
        // 1、将文本去掉格式后, 分词.
        StringReader sr = new StringReader(token);
        IKSegmenter ik = new IKSegmenter(sr, true);
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(lex.getLexemeText());
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    // 这里实际使用中需要 +- 权重，而不是简单的 +1/-1，
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        BigInteger fingerprint = new BigInteger("0");
        StringBuilder simHashBuilder = new StringBuilder();
        for (int i = 0; i < this.hashbits; i++) {
            // 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
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

    // 海明距离
    public int hammingDistance(SimHash other) {
        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int total = 0;

        // 统计x中二进制位数为1的个数
        while (x.signum() != 0) {
            total += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return total;
    }
}
