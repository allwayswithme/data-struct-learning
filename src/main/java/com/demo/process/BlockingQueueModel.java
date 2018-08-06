package com.demo.process;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者消费者模型（BlockingQueue实现）
 */
public class BlockingQueueModel {

    private BlockingQueue<Object> storage = new ArrayBlockingQueue<Object>(100);


    class Consumer implements Runnable{

        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    storage.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ",库存总数:" + storage.size()+"。消费者消费了一个库存");


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

                try {
                    storage.put(new Object());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ",库存总数" + storage.size()+"。生产者生产了一个库存");

            }
        }
    }

    public static void main(String[] args){
        BlockingQueueModel consumerProducerModel = new BlockingQueueModel();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Consumer()).start();
        new Thread(consumerProducerModel.new Producer()).start();
        new Thread(consumerProducerModel.new Producer()).start();
        new Thread(consumerProducerModel.new Producer()).start();

    }

}
