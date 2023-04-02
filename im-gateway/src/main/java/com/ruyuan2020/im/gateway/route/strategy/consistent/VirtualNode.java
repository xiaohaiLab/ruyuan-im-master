package com.ruyuan2020.im.gateway.route.strategy.consistent;

/**
 * 虚拟节点
 *
 * @author case
 */
public class VirtualNode<T extends Node> implements Node {

    /**
     * 物理节点
     */
    final T physicalNode;

    /**
     * 副本索引
     */
    final long replicaIndex;

    public VirtualNode(T physicalNode, long replicaIndex) {
        this.replicaIndex = replicaIndex;
        this.physicalNode = physicalNode;
    }

    @Override
    public String getKey() {
        return physicalNode.getKey() + "-" + replicaIndex;
    }

    /**
     * 判断是否为物理节点
     */
    public boolean isVirtualNodeOf(T physicalNode) {
        return physicalNode.getKey().equals(physicalNode.getKey());
    }

    /**
     * 获取物理节点
     */
    public T getPhysicalNode() {
        return physicalNode;
    }
}
