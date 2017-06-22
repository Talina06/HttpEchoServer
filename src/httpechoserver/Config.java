/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

/**
 *
 * @author Talina
 */
// immutable class.
public final class Config {
    
    // Max worker threads that can process incoming requests simultaneously.
    private final int maxThreadCount;
    // Assumption: backlogCount is always > 0.
    private final int backlogCount;
    // Max time: ThreadPool takes, to shutdown all active threads.
    private final int shutDownTime;
    
    public Config(){
        this.maxThreadCount = 10;
        this.backlogCount = 2;
        this.shutDownTime = 10000; // milliseconds
    }
    public int getMaxThreads(){
        return this.maxThreadCount;
    }
    public int getBacklogCount(){
        return this.backlogCount;
    }
    public int getShutDownTime(){
        return this.shutDownTime;
    }
}
