/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subestring;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ServSubStr {
     public static void main(String[] args) { 
           try{  
                int port=5001; 
                String str_result;
                ServerSocket serverSocket=new ServerSocket(port);  
                System.out.println("SERVIDOR ESPERANDO");  
                Socket clientSocket=serverSocket.accept();  
                
                ObjectOutputStream paraClient = new ObjectOutputStream( clientSocket.getOutputStream());   
                ObjectInputStream desdeClient = new ObjectInputStream( clientSocket.getInputStream()); 
                
                
                 String str1=(String)desdeClient.readObject();  
                 String str2=(String)desdeClient.readObject();  
                 int result=str1.indexOf(str2);  
                 if(result==-1)  
                         str_result=str2 +" NO ES SUBESTRING de "+str1;  
                 else  
                         str_result=str2 +" SI ES SUBESTRING de "+str1;  
                 paraClient.flush();  
                 paraClient.writeObject(str_result);   
                 
                 desdeClient.close();
                 paraClient.close();
                 clientSocket.close();
                 serverSocket.close();
                }
           catch( IOException | ClassNotFoundException e) {
               System.out.println( e );
            }
}
}
