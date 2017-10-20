package factorial;
import java.io.*;
import java.net.Socket;


/**
 *
 * @author MarielaRoxaxa
 */
public class ClienteFact {
    
    private BufferedReader teclado;
    private ObjectOutputStream paraServer;
    private ObjectInputStream desdeServer;
    private Socket cli;
    

    public ClienteFact() {
        
    }
    
    public void conectar(String ip, int puerto) throws IOException{
      cli= new Socket(ip,puerto); 
      System.out.println("Cliente activo");  
      teclado=new BufferedReader(new InputStreamReader(System.in));
      paraServer=new ObjectOutputStream(cli.getOutputStream());
     desdeServer=new ObjectInputStream(cli.getInputStream());
    }
    
    public void enviar() throws IOException, ClassNotFoundException{
      System.out.println("ENTRE UN NÚMERO:");  
      int nro=Integer.parseInt(teclado.readLine());  
      paraServer.writeObject(nro);  
      }
    
    public Object recibir() throws IOException, ClassNotFoundException{
       Object result=desdeServer.readObject();  
       return result;
    }
    
    public void cerrar() throws IOException{
      teclado.close();
      paraServer.close();
      desdeServer.close();
      cli.close();
    }
    
    public String pregunta() throws IOException{
       String rta;
       System.out.println("Desea continuar? S/N: ");
       rta=teclado.readLine();
       paraServer.writeObject(rta);
       return rta;

    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {          
        
        String sigue = "s", ip;
       
        int puerto; 
        
         if(args.length<2){
            puerto=5000;
            ip="localhost";
        }
        else {
            puerto=Integer.parseInt(args[0]);
            ip=args[1];
        }
        ClienteFact cliente=new ClienteFact();
        
        cliente.conectar(ip, puerto);
        while(sigue.equals("s")||sigue.equals("S"))  
                {  
                 
                 cliente.enviar();
                 int res=(int)cliente.recibir();
                 System.out.println("EL FACTORIAL ES:"+res);  
                 sigue=cliente.pregunta();
                 
                }
        
        
        cliente.cerrar();
    }
}
         
               
              
    
   

