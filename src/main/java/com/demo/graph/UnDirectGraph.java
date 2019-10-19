package com.demo.graph;

public interface UnDirectGraph<E> extends Graph<E> {

    /**
     * 添加无向图的边
     * @param beginIndex 起点index
     * @param endIndex   终点index
     */
    void addUndirectedEdge(int beginIndex, int endIndex);


}
