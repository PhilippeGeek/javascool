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

  private static ServerSocket sserver;
  private static Socket socserver;
  private static BufferedReader plecserver;
  private static PrintWriter predserver;
  private static Socket socketclient;
  private static BufferedReader plecclient;
  private static PrintWriter predclient;
  private static boolean modeTraceSocket = false;
  
  
  /** Renvoie l'instance de la proglet. */
  private static Panel getPane() {
    return getProgletPane();
  }  
  
  /** Permet de positionner une marque sur la grille du panel de la proglet */
  public static void setGrille(int i , int j ,char mark) {
    if (mark == 'O') {
      getPane().tictac[i][j].setText("O");
      getPane().tictac[i][j].setForeground(Color.BLUE);
    } else if (mark == 'X'){
      getPane().tictac[i][j].setText("X");
      getPane().tictac[i][j].setForeground(Color.GREEN);    
    }
  }
  
  /** Permet de récupérer la marque sur la grille du panel de la proglet */  
  public static char getGrille(int i , int j ) {
    return getPane().tictac[i][j].getText().charAt(0);
  } 
    
    
  /** Initialisation du mode trace  */  
  public static void setModeTraceSocket(boolean flag) {
    modeTraceSocket = flag;
  }    
  /** Initialisation du socket server  */  
  public static boolean initSocketServer(int numport) {

  try
  {
        if (modeTraceSocket) {
           System.out.println("[initSocketServer] [port : "+numport+"]");
           System.out.println("opening port in progress ...");           
        }
        sserver = new ServerSocket(numport);
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

  } catch (IOException ioe) { 
     System.out.println("[initSocketServer] Fail to open port : "+numport);
     System.out.println(ioe.toString()); 
     return false; 
  }
  if (modeTraceSocket) System.out.println("opening port successful !");
  return true;    
  }
  

  /** Permet de récupérer un message via le socket server */  
  public static String getMessageViaSocketServer() {
  
  String str;
  try
  {
           str = plecserver.readLine();          // lecture du message
           if (modeTraceSocket) System.out.println("[getMessageViaSocketServer] : " + str);  
           

  } catch (IOException ioe) { 
      System.out.println("[getMessageSocketServer] : Socket read error");
      str = "!ERR"; 
  }
    return str; 
  }


 
  /** Permet d'écrire un message sur le socket server */  
  public static void sendMessageViaSocketServer(String text) {

    predserver.println(text);
    if (modeTraceSocket) System.out.println("[sendMessageViaSocketServer] : " + text);   
 
  }
  
  /** Fermeture du socket server  */  
  public static boolean closeSocketServer() {

  try
  {
    plecserver.close();
    predserver.close();
    socserver.close();
  } catch (IOException ioe) {
    System.out.println("[closeSocketServer] : Fail");
    System.out.println(ioe.toString());     
    return false; 
  }
  if (modeTraceSocket) System.out.println("[closeSocketServer] : Successful"); 
  return true;    
  }      
  
  /** Initialisation socket client vers server */  
   public static boolean initSocketClient(String serverName, int numport) {

  try
  {  
    if (modeTraceSocket) {
      System.out.println("[initSocketClient] : "+serverName+"  port : "+numport);
      System.out.println("opening port in progress ...");
    }
        
    socketclient = new Socket(serverName, numport);
    if (socketclient == null) return false;       
         
    if (modeTraceSocket) System.out.println("SOCKET = " + socketclient);

    plecclient = new BufferedReader(
                               new InputStreamReader(socketclient.getInputStream())
                               );

        predclient = new PrintWriter(
                             new BufferedWriter(
                                new OutputStreamWriter(socketclient.getOutputStream())),
                             true);
   
  } catch (IOException ioe) { 
    System.out.println("[initSocketServer] Fail to open port : ("+serverName+") "+numport);
    System.out.println(ioe.toString()); 
    return false; 
  }
  if (modeTraceSocket) System.out.println("opening port successful !");
  return true; 
  } 
  
  /** Permet d'envoyer un message via le socket client */  
   public static void sendMessageViaSocketClient(String text) {

        predclient.println(text);          // envoi d'un message
        if (modeTraceSocket) System.out.println("sendMessageViaSocketClient : "+text);
        
  }
  
  /** Permet de lire un message via le socket client */  
   public static String getMessageViaSocketClient() {

   String str;
  try
  {  
      str = plecclient.readLine(); 
      if (modeTraceSocket) System.out.println("[getMessageViaSocketClient] : "+str);
    } catch (IOException ioe) { 
     System.out.println("[getMessageViaSocketClient] : socket read error");
     str = "!ERR"; 
    }
  return str; 
  }
  
  /** Fermeture de la socket  client */  
   public static boolean closeSocketClient() {

  try
  {  
    plecclient.close();
    predclient.close();
    socketclient.close();   
  } catch (IOException ioe) { 
    System.out.println("[closeSocketClient] : Fail");
    System.out.println(ioe.toString()); 
    return false; 
  }
  if (modeTraceSocket) System.out.println("[closeSocketClient] : Successful"); 
  return true; 
  } 
      
} // class functions
