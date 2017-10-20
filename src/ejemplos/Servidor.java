package ejemplos;
import java.net.*;
import java.io.*;

/**
 *
 * @author MarielaRoxaxa
 */
public class Servidor {
  private ServerSocket serv;
  private Socket cli;
  private DataInputStream entrada;
  private DataOutputStream salida;
  
  public void conectar(int puerto)throws IOException{
        serv=new ServerSocket(puerto);
        serv.setSoTimeout(60000);
        cli=new Socket();
        System.out.println("Esperando una conexiÃ³n...");
        cli=serv.accept();
  }
 
  public void enviarDatos(String mje) throws IOException{
   
        salida=new DataOutputStream(cli.getOutputStream());
        salida.writeUTF(mje);
        salida.flush(); 
      
    }
    
   public void recibirDatos() throws IOException{
        entrada=new DataInputStream(cli.getInputStream());
        System.out.println("Cliente dice: "+entrada.readUTF());
   }
   
   public void cerrarTodo() throws IOException{
        System.out.println("Cerrando conexiÃ³n...");
        entrada.close();
        salida.close();
        serv.close();
        cli.close();
   }
    public static void main(String[] args) throws IOException {
        
        int puerto;
        
        if (args.length ==0)  puerto=5555;
        else puerto=Integer.parseInt(args[0]);
        Servidor s=new Servidor();
        s.conectar(puerto);
        s.enviarDatos("Servidor: Â¡Mensaje recibido!");
        s.recibirDatos();
        s.cerrarTodo();
    }
}
