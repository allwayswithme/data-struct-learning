package com.demo.process;

/**
 * https://www.cnblogs.com/vettel/p/3438257.html
 * 哲学家就餐,解决死锁的方案为：每个哲学家只有确认自己左右手的筷子可用才可以拿起筷子进餐
 * 该解决方案会导致“每个时刻最多能够满足两个人同时进餐，且两人座位不相邻”
 */
public class PhilosopherDinner{

    class Philosopher extends Thread{

        private String name;
        private Fork fork;

        public Philosopher(String name, Fork fork){
            this.name = name;
            this.fork = fork;
        }

        /**
         * 哲学家就餐
         */
        public void eat(){

        }

        /**
         * 哲学家思考
         */
        public void thinking(){

        }
    }


    class Fork{
        private boolean[] used = {false,false,false,false,false};

        /**
         * 拿筷子
         */
        public synchronized void takeFork(){

        }

        /**
         * 放筷子
         */
        public synchronized void putFork(){

        }
    }

    public static void main(String[] args){
        PhilosopherDinner philosopherDinner = new PhilosopherDinner();
        Fork fork = philosopherDinner.new Fork();
    }
}
