/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Talina
 */
public class WorkerThread implements Runnable{
    private Socket socket;
 
    // Constructor
    public WorkerThread(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        System.out.println("Processing request...");
        String line;
        StringBuilder builder = new StringBuilder(); 
        BufferedReader in = null;
        try{
            InputStreamReader is = new InputStreamReader(socket.getInputStream(), "UTF-8");
            in = new BufferedReader(is);
            try{
                while (true) {
                        line = in.readLine();
                        if(line.equals(""))
                                break;
                        builder.append(line + "\n");
                }
                System.out.println("Request processed.");
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("Reading input failed");
                System.exit(-1);
            }
            
            //Sending the response back to the client.
            OutputStream os = socket.getOutputStream();
            String t="HTTP/1.1 200 OK\r\nContent-Length: 123\r\n"
                    + "Content-Type: text/html\r\n\r\n<h1>Response received.</h1>"
                    + "\r\n\r\nConnection: Closed";
            
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(t);
            System.out.println("Response sent back to the client.");
            bw.flush();
            bw.close();
            os.close();
            System.out.println("Request has been processed.");
            in.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Reading input failed");
            System.exit(-1);
        }
    }
}