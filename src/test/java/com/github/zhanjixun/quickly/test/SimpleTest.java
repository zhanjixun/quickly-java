package com.github.zhanjixun.quickly.test;

import com.github.zhanjixun.quickly.CollectorsExt;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanjixun
 * @date 2020-09-21 16:39:40
 */
public class SimpleTest {

    @Test
    public void test1() {
        List<BigDecimal> bigDecimals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bigDecimals.add(new BigDecimal(i + 1));
        }
        System.out.println(bigDecimals);
        BigDecimal avg = bigDecimals.stream().collect(CollectorsExt.averagingBigDecimal(d -> d));
        System.out.println(avg);
    }
}
