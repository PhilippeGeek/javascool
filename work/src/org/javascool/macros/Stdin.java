/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.macros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Cette factory contient des fonctions générales rendues visibles à l'utilisateur de proglets.
 * <p>Elle permet de définir des fonctions statiques qui seront utilisées pour faire des programmes élèves.</p>
 * <p>Elle permet aussi avoir quelques fonctions de base lors de la création de nouvelles proglets.</p>
 *
 * @see <a href="Stdin.java.html">code source</a>
 * @serial exclude
 */
public class Stdin {
  // @factory
  private Stdin() {}

  /** Lit une chaîne de caractère dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La chaîne lue.
   */
  public static String readString(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popString();
    inputQuestion = question;
    inputString = null;
    inputDialog = new Macros.NonModalDialog();
    inputDialog.setTitle("Java's Cool read");
    inputDialog.add(new JPanel() {
                      {
                        add(new JLabel(inputQuestion + " "));
                        add(new JTextField(40) {
                              {
                                addActionListener(new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                      inputString = ((JTextField) e.getSource()).getText();
                                                      inputDialog.close();
                                                    }
                                                  }
                                                  );
                              }
                            }
                            );
                      }
                    }
                    );
    inputDialog.open();
    return inputString == null ? "" : inputString;
  }
  private static Macros.NonModalDialog inputDialog;
  private static String inputQuestion, inputString;

  /**
   * @see #readString(String)
   */
  public static String readString() {
    return readString("Entrez une chaîne :");
  }
  /** Lit un nombre entier dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La valeur lue.
   */
  public static int readInteger(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popInteger();
    String s = readString(question);
    try {
      return Integer.decode(s);
    } catch(Exception e) {
      if(!question.endsWith(" (Merci d'entrer un nombre entier)"))
        question = question + " (Merci d'entrer un nombre entier)";
      if(s.equals(""))
        return 0;
      return readInteger(question);
    }
  }
  /**
   * @see #readInteger(String)
   */
  public static int readInteger() {
    return readInteger("Entrez un nombre entier : ");
  }
  /**
   * @see #readInteger(String)
   */
  public static int readInt(String question) {
    return readInteger(question);
  }
  /**
   * @see #readInteger(String)
   */
  public static int readInt() {
    return readInteger();
  }
  /** Lit un nombre décimal dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La valeur lue.
   */
  public static double readDecimal(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popDecimal();
    String s = readString(question);
    try {
      return Double.parseDouble(s);
    } catch(Exception e) {
      if(!question.endsWith(" (Merci d'entrer un nombre)"))
        question = question + " (Merci d'entrer un nombre)";
      if(s.equals(""))
        return 0;
      return readDecimal(question);
    }
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readDecimal() {
    return readDecimal("Entrez un nombre décimal : ");
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readDouble(String question) {
    return readDecimal(question);
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readDouble() {
    return readDecimal();
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readFloat(String question) {
    return readDecimal(question);
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readFloat() {
    return readDecimal();
  }
  /** Lit une valeur booléenne dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La valeur lue.
   */
  public static boolean readBoolean(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popBoolean();
    inputQuestion = question;
    inputString = null;
    inputDialog = new Macros.NonModalDialog();
    inputDialog.setTitle("Java's Cool read");
    inputDialog.add(new JPanel() {
                      {
                        add(new JLabel(inputQuestion + " "));
                        add(new JButton("OUI") {
                              {
                                addActionListener(new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                      inputString = "OUI";
                                                      inputDialog.close();
                                                    }
                                                  }
                                                  );
                              }
                            }
                            );
                        add(new JButton("NON") {
                              {
                                addActionListener(new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                      inputString = "NON";
                                                      inputDialog.close();
                                                    }
                                                  }
                                                  );
                              }
                            }
                            );
                      }
                    }
                    );
    inputDialog.open();
    return "OUI".equals(inputString);
  }
  /**
   * @see #readBoolean(String)
   */
  public static boolean readBoolean() {
    return readBoolean("Entrez une valeur booléenne (oui/non) : ");
  }
  /**
   * @see #readBoolean(String)
   */
  public static Boolean readBool(String question) {
    return readBoolean(question);
  }
  /**
   * @see #readBoolean(String)
   */
  public static Boolean readBool() {
    return readBoolean();
  }
  /** Charge une chaine de caractère pour que son contenu serve d'entrée à la console.
   * @param string La chaine de caractère à ajouter.
   */
  public static void addConsoleInput(String string) {
    inputBuffer.add(string);
  }
  /** Charge le contenu d'un fichier pour que son contenu serve d'entrée à la console.
   * @param location La localisation (chemin du fichier ou localisation internet) d'où charger le texte.
   */
  public static void loadConsoleInput(String location) {
    addConsoleInput(org.javascool.tools.FileManager.load(location));
  }
  /** Définit une zone tampon qui permet de substituer un fichier aux lectures au clavier. */
  private static class InputBuffer {
    String inputs = new String();

    /** Ajoute une chaîne en substitution d'une lecture au clavier.
     * @param string Le texte à ajouter.
     */
    public void add(String string) {
      inputs += string.trim() + "\n";
    }
    /** Teste si il y une chaîne disponible.
     * @return La valeur true si il y une entrée disponible.
     */
    public boolean isPopable() {
      return inputs.length() > 0;
    }
    /** Récupére une chaîne en substitution d'une lecture au clavier.
     * @return Le texte suivant à considérer. Ou la chaîne vide si le tampon est vide.
     */
    public String popString() {
      Macros.sleep(500);
      int i = inputs.indexOf("\n");
      if(i != -1) {
        String input = inputs.substring(0, i);
        inputs = inputs.substring(i + 1);
        return input;
      } else
        return "";
    }
    /**
     * @see #popString(String)
     */
    public int popInteger() {
      try {
        return Integer.decode(popString());
      } catch(Exception e) {
        return 0;
      }
    }
    /**
     * @see #popString(String)
     */
    public double popDecimal() {
      try {
        return Double.parseDouble(popString());
      } catch(Exception e) {
        return 0;
      }
    }
    /**
     * @see #popString(String)
     */
    public boolean popBoolean() {
      // Renvoie vrai si [t]rue [y]es [v]rai [o]ui 1
      return popString().toLowerCase().matches("[tyvo1].*");
    }
  }
  private static InputBuffer inputBuffer = new InputBuffer();
}

