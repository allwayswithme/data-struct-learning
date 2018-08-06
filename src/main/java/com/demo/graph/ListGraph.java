package com.demo.graph;


/**
 * 图的实现-邻接表存储结构
 */
public class ListGraph<E> {

    /**
     * 邻接表的顶点内部类
     */
    private class VertexNode<E> {
        private E element;
        private boolean isVisited;//遍历时使用，判断该点是否已经被访问
        private EdgeNode firArc;//指向第一条弧
    }

    /**
     * 邻接表的边内部类
     */
    private class EdgeNode {
        private int adjIndex;
        private double weight;//边的权重
        EdgeNode nextArc;//指向下一条弧
    }


    private int vertexNum = 0;//顶点的个数
    private int unDirectEdgeNum = 0;//无向边的个数
    private int directEdgeNum = 0;//有向边的个数
    private VertexNode[] adjList = null;//保存邻接表的头节点

    /**
     * 一次性添加所有元素，暂时不支持动态扩容
     * @param elements
     */
    public void addElements(E[] elements){
        if(this.adjList != null){
            System.out.println("只能一次性添加所有元素，请重新新建图");
        }
        this.adjList = new VertexNode[elements.length + 1];
        this.unDirectEdgeNum = 0;
        this.vertexNum = elements.length;

        for(int i = 0;i < elements.length;i++){
            VertexNode node = new VertexNode();
            this.adjList[i] = node;
            this.adjList[i].element = elements[i];
        }

    }

    /**
     * 添加无向图的边
     * @param beginIndex
     * @param endIndex
     */
    public void addUndirectedEdge(int beginIndex, int endIndex){
        this.addDirectedEdge(beginIndex,endIndex);
        this.addDirectedEdge(endIndex,beginIndex);

        this.unDirectEdgeNum = this.unDirectEdgeNum + 1;
        this.directEdgeNum = 0;
    }

    /**
     * 添加有向图的边
     * @param beginIndex
     * @param endIndex
     */
    public void addDirectedEdge(int beginIndex,int endIndex){

        EdgeNode firArc = this.adjList[beginIndex].firArc;
        while (firArc != null){
            firArc = firArc.nextArc;
            if(firArc.adjIndex == endIndex){//已存储该指向信息，直接忽略本次添加有向边
                return;
            }
        }
        firArc = new EdgeNode();
        firArc.adjIndex = endIndex;

        this.directEdgeNum = this.directEdgeNum + 1;
    }

    /**
     * 深度优先搜索遍历，起始点为VertexNode[0]
     */
    public void depthFirstTraversal(){

        //重置是否访问字段为false
        for (int i = 0; i <this.adjList.length;i++){
            this.adjList[i].isVisited = false;
        }

        //开始遍历
        DFS(this.adjList[0]);

    }

    /**
     * 深度遍历递归访问
     */
    private void DFS(VertexNode node){
        System.out.println(node.element);
        node.isVisited = true;

        EdgeNode p = node.firArc;//取得顶点对应边的头指针
        while (p != null){
            if(node.isVisited != true){
                DFS(this.adjList[p.adjIndex]);
            }
            p = p.nextArc;
        }
    }

    /**
     * 广度优先搜索遍历，起始点为VertexNode[0]
     */
    public void breadthFirstTraversal(){

    }


    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args){
        ListGraph<Integer> graph = new ListGraph();
        Integer[] elements = {0,1,2,3,4};
        
        //添加节点
        graph.addElements(elements);
        //添加边
        graph.addUndirectedEdge(0,1);
        graph.addUndirectedEdge(0,4);
        graph.addUndirectedEdge(1,2);
        graph.addUndirectedEdge(1,3);
        graph.addUndirectedEdge(2,3);
        graph.addUndirectedEdge(3,4);
        graph.addUndirectedEdge(1,4);

        graph.depthFirstTraversal();

        int[] a = new int[2];
        System.out.println("a[0]:" + a[0]);
        a[1] = 1;
        System.out.println("length:" + a.length);

    }


}