/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Talina
 */
public class Handlers {
    public static class RootHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange hpe) throws IOException {
            //throw new UnsupportedOperationException("Not supported yet."); 
            String response = "Output received at root. Port: "
                    + HttpEchoServerProgram.port;
            hpe.sendResponseHeaders(200, response.length());
            OutputStream os = hpe.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    
    }
}
