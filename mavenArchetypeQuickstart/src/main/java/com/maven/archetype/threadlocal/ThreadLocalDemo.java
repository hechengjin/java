package com.maven.archetype.threadlocal;

/*
*　　首先，在每个线程Thread内部有一个ThreadLocal.ThreadLocalMap类型的成员变量threadLocals，这个threadLocals就是用来存储实际的变量副本的，键值为当前ThreadLocal变量，value为变量副本（即T类型的变量）。
*　初始时，在Thread里面，threadLocals为空，当通过ThreadLocal变量调用get()方法或者set()方法，就会对Thread类中的threadLocals进行初始化，并且以当前ThreadLocal变量为键值，以ThreadLocal要保存的副本变量为value，存到threadLocals。
*　然后在当前线程里面，如果要使用副本变量，就可以通过get方法在threadLocals里面查找。
* 为何threadLocals的类型ThreadLocalMap的键值为ThreadLocal对象，因为每个线程中可有多个threadLocal变量
* 在进行get之前，必须先set，否则会报空指针异常；
* 如果想在get之前不需要调用set就能正常访问的话，必须重写initialValue()方法。
* https://www.jianshu.com/p/98b68c97df9b
* 和HashMap的最大的不同在于，ThreadLocalMap结构非常简单，没有next引用，也就是说ThreadLocalMap中解决Hash冲突的方式并非链表的方式，而是采用线性探测的方式，
* 所谓线性探测，就是根据初始key的hashcode值确定元素在table数组中的位置，如果发现这个位置上已经有其他key值的元素被占用，则利用固定的算法寻找一定步长的下个位置，依次判断，直至找到能够存放的位置。
* map是线程里的map，线程里的map防止多个threadlocal哈希冲突，而采用线性探测
 */

public class ThreadLocalDemo {

//    private static  Integer num = 0;

    //这个代替上面的num,即每个线程里都有一个独立的num的副本,输出结果都为5,就是定义一个num的成员属性,内部通过map指向不同的副本
    private static final ThreadLocal<Integer> local = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName());

        Thread[] threads = new Thread[5];

        for (int i =0 ; i < 5; i++) {
            //λ(Lambda)表达式 Language Level 8
//            threads[i] = new Thread( ()-> {
//                int num = local.get().intValue();
//                num+=5;
//                System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName() + ":" + num);
//            });
            if (i==3 ){
                threads[i] = new Thread( new Runnable() {

                    @Override
                    public void run() {
                        int num = local.get().intValue();
                        num+=3;
                        System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName() + ":" + num);
                    }
                });
            } else {
                threads[i] = new Thread( new Runnable() {

                    @Override
                    public void run() {
                        int num = local.get().intValue();
                        num+=10;
                        System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName() + ":" + num);
                    }
                });
            }

        }

        for (int i=0; i < 5; i++){
            threads[i].start();
        }

    }
}
