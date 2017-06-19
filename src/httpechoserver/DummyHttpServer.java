/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import com.sun.net.httpserver.HttpServer;
import static httpechoserver.HttpEchoServer.port;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Talina
 */
public class DummyHttpServer {
    private int port;
    private ServerSocket server; 
    public DummyHttpServer(int port){
        this.port = port;
    }
    
    public void StartServer(){
        try{
            server = new ServerSocket(port);
            System.out.println("Server is listening on port " + port + ".");
    
            while (true) {
                // Listen for a TCP connection request.
                Socket connectionSocket = server.accept();
                System.out.println("New incoming connection request.");
                //Construct object to process HTTP request message
                WorkerThread request = new WorkerThread(connectionSocket);
                
                Thread thread = new Thread(request);
                thread.start(); //start thread
           }
        }catch(IOException e){
            System.out.println("Accepting new requests failed for port " + port);
            System.exit(-1);
        }
    }
    
    public void StopServer() throws IOException{
        this.server.close(); // close the socket connection.
    }
}
