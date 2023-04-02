package com.ruyuan2020.im.gateway.route.strategy.consistent;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.HashUtil;
import com.ruyuan2020.im.common.core.exception.SystemException;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash路由
 *
 * @author case
 */
public class ConsistentHashRoute<T extends Node> {

    /**
     * Tree实现一个虚拟节点环形结构
     */
    private final SortedMap<Long, VirtualNode<T>> ring = new TreeMap<>();

    /**
     * 哈希函数
     */
    private final HashFunction hashFunction;

    public ConsistentHashRoute(Collection<T> physicalNodes, int virtualNodeCount) {
        this(physicalNodes, virtualNodeCount, new Murmur32Hash());
    }

    /**
     * @param physicalNodes    物理节点集合
     * @param virtualNodeCount 虚拟节点数量
     * @param hashFunction     哈希函数
     */
    public ConsistentHashRoute(Collection<T> physicalNodes, int virtualNodeCount, HashFunction hashFunction) {
        if (hashFunction == null) {
            throw new SystemException("哈希函数不能为空");
        }
        this.hashFunction = hashFunction;
        // 如果物理节点不为空
        if (CollUtil.isNotEmpty(physicalNodes)) {
            // 遍历物理节点，把物理节点添加到虚拟的哈希环中
            for (T physicalNode : physicalNodes) {
                addNode(physicalNode, virtualNodeCount);
            }
        }
    }

    /**
     * 添加物理节点到虚拟环中
     *
     * @param physicalNode     物理节点
     * @param virtualNodeCount the number of virtual node of the physical node. Value should be greater than or equals to 0
     */
    public void addNode(T physicalNode, int virtualNodeCount) {
        if (virtualNodeCount < 0) throw new SystemException("虚拟节点数不能小于0");
        // 获取物理节点副本数
        long replicas = getReplicas(physicalNode);
        for (int i = 0; i < virtualNodeCount; i++) {
            VirtualNode<T> virtualNode = new VirtualNode<>(physicalNode, i + replicas);
            ring.put(hashFunction.hash(virtualNode.getKey()), virtualNode);
        }
    }

    /**
     * 从环中删除物理节点
     */
    public void removeNode(T physicalNode) {
        Iterator<Long> it = ring.keySet().iterator();
        while (it.hasNext()) {
            Long key = it.next();
            VirtualNode<T> virtualNode = ring.get(key);
            if (virtualNode.isVirtualNodeOf(physicalNode)) {
                it.remove();
            }
        }
    }

    public T routeNode(String objectKey) {
        if (ring.isEmpty()) {
            return null;
        }
        Long hashVal = hashFunction.hash(objectKey);
        SortedMap<Long, VirtualNode<T>> tailMap = ring.tailMap(hashVal);
        Long nodeHashVal = !tailMap.isEmpty() ? tailMap.firstKey() : ring.firstKey();
        return ring.get(nodeHashVal).getPhysicalNode();
    }


    /**
     * 获取已存在环中的物理节点副本数
     */
    public long getReplicas(T physicalNode) {
        return ring.values().stream().filter(virtualNode -> virtualNode.isVirtualNodeOf(physicalNode)).count();
    }


    /**
     * 默认实现的哈希函数
     */
    private static class Murmur32Hash implements HashFunction {

        @Override
        public long hash(String key) {
            return HashUtil.murmur32(key.getBytes());
        }
    }

}
