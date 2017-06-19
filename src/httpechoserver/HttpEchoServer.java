/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Talina
 */
public class HttpEchoServer {
    
    public static int port;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // accept port from as argument.
        if(args.length != 1){
            System.err.println("Missing port number."
                    + "\n Usage: java HttpEchoServer <port-number>");
        }
        port = Integer.parseInt(args[0]);   
        
        DummyHttpServer server = new DummyHttpServer(port);
        server.startServer();
        
    }
}
