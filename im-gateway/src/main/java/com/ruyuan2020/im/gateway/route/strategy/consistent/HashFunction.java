package com.ruyuan2020.im.gateway.route.strategy.consistent;

/**
 * 哈希函数
 *
 * @author case
 */
public interface HashFunction {

    /**
     * 通过key获取哈希值
     */
    long hash(String key);
}
