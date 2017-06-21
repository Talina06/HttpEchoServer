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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Talina
 */
public class WorkerThread implements Runnable{
    private Socket socket;
    private static final String CRLF = "\r\n";
 
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
        int contentLength = 0;
        try{
            InputStreamReader is = new InputStreamReader(socket.getInputStream(), "UTF-8");
            in = new BufferedReader(is);
            try{
                line = in.readLine();
                boolean isPost = line.startsWith("POST");
                while (true) {
                        line = in.readLine();
                        if (isPost) {
                            final String contentHeader = "Content-Length: ";
                            if (line.startsWith(contentHeader)) {
                                contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                            }
                        }
                        if(line.isEmpty())
                                break;
                        builder.append(line + CRLF);
                }
                System.out.println("Request processed.");
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("Reading input failed");
                System.exit(-1);
            }
            
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Sending the response back to the client.
            OutputStream os = socket.getOutputStream();
            String t="HTTP/1.1 200 OK" + CRLF + "Content-Length: " + contentLength 
                    + CRLF + CRLF + "<h1>Response received.</h1>"
                    + CRLF + CRLF + "Connection: Closed";
            
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