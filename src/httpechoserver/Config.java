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
    
    private final int maxThreadCount;
    private final int backlogCount;
    
    public Config(){
        this.maxThreadCount = 3;
        this.backlogCount = 2;
    }
    public int getMaxThreads(){
        return this.maxThreadCount;
    }
    public int getbacklogCount(){
        return this.backlogCount;
    }
}
