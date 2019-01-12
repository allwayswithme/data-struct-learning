package com.demo.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的实现 -邻接矩阵存储结构
 */
public class MatrixGraph<E> implements DirectGraph<E>,UnDirectGraph<E>  {

    private class VertexNode<E>{
        private E element;
        private boolean isVisited;
    }

    private VertexNode[] adjList;//邻接矩阵的点
    private int[][] edges;//邻接矩阵的边的集合


    public void addDirectedEdge(int beginIndex, int endIndex) {
        edges[beginIndex][endIndex] = 1;
    }

    public void addUndirectedEdge(int beginIndex, int endIndex) {
        addDirectedEdge(beginIndex,endIndex);
        addDirectedEdge(endIndex,beginIndex);
    }

    public void addElements(E[] elements) {
        if(this.adjList != null){
            System.out.println("只能一次性添加所有元素，请重新新建图");
        }
        this.adjList = new VertexNode[elements.length];
        for(int i = 0;i < elements.length;i++){
            VertexNode node = new VertexNode();
            this.adjList[i] = node;
            this.adjList[i].element = elements[i];
        }

        if(this.edges == null){
            edges = new int[adjList.length][adjList.length];
        }
    }

    public void depthFirstTraversal() {
        //重置访问状态为false
        for(int i = 0;i < this.adjList.length ;i++){
            this.adjList[i].isVisited = false;
        }

        Queue<Integer> queue = new LinkedList();
        queue.add(0);//默认从零开始遍历

        while (!queue.isEmpty()){
            int currentIndex = queue.poll();
            System.out.println(this.adjList[currentIndex].element);
            this.adjList[currentIndex].isVisited = true;
            //邻接表存储的图，边为一个方阵，故直接用顶点数组的长度做循环即可
            for(int i = 0;i < this.adjList.length ;i++){
                if(this.edges[currentIndex][i] == 1 && this.adjList[i].isVisited == false){
                    queue.add(i);

                }
            }
        }


    }

    public void breadthFirstTraversal() {

        //重置访问状态为false
        for(int i = 0;i < this.adjList.length ;i++){
           this.adjList[i].isVisited = false;
        }

        Queue<Integer> queue = new LinkedList();
        queue.add(0);//默认从零开始遍历

        while (!queue.isEmpty()){
            int currentIndex = queue.poll();
            System.out.println(this.adjList[currentIndex].element);
            this.adjList[currentIndex].isVisited = true;
            //邻接表存储的图，边为一个方阵，故直接用顶点数组的长度做循环即可
            for(int i = 0;i < this.adjList.length ;i++){
                if(this.edges[currentIndex][i] == 1 && this.adjList[i].isVisited == false){
                    queue.add(i);
                }
            }
        }

    }

    public static void main(String[] args){
        MatrixGraph<Integer> graph = new MatrixGraph();
        Integer[] elements = {0,1,2,3,4,5,6};

        //添加节点
        graph.addElements(elements);
        //添加边
        graph.addDirectedEdge(1,6);
        graph.addDirectedEdge(6,3);
        graph.addDirectedEdge(6,2);
        graph.addDirectedEdge(6,4);
        graph.addDirectedEdge(4,6);
        graph.addDirectedEdge(1,5);
        graph.addDirectedEdge(0,1);
        graph.addDirectedEdge(6,1);
        graph.addDirectedEdge(1,0);

        graph.depthFirstTraversal();
        //graph.breadthFirstTraversal();

    }
}
