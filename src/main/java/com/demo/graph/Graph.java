package com.demo.graph;

public interface Graph<E> {

    /**
     * 一次性添加所有元素，暂时不支持动态扩容
     * @param elements  元素数组
     */
    void addElements(E[] elements);

    /**
     * 深度优先搜索遍历，起始点为VertexNode[0]，支持连通图和非连通图
     */
    void depthFirstTraversal();


    /**
     * 广度优先搜索遍历，起始点为VertexNode[0]
     */
    void breadthFirstTraversal();


}
