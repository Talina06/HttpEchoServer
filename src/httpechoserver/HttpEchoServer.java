/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Talina
 */
public class HttpEchoServer {
    
    public static volatile Queue<WorkerThread> pendingReqQueue = new LinkedList<>();
    private int port;
    private ServerSocket server; 
    private Config cfg;
    
    public HttpEchoServer(int port){
        this.port = port;
        this.cfg = new Config();
    }
        
    public void startServer(){
        try{
            server = new ServerSocket(port);
            System.out.println("Server is listening on port " + port + ".");
        
            ParentThread parent =  new ParentThread();
            Thread pth = new Thread(parent);
            pth.start();
            
            // Shutdown thread.
            Thread sth= new Thread(parent){public void run(){
                System.out.println("Shutdown hook is running");
                try{
                    parent.shutdown();
                    server.close();
                    System.out.println("Server has been shut down gracefully...");
                }catch(IOException e){
                    System.out.println("Server socket could not be closed.");
                    System.exit(-1);
                }catch(InterruptedException e){
                    System.out.println("Running worker threads could not be closed");
                    System.exit(-1);
                }
            }};
            Runtime.getRuntime().addShutdownHook(sth);
            
            // Accept incoming requests in parent thread, which passes them to worker threads.
            while (true) {
                if(pendingReqQueue.size() < cfg.getBacklogCount()){ 
                    // Listen for a TCP connection request.
                    Socket connectionSocket = server.accept();
                    System.out.println("New incoming connection request.");
                    //Construct object to process HTTP request message
                    WorkerThread request = new WorkerThread(connectionSocket);
                    pendingReqQueue.add(request);    
                }
            }
        }catch(IOException e){
            System.out.println("Accepting new requests failed for port " + port);
            System.exit(-1);
        }
    }
}