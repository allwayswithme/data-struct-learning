package com.demo.sort;

import java.util.Arrays;

/**
 * http://blog.51cto.com/flyingcat2013/1281614
 * 快速排序算法
 */
public class QuickSort{

    public static void quickSort(int[] arr){
        qsort(arr, 0, arr.length-1);
    }

    private static void qsort(int[] arr, int low, int high){
        if (low < high){
            int pivot=partition(arr, low, high);        //将数组分为两部分
            qsort(arr, low, pivot-1);                   //递归排序左子数组
            qsort(arr, pivot+1, high);                  //递归排序右子数组
        }
    }

    private static int partition(int[] arr, int low, int high){
        int p = arr[low];
        while (high>low){
            while (high>low && arr[high]>=p) high--;
            arr[low] = arr[high];//交换一个数组坐标为高位的，但数值比基准数小的数到低位（第一次循环时low为基准数的坐标）
            while (high>low && arr[low]<=p) low++;
            arr[high] = arr[low];
        }

        System.out.println(high == low);
        arr[low] = p;
        return low;

    }

    public static void main(String[] args){
        int[] arr = {1,3,2,5,2,5,2,56,50,20,12,125,125,12,20,50,0};

        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
