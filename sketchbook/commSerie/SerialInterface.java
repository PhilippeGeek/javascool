package org.javascool.proglets.commSerie;

/** 
  Adaptation for JavaScool of the PSerial - class for serial port goodness
  Part of the Processing project - http://processing.org
  Copyright (c) 2004-05 Ben Fry & Casey Reas
*/

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;
import gnu.io.SerialPort;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import gnu.io.SerialPortEventListener;
import gnu.io.SerialPortEvent;
import java.util.ArrayList;

/** Définit les primitives pour s'interfacer avec un port série. 
 * <p>Les erreurs d'entrée-sortie sont affichée dans la console (<tt>System.out</tt>).</p>
 * <p>Ce code utilise <a href="http://www.jcontrol.org">RXTX</a> (documenté <a href="http://www.jcontrol.org/docs/api">ici</a>) 
 *   et s'est largement inspiré du travail de l'outil proposé par <a href="http://processing.org/reference/libraries/serial/index.html">processing</a>.</p>
 * <p>Note: La librairie  <a href="http://www.jcontrol.org">RXTX</a> est une bonne alternative 
 *   à <a href="http://download.oracle.com/docs/cd/E17802_01/products/products/javacomm/reference/api">javax.comm</a> 
 *   analysée <a href="http://www.javaworld.com/javaworld/jw-05-1998/jw-05-javadev.html">ici</a>.</p>
 * @see <a href="SerialInterface.java.html">code source</a>
 * @serial exclude
 */
public class SerialInterface {

  /** Définit le nom du port série.
   * @param name Le nom du port, "COM1" par défaut.
   * @return Cet objet permettant la construction <tt>new SerialInterface().setName(..</tt>.
   */
  public SerialInterface setName(String name) {
    this.name = name;
    return this;
  }
  /** Renvoie le nom du port série. */
  public String getName() {
    return name;
  }
  private String name = "COM1";

  /** Définit le débit du port série.
   * @param rate Le débit du port en bauds (bits par seconde), 9600 par défaut.
   * @return Cet objet permettant la construction <tt>new SerialInterface().setRate(..</tt>.
   */
  public SerialInterface setRate(int rate) {
    this.rate = rate;
    return this;
  }
  /** Renvoie le débit du port série. */
  public int getRate() {
    return rate;
  }
  private int rate = 9600;

  /** Définit la parité du port série.
   * @param parity La parité du port : 'N' (par défaut) si pas de parité, 'E' pour even si parité paire, 'O' pour odd si parité impaire.
   * @return Cet objet permettant la construction <tt>new SerialInterface().setParity(..</tt>.
   */
  public SerialInterface setParity(char parity) {
    this.parity = parity;
    return this;
  }
  /** Renvoie la parité du port série. */
  public int getParity() {
    return parity;
  }
  private char parity = 'N';

  /** Définit la taille des mots du port série.
   * @param size La taille des mots : 8 (par défaut) ou 7..
   * @return Cet objet permettant la construction <tt>new SerialInterface().setSize(..</tt>.
   */
  public SerialInterface setSize(int size) {
    this.size = size;
    return this;
  }
  /** Renvoie la taille des mots du port série. */
  public int getSize() {
    return size;
  }
  private int size = 8;

  /** Définit le nombre de bits de stop du port série.
   * @param stop Le nombre de bits de stop : 1 (par défaut), 1.5 ou 2.
   * @return Cet objet permettant la construction <tt>new SerialInterface().setStop(..</tt>.
   */
  public SerialInterface setStop(float stop) {
    this.stop = stop;
    return this;
  }
  /** Renvoie le nombre de bits de stop du port série. */
  public float getStop() {
    return stop;
  }
  private float stop = 1;

