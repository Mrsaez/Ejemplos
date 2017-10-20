package UDPMayusculas;

import java.io.*;
import java.net.*;
/**
 *
 * @author MarielaRoxaxa
 */
public class ClienteMayu {
 
public static void main(String args[]) throws Exception
  {
    System.out.println("El cliente se pone en marcha, un momento mientras se leen los parametros");
    int port = 2510;
    String dire = null;
    String frase = null;
     dire = "lcalhost";//(args[0]);
      port = 2510;//Integer.parseInt(args[1]); 
     frase = args[2];
      System.out.println("Vamos a usar la direccion IP:" + dire);
      
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
