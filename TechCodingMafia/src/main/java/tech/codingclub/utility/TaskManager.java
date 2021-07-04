package tech.codingclub.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager {

    private int threadCount;
    private ExecutorService executorService;

    public TaskManager(int threadCount){
        this.threadCount=threadCount;
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }

    // Here if in for loop on creation of one more thread the count of threads exceeds
    // the threadCount(max possible) then the thread will go to sleep for time 100
    // and after this time will again check the condn for size
    // So during this time the for loop will not execute i.e it will be like stuck

    public void waitTillQueueIsFreeAndTask(Runnable runnable){
        while(getQueueSize()>=threadCount){
            try{
                System.out.println("Sleeping");
                Thread.currentThread().sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        addTask(runnable);
    }

    private void addTask(Runnable runnable) {
        this.executorService.submit(runnable);
    }

    private int getQueueSize() {
        ThreadPoolExecutor executor=(ThreadPoolExecutor) (executorService);
        return executor.getQueue().size();
    }

}

