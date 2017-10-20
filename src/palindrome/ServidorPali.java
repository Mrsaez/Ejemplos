/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package palindrome;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ServidorPali {
     
     public static void main(String[] args) { 
           try{  
                int port=5001; 
                String str_result;
                ServerSocket serverSocket=new ServerSocket(port);  
                System.out.println("SERVIDOR ESPERANDO");  
                Socket clientSocket=serverSocket.accept();  
                
                ObjectOutputStream paraClient = new ObjectOutputStream( clientSocket.getOutputStream());   
                ObjectInputStream desdeClient = new ObjectInputStream( clientSocket.getInputStream()); 
                
                
                 String str=(String)desdeClient.readObject();  
                  int i,j;  
                  
                 for(i=0,j=str.length()-1;i<=j;i++,j--){  
                    if(str.charAt(i)!=str.charAt(j))  
                              break;  
                    }  
                 if (i<j)  
                    str_result="NO es palindromo";  
                 else  
                    str_result="Si es palindromo";  
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
