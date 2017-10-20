/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
/**
 *
 * @author MarielaRoxaxa
 */
public class Cliente {
    private Socket cliente;
    private BufferedReader  teclado;
    private DataOutputStream salida;
    private DataInputStream entrada;
   
  public void conectar(String ip, int puerto)throws IOException{
       cliente=new Socket(ip,puerto);
       System.out.println("Cliente activo"); 
  
  } 
  
  public void enviarDatos() throws IOException{
       System.out.println("Ingrese un mensaje para server:");  
       teclado= new BufferedReader(new InputStreamReader(System.in));
       String mje=teclado.readLine();  
       salida=new DataOutputStream(cliente.getOutputStream());
       salida.writeUTF(mje);
       salida.flush();
  }
  
  public void recibirDatos() throws IOException{
       entrada=new DataInputStream(cliente.getInputStream());
       String mensaje=entrada.readUTF();
       System.out.println(mensaje);
       entrada.close();
       
  }
    public void cerrarTodo() throws IOException{
  
       salida.close();
       teclado.close();
       cliente.close();       
      }
    
    
    public static void main(String[] args) throws IOException {  
        String ipMaquina ;
        int puerto;
        if (args.length <2)  {
            puerto=5555; 
            ipMaquina="127.0.0.1";
        }
        else {
            puerto=Integer.parseInt(args[1]);
            ipMaquina = args[0];
        }
        Cliente cli = new Cliente();
        try{
            cli.conectar(ipMaquina, puerto); 
            cli.enviarDatos();
            cli.recibirDatos();
            cli.cerrarTodo();
        }
        catch(IOException e){
            System.out.println("ERROR AL conectar LOS SOCKETS CLIENTE " + e.getMessage());
        }
    }
}
