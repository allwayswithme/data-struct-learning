package com.demo.tree;

import java.util.Stack;

/**
 * http://www.cnblogs.com/hapjin/p/5679482.html
 * https://zhuanlan.zhihu.com/p/34899732
 * 平衡二叉树
 * 由于平衡二叉树为有序树，所以泛型必须实现Comparable（可比较接口）
 * Integer，Long,String默认已实现了Comparable接口
 * */
public class AvlTree<E extends Comparable<E>> {

    /**
     * 节点定义
     */
    private class AvlTreeNode<E> {
        private E element;
        private AvlTreeNode<E> leftChild = null;
        private AvlTreeNode<E> rightChild = null;

        /**
         * 相比于二叉查找树，平衡二叉树多了height字段,叶子节点的高度为0，空的叶子节点的高度为-1
         * 其他节点的高度为其左右子树的高度的最大值 + 1
         */
        private int height;

    }

    /**
     * 根节点
     */
    private AvlTreeNode<E> root = null;

    /**
     * 树的节点数目
     */
    private int size;

    /**
     * 返回二叉树的节点数目
     * @return
     */
    public int size(){
        return this.size;
    }

  /*********************************************  平衡二叉树特色代码begin******************************************/

    /**
     * 传入树或者子树的根节点，得到相应树的高度
     * @return
     */
    public int computeHeight(AvlTreeNode<E> rootTreeNode){
        if(rootTreeNode == null){
            return -1;
        }else {
            return Math.max(computeHeight(rootTreeNode.leftChild), computeHeight(rootTreeNode.rightChild)) + 1;
        }
    }

    /**
     * 计算树的平衡因子(平衡因子 = 树的左子树高度 - 树的右子树高度)
     * 当树的平衡因子大于 1 或者小于 -1 时，触发树的再平衡操作，平衡因子的正负可以用来区分左旋和右旋
     */
    public int getTreeBalanceFactor(AvlTreeNode<E> rootTreeNode){
        if(rootTreeNode == null){//如果传入来的子树根节点为null，那么该根节点为null的子树高度为-1
            return -1;
        }else {

            int leftHeight = rootTreeNode.leftChild == null? -1 : rootTreeNode.leftChild.height;
            int rightHeight = rootTreeNode.rightChild == null? -1 : rootTreeNode.rightChild.height;

            return leftHeight - rightHeight;
        }
    }

    /**
     *  平衡化操作
     *  传入节点，如果树是平衡的，则不进行操作，否则进行左旋转和右旋转，使树平衡
     *  @return 返回平衡的子树根节点
     */
    AvlTreeNode treeRebalance(AvlTreeNode root){

        int factor = getTreeBalanceFactor(root);
        if(factor > 1 && getTreeBalanceFactor(root.leftChild) > 0 )//LL
            return rightRotation(root);
        else if(factor > 1 && getTreeBalanceFactor(root.leftChild) <= 0) {//LR
            root.leftChild = leftRotation(root.leftChild);
            return rightRotation(root);
        }
        else if(factor < -1 && getTreeBalanceFactor(root.rightChild) <= 0){//RR
            return leftRotation(root);
        }
        else if(factor < -1 && getTreeBalanceFactor(root.rightChild) > 0){//RL
            root.rightChild = rightRotation(root.rightChild);
            return leftRotation(root);
        }

        return root;
    }

    /**
     * 左旋转,传入最小不平衡子树的根节点
     * 返回新的子树根节点
     */
    private AvlTreeNode<E> leftRotation(AvlTreeNode<E> root){
        AvlTreeNode right = root.rightChild;

        root.rightChild = right.leftChild;
        right.leftChild = root;

        right.height = Math.max(computeHeight(right.leftChild), computeHeight(right.rightChild)) + 1;
        root.height = Math.max(computeHeight(root.leftChild), computeHeight(root.rightChild)) + 1;

        return right;

    }

    /**
     * 右旋转,传入最小不平衡子树的根节点
     * 返回新的子树根节点
     */
    private AvlTreeNode<E> rightRotation(AvlTreeNode<E> root){
        AvlTreeNode left = root.leftChild;

        root.leftChild = left.rightChild;//将要被抛弃的结点连接为旋转后的root的左孩子
        left.rightChild = root;//调换父子关系

        //重新计算调换后的父子节点的高度
        left.height = Math.max(computeHeight(left.leftChild), computeHeight(left.rightChild)) + 1;
        root.height = Math.max(computeHeight(root.leftChild), computeHeight(root.rightChild)) + 1;

        return left;
    }

