package com.demo.graph;

public interface DirectGraph<E> extends Graph<E> {

    /**
     * 添加有向图的边
     */
    void addDirectedEdge(int beginIndex, int endIndex);

}
