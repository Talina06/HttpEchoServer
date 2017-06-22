/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Talina
 */
public class ParentThread implements Runnable{
    private Config cfg = new Config();
    ExecutorService pool = Executors.newFixedThreadPool(cfg.getMaxThreads());
    // Logger logger = Logger.getLogger("logRecords");
    
    @Override
    public void run() {
        while(true){
            if(!(HttpEchoServer.pendingReqQueue.isEmpty()) &&
                    HttpEchoServer.pendingReqQueue.size() < cfg.getBacklogCount() && 
                    ((ThreadPoolExecutor)pool).getActiveCount() != cfg.getMaxThreads()){
                pool.submit(HttpEchoServer.pendingReqQueue.remove());
            }
        }
    }
    
    public void shutdown() throws InterruptedException{
        // await for the pool to shutdown all it's active threads.
        pool.shutdown();
        if (!pool.awaitTermination(cfg.getShutDownTime(),TimeUnit.MILLISECONDS)) { 
            System.out.println("Worker threads did not terminate in the specified time.\n"
                    + "Abruptly shutting them."); 
            pool.shutdownNow();
        }
    }
}