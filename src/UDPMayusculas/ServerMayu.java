package UDPMayusculas;

import java.io.*;
import java.net.*;
/**
 *
 * @author MarielaRoxaxa
 */
public class ServerMayu {

public static void main(String args[]) throws Exception

 {

 System.out.println("El servidor va a ponerse a la escucha, un momento mientras se leen los parametros");

 int port = 2510;

 if (args.length == 0 || args==null) {

 System.out.println("El puerto no se ha especificado, se usara el puerto por defecto: 2510");

 } else if (args[0].equals("p")) {

 port = Integer.parseInt(args[1]);

 System.out.println("Vamos a usar el puerto:"+port);

 }

 else    {

 System.out.println("Debes especificar la opcion p");

 }

 DatagramSocket socketServidor = null;

 try {

 socketServidor = new DatagramSocket(port);

 } catch (IOException e)

 {

 System.out.println("Error al crear el objeto socket servidor");

 System.exit ( 0 );

 }

 byte [] recibirDatos = new byte[1024];

 byte [] enviarDatos = new byte[1024];

 

byte [] enviarDatos2 = new byte[1024];

 while(true)

 {

 DatagramPacket recibirPaquete = new DatagramPacket(recibirDatos, recibirDatos.length);

 try {        socketServidor.receive(recibirPaquete);

 } catch (IOException e)

 {

 System.out.println("Error al recibir");

 System.exit ( 0 );

 }

 String frase = new String(recibirPaquete.getData());

 InetAddress DireccionIP = recibirPaquete.getAddress();

 int puerto = recibirPaquete.getPort();

 String fraseMayusculas = frase.toUpperCase();

 enviarDatos = fraseMayusculas.getBytes();

 DatagramPacket enviarPaquete = new DatagramPacket(enviarDatos, enviarDatos.length, DireccionIP, puerto);

 try {        socketServidor.send(enviarPaquete);

 } catch (IOException e)

 {

 System.out.println("Error al recibir");

 System.exit ( 0 );

 }

 }

 }
}
