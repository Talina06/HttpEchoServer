/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Talina
 */
public class ParentThread implements Runnable{
    private int backlogCount = 2;
    private int maxThreadCount = 3;
    ExecutorService pool = Executors.newFixedThreadPool(maxThreadCount);
    
    @Override
    public void run() {
        while(true){
            if(!(HttpEchoServer.pendingReqQueue.isEmpty()) &&
                    HttpEchoServer.pendingReqQueue.size() < backlogCount && 
                    ((ThreadPoolExecutor)pool).getActiveCount() != maxThreadCount){
                pool.submit(HttpEchoServer.pendingReqQueue.remove());
            }
        }
    }
    
}
