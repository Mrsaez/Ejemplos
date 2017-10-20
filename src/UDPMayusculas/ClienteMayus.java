package UDPMayusculas;

import java.io.*;
import java.net.*;

/**
 *
 * @author MarielaRoxaxa
 */
public class ClienteMayus {
 
public static void main(String args[]) throws Exception
  {
    System.out.println("El cliente se pone en marcha, un momento mientras se leen los parametros");
    int port = 2510;
    String dire = null;
    String frase = null;
    if (args.length == 0 || args == null) {
          System.out.println("La direccion IP es obligatoria, el programa terminara");
          System.exit(0);
     } else if (args.length < 4) {
       // ERROR SIEMPRE: EL NUMERO DE ARGUMENTOS OBLIGATORIOS ES 4
       System.out.println("El numero de argumentos es erroneo, minimo direccion y palabra: d direccion m palabra");
       System.exit(0);
     }
     else if (args.length > 4) {
           if (args[4].equals("p")) {
               if (args.length < 6) {
                      System.out.println("No se ha especificado puerto con opcion p; el programa terminara");
                      System.exit(0);
                 } else {
                      port = Integer.parseInt(args[5]);
                   }
              System.out.println("Vamos a usar el puerto:" + port);
            }

           }
     if (args[0].equals("d")) {
             dire = (args[1]);
             System.out.println("Vamos a usar la direccion IP:" + dire);
         } else { // si no ponemos d, que es obligatorio, fallara
                 System.out.println("El parametro debe ser d, el programa terminara");
                 System.exit(0);

             }

     if (args[2].equals("m")) {
            frase = args[3];
      } else {// si no ponemos m, que es obligatorio, fallara
            System.out.println("El parametro debe ser m, el programa terminara");
            System.exit(0);
     }
    BufferedReader entradaDesdeUsuario = new BufferedReader(new InputStreamReader(System.in));
    DatagramSocket socketCliente = null;
    try {
      socketCliente = new DatagramSocket();
    } catch (IOException e)
        {
         System.out.println("Error al crear el objeto socket cliente");
         System.exit ( 0 );
       }
     InetAddress DireccionIP = null;
     try {
        DireccionIP = InetAddress.getByName(dire);
     } catch (IOException e)
       {
        System.out.println("Error al recuperar la IP del proceso");
        System.exit ( 0 );
       }
        byte [] enviarDatos = new byte[1024];
        byte [] recibirDatos = new byte[1024];
        enviarDatos = frase.getBytes();
        DatagramPacket enviarPaquete = new DatagramPacket(enviarDatos, enviarDatos.length, DireccionIP, port);
        socketCliente.send(enviarPaquete);
        DatagramPacket recibirPaquete = new DatagramPacket(recibirDatos, recibirDatos.length);
        socketCliente.receive(recibirPaquete);
        String fraseModificada = new String(recibirPaquete.getData());
        InetAddress direccion = (recibirPaquete.getAddress());
        int puertoUsado = (recibirPaquete.getPort());
        System.out.println("DEL SERVIDOR: " + fraseModificada);
        System.out.println("La direccion IP es:"+ direccion);
        System.out.println("El puerto es:"+ puertoUsado);
        socketCliente.close();
 }

 }
