package com.github.zhanjixun.quickly;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Collectors扩展
 *
 * @author zhanjixun
 * @date 2020-09-21 16:25:01
 * @see java.util.stream.Collectors
 */
public class CollectorsExt {

    /**
     * 统计BigDecimal平均数
     *
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> Collector<T, ?, BigDecimal> averagingBigDecimal(Function<? super T, BigDecimal> mapper) {
        return Collector.of(() -> new BigDecimal[]{new BigDecimal(0), new BigDecimal(0)}, (a, t) -> {
            a[0] = a[0].add(mapper.apply(t));//累加
            a[1] = a[1].add(BigDecimal.ONE);//计算个数
        }, (a, b) -> {
            //分组合并 合并总和
            a[0] = a[0].add(b[0]);
            return a;
        }, a -> a[0].divide(a[1], MathContext.DECIMAL32));
    }
    
}
