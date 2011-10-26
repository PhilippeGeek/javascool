package org.javascool.proglets.ticTacToe;
import static org.javascool.macros.Macros.*;
import java.awt.*;
import java.io.*;
import java.net.*;
//import java.nio.*;

/** Définit les fonctions de la proglet.
 * @see <a href="Functions.java.html">source code</a>
 * @serial exclude
 */
public class Functions {

  private static final int port = 8080;
  private static ServerSocket sserver;
  private static Socket socserver;
  private static BufferedReader plecserver;
  private static PrintWriter predserver;
  private static Socket socketclient;
  private static BufferedReader plecclient;
  private static PrintWriter predclient;
  
  /** Renvoie l'instance de la proglet. */
  private static Panel getPane() {
    return getProgletPane();
  }
  
  /** Permet de positionner une marque sur la grille du panel de la proglet */
  public static void setGrille(int i , int j , int flag) {
    if (flag == 0) {
      getPane().tictac[i][j].setText("O");
      getPane().tictac[i][j].setForeground(Color.BLUE);
    } else {
      getPane().tictac[i][j].setText("X");
      getPane().tictac[i][j].setForeground(Color.GREEN);    
    }
  }
  
  /** Permet de récupérer la marque sur la grille du panel de la proglet */  
  public static String getGrille(int i , int j ) {
    return getPane().tictac[i][j].getText();
  } 
    
  /** Initialisation du socket server (port 8080) */  
  public static boolean initSocketServer() {

  try
  {
        sserver = new ServerSocket(port);
        if (sserver == null) return false;
        
        socserver = sserver.accept();

        // Un BufferedReader permet de lire par ligne.
        plecserver = new BufferedReader(
                               new InputStreamReader(socserver.getInputStream())
                              );

        // Un PrintWriter possède toutes les opérations print classiques.
        // En mode auto-flush, le tampon est vidé (flush) à l'appel de println.
        predserver = new PrintWriter(
                             new BufferedWriter(
                                new OutputStreamWriter(socserver.getOutputStream())), 
                             true);

  } catch (IOException ioe) { return false; }
  return true;    
  }
  

  /** Permet de récupérer un message via le socket server */  
  public static String getMessageSocketServer() {
  
  String str;
  try
  {
           str = plecserver.readLine();          // lecture du message
           System.out.println("getMessageSocketServer = " + str);  
           

  } catch (IOException ioe) { 
      System.out.println("getMessageSocketServer :Erreur lecture socket");
      str = "!ERR"; 
  }
    return str; 
  }


 
  /** Permet d'écrire un message sur le socket server */  
  public static boolean sendMessageSocketServer(String text) {

    predserver.println(text);
    System.out.println("sendMessageSocketServer = " + text);   // trace locale

  return true;    
  }
  
  /** Fermeture du socket server (port 8080) */  
  public static boolean closeSocketServer() {

  try
  {

    plecserver.close();
    predserver.close();
    socserver.close();
  } catch (IOException ioe) { return false; }
  return true;    
  }      
  
  /** Initialisation socket client vers server (port 8080) */  
   public static boolean initSocketClient(String serverName) {

  try
  {  
        socketclient = new Socket(serverName, port);
        if (socketclient == null) return false;       
         
        System.out.println("SOCKET = " + socketclient);

        plecclient = new BufferedReader(
                               new InputStreamReader(socketclient.getInputStream())
                               );

        predclient = new PrintWriter(
                             new BufferedWriter(
                                new OutputStreamWriter(socketclient.getOutputStream())),
                             true);
   
    } catch (IOException ioe) { return false; }
  return true; 
  } 
  
  /** Permet d'envoyer un message via le socket client */  
   public static boolean sendMessageSocketClient(String text) {

        predclient.println(text);          // envoi d'un message

  return true; 
  }
  
  /** Permet de lire un message via le socket client */  
   public static String getMessageSocketClient() {

   String str;
  try
  {  
      str = plecclient.readLine();  
    } catch (IOException ioe) { 
     System.out.println("getMessageSocketClient :Erreur lecture socket");
     str = "!ERR"; 
    }
  return str; 
  }
  
  /** Permet d'envoyer un message via une socket (port 8080) */  
   public static boolean closeSocketClient(String text) {

  try
  {  
    plecclient.close();
    predclient.close();
    socketclient.close();   
  } catch (IOException ioe) { return false; }
  return true; 
  }         
 
      
} // class functions
