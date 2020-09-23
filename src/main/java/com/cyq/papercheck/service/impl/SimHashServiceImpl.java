package com.cyq.papercheck.service.impl;

import com.cyq.papercheck.service.SimHashService;

/**
 * SimHash算法计算相似度
 *
 * @author cyq
 * @date 2020/9/22
 */
public class SimHashServiceImpl implements SimHashService {

    @Override
    public  double getSimliar(int x) {
        double part1 = Math.sqrt(2 * Math.PI * 0.16);

        double part2 = Math.pow(0.01 * x - 0.01, 2);

        double part3 = 2 * 0.0459 * 0.0459 * (-1.0);

        double form1 = 1 / part1;

        double form2 = Math.exp(part2 / part3);
        System.out.println((form1 * form2));
        return form1 * form2;
    }
}