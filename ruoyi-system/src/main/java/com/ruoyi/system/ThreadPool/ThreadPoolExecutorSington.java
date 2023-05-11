package com.ruoyi.system.ThreadPool;

import java.util.concurrent.*;

public class ThreadPoolExecutorSington extends ThreadPoolExecutor {

    private static ThreadPoolExecutorSington executor = null;

    private static final int COREPOOLSIZE = 30;

    private static final int MAXIMUMPOOLSIZE = 150;

    private static final long KEEPALIVETIME = 120l;


    private ThreadPoolExecutorSington() {
        super(COREPOOLSIZE, MAXIMUMPOOLSIZE, KEEPALIVETIME,TimeUnit.SECONDS, new LinkedBlockingQueue<>(),new DiscardOldestPolicy());
    }

    public static ThreadPoolExecutorSington getInstance(){
        if(executor == null){
            synchronized (ThreadPoolExecutorSington.class){
                 executor = new ThreadPoolExecutorSington();
            }
        }
        return executor;
    }
}