    /**
     * 添加平衡二叉树的值,如果元素值比较相等，则添加失败
     * 可用递归，迭代方式做，本次使用迭代方式做
     */
    public boolean add(E element){

        AvlTreeNode<E> treeNode = new AvlTreeNode();
        treeNode.element = element;
        treeNode.height = computeHeight(treeNode);

        //根节点为空，将新增的节点作为根节点
        if(this.root == null){
            this.root = treeNode;
            this.size = 1;
            return true;
        }

        int compareFlag = 0;//比较标志,0为相等，1为大于,-1为小于
        AvlTreeNode<E> currentNode = this.root;

        //递归算法改写为迭代时，很多时候需要用到栈stack结构
        Stack<AvlTreeNode<E>> stack = new Stack();

        while (currentNode!= null){

            stack.push(currentNode);

            compareFlag = element.compareTo(currentNode.element);
            if(compareFlag < 0){
                currentNode = currentNode.leftChild;
            }else if(compareFlag > 0){
                currentNode = currentNode.rightChild;
            }else {
                return false;//当有重复元素时，直接添加失败
            }
        }

        if(compareFlag < 0){
           stack.peek().leftChild = treeNode;//peek()方法表示查看栈顶对象而不移除它
        }else if(compareFlag > 0){
           stack.peek().rightChild = treeNode;
        }else {
           return false;
        }

        //重新计算树的高度
        while (!stack.isEmpty()){
            AvlTreeNode<E> node = stack.pop();
            node.height = computeHeight(node);

            //判断当前二叉子树是否平衡，如果不平衡，需要进行旋转
            AvlTreeNode<E> root = treeRebalance(node);
            if(root != node){
                if(stack.isEmpty()){//返回的点是根节点，不平衡子树为原树本身
                    this.root = root;
                    break;
                }
                if(stack.peek().element.compareTo(root.element) < 0){
                    stack.peek().rightChild = root;
                }else if(stack.peek().element.compareTo(root.element) > 0){
                    stack.peek().leftChild = root;
                }
            }
        }

        this.size++;

        return true;
    }

    public AvlTreeNode<E> queryTreeNode(E element){

        int compareFlag;//比较标志,0为相等，1为大于,-1为小于
        AvlTreeNode<E> currentNode = this.root;

        while (currentNode!= null){
            compareFlag = element.compareTo(currentNode.element);
            if(compareFlag < 0){
                currentNode = currentNode.leftChild;
            }else if(compareFlag > 0){
                currentNode = currentNode.rightChild;
            }else {
                return currentNode;
            }
        }
        System.out.println("查找不到相应的元素，请检查");
        return null;
    }

    /**
     * 删除平衡二叉树的值
     */
    public void remove(E element){

    }


    /**
     * 先序遍历
     */
    public void preOrderTraversal(){

        System.out.println("先序遍历start");

        AvlTreeNode<E> currentNode = this.root;
        Stack<AvlTreeNode> stack = new Stack();
        AvlTreeNode<E> temp;

        while (currentNode != null || !stack.isEmpty()){
            while (currentNode != null){
                System.out.println(currentNode.element);
                stack.push(currentNode);
                currentNode = currentNode.leftChild;
            }
            if(!stack.isEmpty()) {
                temp = stack.pop();
                currentNode = temp.rightChild;
            }
        }
        System.out.println("先序遍历end");

    }

    /**
     * 中序遍历
     */
    public void inOrderTraversal(){
        System.out.println("中序遍历start");

        Stack<AvlTreeNode> stack = new Stack();
        AvlTreeNode<E> currentNode = this.root;
        AvlTreeNode<E> temp;

        while (currentNode != null || !stack.isEmpty()){
            //遍历左节点
            while (currentNode != null){
                stack.push(currentNode);
                currentNode = currentNode.leftChild;
            }
            if(!stack.isEmpty()){
                //输出中间节点
                temp = stack.pop();
                System.out.println(temp.element);
                currentNode = temp.rightChild;
            }
        }
        System.out.println("中序遍历end");
    }


    /**
     * 后序遍历,递归实现
     */
    public void postOrderTraversal(){
        this.subPostOrderTraversal(this.root);
    }
    //后序遍历的递归子方法
    private void subPostOrderTraversal(AvlTreeNode<E> currentNode){
        if(currentNode == null)
            return;
        subPostOrderTraversal(currentNode.leftChild);
        subPostOrderTraversal(currentNode.rightChild);
        System.out.println(currentNode.element);
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args){
        AvlTree<Integer> avlTree = new AvlTree();
        avlTree.add(50);
        avlTree.add(1);
        avlTree.add(66);
        avlTree.add(2);
        avlTree.add(3);

    /*    avlTree.add(5);
        avlTree.add(0);
        avlTree.add(67);
        avlTree.add(68);
        avlTree.add(100);*/

        if(avlTree.root != null){
            //avlTree.inOrderTraversal();
            avlTree.preOrderTraversal();
            //avlTree.postOrderTraversal();
        }

    }
}
