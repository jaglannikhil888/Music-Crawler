package tech.codingclub.utility;

import tech.codingclub.RunnableExample;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    /*private int poolSize;
    private ExecutorService executorService;

    public ThreadManager(int poolSize){
        this.poolSize=poolSize;
        executorService=Executors.newFixedThreadPool(poolSize);
    }

    public void execute(Runnable runnable){
        executorService.submit(runnable);
    }*/

    public static void main(String[] args) {
        // Here newFixedThreadPool is static as we have not created any object of it

        // Here it's not that if u create more than 10 threads it will not be able to manage it
        // it will take care of those things automatically

        // here submit is like mere ko koi thread de do
        // i'm gonna manage it and run it
        //executorService.submit(new RunnableExample("THREAD-A",0,500));
        //executorService.submit(new RunnableExample("THREAD-B",0,1000));
        //executorService.submit(new RunnableExample("THREAD-C",0,2000));

        /*for(int i=0;i<1000;i++){
            RunnableExample temp=new RunnableExample("THREAD-No."+i,0,500+i);
            executorService.submit(temp);
            // If Queue is heavy now then pause with in this for loop !

        }*/

        // 1. Task Manager for N no of threads parallely !
        // 2. Let say I want Main Thread to pause when there is enough queue size !
        // this is bcz if we create 1 crore threads then queue will explode/crash
        // so some upper-bound must be defined

        /*
        ThreadManager threadManager=new ThreadManager(100);
        for(int i=0;i<10000;i++){
            RunnableExample temp=new RunnableExample("THREAD-NO-"+i,0,500+i);
            threadManager.execute(temp);
        }
        */

        System.out.println("This side is Nikhil Jaglan");
        System.out.println("Running ThreadManager at "+new Date().toString());

        TaskManager taskManager=new TaskManager(100);

        for(int i=0;i<10000;i++){
            RunnableExample temp=new RunnableExample("THREAD-No-"+i,0,500);
            taskManager.waitTillQueueIsFreeAndTask(temp);

            System.out.println("$$$$$$$$$"+i);
        }

        System.out.println("##########################################");

        // 1. Task Manager for N no of threads parallely !
        // 2. Let say I want Main Thread to pause when there is enough queue size !

    }
}
