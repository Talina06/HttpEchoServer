/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author Talina
 */
public class DummyHttpServer {
    private int port;
    private HttpServer server; 
    public DummyHttpServer(int port){
        this.port = port;
    }
    
    public void StartServer(){
        try{
            server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Server started at port " + port);
            // Create handlers for every route.
            server.createContext("/", new Handlers.RootHandler());
            server.setExecutor(null);
            server.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
