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
public class HttpEchoServer {
    
    public static int port = 80;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DummyHttpServer server = new DummyHttpServer(port);
        server.StartServer();
    }
    
}
