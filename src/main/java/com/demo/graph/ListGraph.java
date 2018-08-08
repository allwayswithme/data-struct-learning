package com.demo.graph;


import java.util.LinkedList;
import java.util.Queue;

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
        this.adjList = new VertexNode[elements.length];
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

        if(beginIndex >= this.vertexNum || endIndex >= this.vertexNum){
            System.out.println("请检查beginIndex和endIndex");
        }

        EdgeNode firArc = this.adjList[beginIndex].firArc;//边的头结点

        EdgeNode currentEdgeNode = firArc;
        EdgeNode parentEdgeNode = firArc;
        while (currentEdgeNode != null){
            if(currentEdgeNode.adjIndex == endIndex){//已存储该指向信息，直接忽略本次添加有向边
                return;
            }
            parentEdgeNode = currentEdgeNode;
            currentEdgeNode = currentEdgeNode.nextArc;
        }
        currentEdgeNode = new EdgeNode();
        currentEdgeNode.adjIndex = endIndex;
        //如果是该顶点的第一条边，则直接新增头结点
        if(firArc == null)
            this.adjList[beginIndex].firArc = currentEdgeNode;
        else
            parentEdgeNode.nextArc = currentEdgeNode;

        this.directEdgeNum = this.directEdgeNum + 1;
    }

    /**
     * 深度优先搜索遍历，起始点为VertexNode[0]，支持连通图和非连通图
     */
    public void depthFirstTraversal(){

        //重置是否访问字段为false
        for (int i = 0; i < this.adjList.length;i++){
            this.adjList[i].isVisited = false;
        }

        //开始遍历
        for (int i = 0; i < this.adjList.length;i++){
            if(this.adjList[i].isVisited == false)
                DFS(this.adjList[i]);
        }

    }

    /**
     * 连通图或者单个顶点的深度遍历递归访问，退出递归的条件为所有与 v 有路径相通的顶点都被访问到
     */
    private void DFS(VertexNode currentNode){

        //访问节点
        if(currentNode.isVisited == false){
            System.out.println(currentNode.element);
            currentNode.isVisited = true;
        }

        EdgeNode firArc = currentNode.firArc;//取得顶点对应边的头指针
        if(firArc == null){//该顶点没有出度或者为单个顶点
           return;
        }
        //遍历该顶点 v 对应的链表,如果所有与 v 有路径相通的顶点都被访问到，则退出递归
        while (firArc != null){
            if(this.adjList[firArc.adjIndex].isVisited == false)//还有节点未访问，跳出链表遍历
                break;
            firArc = firArc.nextArc;
            if(firArc == null)//已遍历完毕，退出递归
                return;

        }

        EdgeNode p = firArc;
        while (p != null){
            DFS(this.adjList[p.adjIndex]);
            p = p.nextArc;
        }

    }

    /**
     * 广度优先搜索遍历，起始点为VertexNode[0]
     */
    public void breadthFirstTraversal(){

        //重置是否访问字段为false
        for (int i = 0; i < this.adjList.length;i++){
            this.adjList[i].isVisited = false;
        }

        //开始遍历
        for (int i = 0; i < this.adjList.length;i++){
            if(this.adjList[i].isVisited == false){
                BFS(this.adjList[i],i);
            }

        }

    }

    /**
     * 只支持连通图或者单个顶点的广度优先搜索遍历
     */
    private void BFS(VertexNode currentNode,int index){

        //访问该节点
        if(currentNode.isVisited == false){
            System.out.println(currentNode.element);
            currentNode.isVisited = true;
        }

        Queue<Integer> queue = new LinkedList();
        queue.add(index);
        while (!queue.isEmpty()){
            int currentIndex = queue.poll();
            EdgeNode p = this.adjList[currentIndex].firArc;
            while (p != null){
                if(this.adjList[p.adjIndex].isVisited == false){
                    System.out.println(this.adjList[p.adjIndex].element);
                    this.adjList[p.adjIndex].isVisited = true;
                    queue.add(p.adjIndex);
                }
                p = p.nextArc;
            }
        }

    }


    /**
     * 测试方法
     * 为了测试方便，将数字和index设置为一样。现实中，index和element的值可以不一样
     * @param args
     */
    public static void main(String[] args){
        ListGraph<Integer> graph = new ListGraph();
        Integer[] elements = {0,1,2,3,4,5,6};
        
        //添加节点
        graph.addElements(elements);
        //添加边
        graph.addDirectedEdge(6,1);
        graph.addDirectedEdge(6,3);
        graph.addDirectedEdge(6,5);
        graph.addDirectedEdge(6,4);
        graph.addDirectedEdge(4,6);

        //graph.depthFirstTraversal();
        graph.breadthFirstTraversal();

    }
}
