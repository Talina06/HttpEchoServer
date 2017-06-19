/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpechoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        PrintWriter out = null;
        try{
            InputStreamReader is = new InputStreamReader(socket.getInputStream(), "UTF-8");
            in = new BufferedReader(is);
            out = new PrintWriter(socket.getOutputStream());
        }catch(IOException e){
            System.out.println("Unable to fetch Input or Output from the request.");
            System.exit(-1);
        }
        try{
            while (true) {
                    line = in.readLine();
                    if(line.equals(""))
                            break;
                    builder.append(line + "\n");
            }
            System.out.println("Request processed.");
            out.println(builder.toString());
            out.close();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Reading input failed");
            System.exit(-1);
        }
    }
}