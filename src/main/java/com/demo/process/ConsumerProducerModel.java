package com.demo.process;

import java.util.LinkedList;

/**
 * 生产者消费者模型
 */
public class ConsumerProducerModel {

    private final int MAX_SIZE = 100;
    private LinkedList<Object> storage = new LinkedList<Object>();


    class Consumer implements Runnable{

        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (storage){
                    while (storage.size() == 0){
                        try {
                            storage.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    storage.removeFirst();//linkedList不是线程安全的，所以需要放在锁里面
                    System.out.println(Thread.currentThread().getName() + ",库存总数storage.size():" + storage.size()+"。消费者消费了一个库存");
                    storage.notifyAll();//notify通知也要放在锁里面

                }
            }

        }
    }
    class Producer implements  Runnable{

        public void run() {
            while (true){
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (storage){
                    while (storage.size() == MAX_SIZE){
                        try {
                            storage.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    storage.add(new Object());
                    System.out.println(Thread.currentThread().getName() + ",库存总数storage.size():" + storage.size()+"。生产者生产了一个库存");
                    storage.notifyAll();

                }
            }
        }
    }

    public static void main(String[] args){
        ConsumerProducerModel consumerProducerModel = new ConsumerProducerModel();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Producer()).start();
        new Thread(consumerProducerModel.new Producer()).start();
        new Thread(consumerProducerModel.new Producer()).start();

    }

}
