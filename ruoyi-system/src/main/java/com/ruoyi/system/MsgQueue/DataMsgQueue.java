package com.ruoyi.system.MsgQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * 体温数据处理的单例队列
 */
public class DataMsgQueue {

    private static BlockingQueue blockingQueue = null;
    private DataMsgQueue(){
    }

    public static BlockingQueue getInstance() {
        if(blockingQueue == null){
            synchronized (BlockingQueue.class){
                if(blockingQueue == null){
                    blockingQueue = (BlockingQueue) new ArrayBlockingQueue(300);
                }
            }
        }
        return blockingQueue;
    }
}
