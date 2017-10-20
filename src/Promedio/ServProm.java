/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Promedio;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author MarielaRoxaxa
 */
public class ServProm {
    public static void main(String[] args) { 
           try{  
                //int puertoServidor = Integer.parseInt(args[0]); 
                double resultado=0;
                ServerSocket serverSocket=new ServerSocket(5001);  
                System.out.println("SERVIDOR ESPERANDO");  
                Socket clientSocket=serverSocket.accept();  
               
                ObjectOutputStream paraCliente = new ObjectOutputStream( clientSocket.getOutputStream());   
                ObjectInputStream desdeCliente = new ObjectInputStream( clientSocket.getInputStream()); 
               
                while(true)  
                {  
                 
                 ArrayList<Integer> lista = (ArrayList<Integer>) desdeCliente.readObject(); 
                 
                int temp;
                for(int i=0; i<lista.size(); i++){ 
                    temp=(Integer)lista.get(i);
                    System.out.println("Servidor: "+temp);
                    resultado+=temp ; 
                }  
                resultado/= lista.size();
                paraCliente.flush();
                
                paraCliente.writeObject(resultado);
               }}
           catch( IOException | ClassNotFoundException e) {
               System.out.println( e );
}
}
}