  /** Ouvre le port série avec les paramètres actuels. 
   * @return Cet objet permettant la construction <tt>new SerialInterface().set*(.. ). open()</tt>.
   */
  public SerialInterface open() {
    close();
    try {
      for(Enumeration<?> list = CommPortIdentifier.getPortIdentifiers(); list.hasMoreElements();) {
	CommPortIdentifier id = (CommPortIdentifier) list.nextElement();
	if (id.getPortType() == CommPortIdentifier.PORT_SERIAL && id.getName().equals(name)) {
	  port = (SerialPort) id.open("serial madness", 2000);
	  output = port.getOutputStream();
	  input = port.getInputStream();
	  data = new ArrayList<Integer>();
	  port.setSerialPortParams(rate, size, 
				   stop == 2f ? SerialPort.STOPBITS_2 : stop == 1.5f ? SerialPort.STOPBITS_1_5 : SerialPort.STOPBITS_1, 
				   parity == 'E' ? SerialPort.PARITY_EVEN : parity == 'O' ? SerialPort.PARITY_ODD : SerialPort.PARITY_NONE);
	  port.addEventListener(new SerialPortEventListener() {
	      synchronized public void serialEvent(SerialPortEvent serialEvent) {
		if (serialEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) { 
		  try {
		    while (input.available() > 0) 
		      data.add(input.read());
		  } catch(Exception e) {
		    System.out.println("Erreur à la réception d'un caractère sur le port série "+this+" : " + e);
		  }
		}
	      }});
	  port.notifyOnDataAvailable(true);
	}
      }
      throw new IOException("Impossible d'ouvrir un port série de nom :" + name);
    } catch(Exception e) {
      output = null; input = null; data = null; port = null;
      System.out.println("Erreur à l'ouverture du port série "+this+" : " + e);
    }
    return this;
  }
  private SerialPort port = null;
  private OutputStream output = null;
  private InputStream input = null;
  private ArrayList<Integer> data = null;

  /** Ferme le port série. */
  public void close() {
    try {
      if (output != null) { output.close(); output = null; }
      if (input != null) { input.close(); input = null; data = null; }
      if (port != null) { port.close(); port = null; }
    } catch(Exception e) {
      output = null; input = null; data = null; port = null;
      System.out.println("Erreur à la fermeture du port série "+this+" : " + e);
    }
  }

  /** Teste si le port est ouvert. 
   * @return Renvoie true si le port a été ouvert sans erreur.
   */
  public boolean isOpen() {
    return port != null;
  }

  /** Ecrit un octet sur le port série.
   * @param value L'octet à écrire.
   */
  public void write(int value){ 
    try {
      if (output == null) 
	throw new IOException("Impossible décire sur le port série "+this+" qui est fermé ou en erreur");
      output.write(value & 0xff);
      output.flush(); 
    } catch(Exception e) {
      System.out.println("Erreur à l'écriture de '"+value+"' sur le port série "+this+" : " + e);
    }
  }

  /** Lit un octet sur le port série.
   * @return La valeur de l'octet à lire ou -1 si il n'y a pas d'octet à lire.
   */
  public int read() {   
    if (data == null || data.size() == 0) {
      return -1;
    } else {
      int value = data.get(0);
      data.remove(0);
      return value;
    }
  }

  /** Renvoie une description des paramètres du port série. */
  public String toString() {
    return "<port name=\""+name+"\" rate=\""+rate+"\" parity=\""+parity+"\" size=\""+size+"\" stop=\""+stop+
      "\" open=\""+isOpen()+"\"input-buffer-size =\""+(data == null ? 0 : data.size())+"\"/>";
  }

  /** Renvoie la liste des noms de ports séries disponibles. */
  public static String[] getPortNames() {
    ArrayList<String> names = new ArrayList<String>();
    for(Enumeration<?> list = CommPortIdentifier.getPortIdentifiers(); list.hasMoreElements();) {
      CommPortIdentifier id =  (CommPortIdentifier) list.nextElement();
      if (id.getPortType() == CommPortIdentifier.PORT_SERIAL)
	names.add(id.getName());
    }
    return names.toArray(new String[names.size()]);
  }

  /** Renvoie la liste des des noms de ports séries disponibles ce qui teste l'installation des librairies. */
  public static void main(String usage[]) {
    String names[] =  getPortNames();
    {
      System.out.print("Il y a "+names.length+" ports série:");
      for(String name : names)
	System.out.print(" "+name);
      System.out.println();
    }
  }
}
