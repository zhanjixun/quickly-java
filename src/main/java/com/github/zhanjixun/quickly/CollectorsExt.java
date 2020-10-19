package com.github.zhanjixun.quickly;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
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

    /**
     * 分组收集,只取值,不要分组键
     *
     * @param groupBy 分组标记
     * @param mapper  组内转换
     * @param <T>
     * @param <K>
     * @param <R>
     * @return
     */
    public static <T, K, R> Collector<T, ?, List<R>> groupCollect(Function<? super T, ? extends K> groupBy, Function<List<T>, R> mapper) {
        return Collector.of(() -> new HashMap<K, List<T>>(), (map, t) -> {
            K key = groupBy.apply(t);
            if (map.containsKey(key)) {
                map.get(key).add(t);
            } else {
                map.put(key, new ArrayList<>(Collections.singleton(t)));
            }
        }, (map1, map2) -> {
            HashMap<K, List<T>> resultMap = new HashMap<>(map1);
            for (Map.Entry<K, List<T>> entry : map2.entrySet()) {
                if (resultMap.containsKey(entry.getKey())) {
                    map2.get(entry.getKey()).addAll(entry.getValue());
                } else {
                    resultMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
                }
            }
            return resultMap;
        }, map -> {
            List<R> result = new ArrayList<>();
            Collection<List<T>> values = map.values();
            for (List<T> value : values) {
                result.add(mapper.apply(value));
            }
            return result;
        });
    }

}
