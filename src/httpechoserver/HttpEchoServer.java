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
    private int maxThreadCount = 3;
    
    public HttpEchoServer(int port){
        this.port = port;
    }
        
    public void startServer(){
        try{
            server = new ServerSocket(port);
            System.out.println("Server is listening on port " + port + ".");
        
            ParentThread pendingQueue =  new ParentThread();
            Thread th= new Thread(pendingQueue);
            th.start();
            
            while (true) {
                // Listen for a TCP connection request.
                Socket connectionSocket = server.accept();
                System.out.println("New incoming connection request.");
                //Construct object to process HTTP request message
                WorkerThread request = new WorkerThread(connectionSocket);
                pendingReqQueue.add(request);    
    }
            /*System.out.println("Cannot accept further requests.");
            pool.shutdown();
            stopServer();*/
        }catch(IOException e){
            System.out.println("Accepting new requests failed for port " + port);
            System.exit(-1);
}
    }
    
    public void stopServer() throws IOException{
        this.server.close(); // close the socket connection.
    }
}
