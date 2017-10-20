/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ParImpar;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ClienteParImpar {
    private BufferedReader teclado;
    private ObjectOutputStream paraServer;
    private ObjectInputStream desdeServer;
    private Socket cli;
    
    public void conectar(String ip, int puerto) throws IOException{
      cli= new Socket(ip,puerto); 
      System.out.println("Cliente activo");  
    }
    
    public void envia() throws IOException{
      teclado=new BufferedReader(new InputStreamReader(System.in));
      
      paraServer=new ObjectOutputStream(cli.getOutputStream());
      System.out.println("INGRESE UN NUMERO:");  
      int no=Integer.parseInt(teclado.readLine());  
      paraServer.writeObject(no);
      }
    
    public void recibir() throws IOException, ClassNotFoundException{
        desdeServer=new ObjectInputStream(cli.getInputStream());
        String result=(String)desdeServer.readObject();  
        System.out.println("El número es:"+result);
    }
    
    public void cerrar() throws IOException{
      teclado.close();
      paraServer.close();
      desdeServer.close();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {          
        int puerto;
        String ip;
        if(args.length<2){
            puerto=5000;
            ip="localhost";
        }
        else {
            puerto=Integer.parseInt(args[0]);
            ip=args[1];
        }
        ClienteParImpar cliente=new ClienteParImpar();
        
        cliente.conectar(ip, puerto);
        cliente.envia();
        cliente.recibir();
        cliente.cerrar();
    }
}
