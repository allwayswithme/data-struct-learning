package com.demo.sort;

import java.util.Arrays;

/**
 * https://blog.csdn.net/u012483425/article/details/50409415
 * 插入排序
 */
public class InsertSort {

    public static void insertSort(int[] arr){

        if(arr==null||arr.length<=1){
            return;
        }

        int i,j,temp;

        for(i = 1;i < arr.length;i++){
            temp = arr[i];
            for(j = i-1;j>=0 && arr[j]>temp;j--)
                arr[j+1] = arr[j];
            arr[j+1] = temp;//因为前面j--了，所以这里要j+1
        }

    }

    public static void main(String[] args){
        int[] arr = {1,3,2,3,45,3,34,3,2,1};

        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 交换两个数字的值
     * @param a
     * @param b
     * @param arr
     */
    public static void swapValue(int a,int b,int[] arr){
        arr[a] = arr[a] + arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }

}
