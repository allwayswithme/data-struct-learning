package com.demo.process;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * https://blog.csdn.net/mm_bit/article/details/50010623
 * 生产者消费者模型（信号量实现）
 */
public class SemaphoreModel {

    final Semaphore notFull = new Semaphore(10);//未满锁
    final Semaphore notEmpty = new Semaphore(0);//非空锁
    final Semaphore mutex = new Semaphore(1);//初始值为1时就变成互斥锁（mutex）
    private LinkedList<Object> storage = new LinkedList<Object>();

    class Consumer implements Runnable{

        public void run() {
            while (true){
                try {
                    Thread.sleep(1500);
                    notEmpty.acquire();
                    mutex.acquire(); //因为linkList不是线程安全的，为了保证同一时刻只有一个消费者取商品，需要有这个信号量进行限制
                    storage.removeFirst();
                    System.out.println(Thread.currentThread().getName() + ",库存总数storage.size():" + storage.size()+"。消费者消费了一个库存");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //这个顺序似乎是不能变的，一旦变化可能会导致线程不安全
                    mutex.release();
                    notFull.release();

                }

            }

        }
    }
    class Producer implements  Runnable{

        public void run() {
            while (true){
                try {
                    Thread.sleep(500);
                    notFull.acquire();
                    mutex.acquire();
                    storage.add(new Object());
                    System.out.println(Thread.currentThread().getName() + ",库存总数storage.size():" + storage.size()+"。生产者生产了一个库存");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    mutex.release();
                    notEmpty.release();
                }

            }
        }
    }

    public static void main(String[] args){
        SemaphoreModel consumerProducerModel = new SemaphoreModel();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Producer()).start();
        new Thread(consumerProducerModel.new Producer()).start();
        new Thread(consumerProducerModel.new Producer()).start();

    }

}
