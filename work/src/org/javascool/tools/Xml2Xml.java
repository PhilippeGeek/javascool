/*******************************************************************************
* They.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
*******************************************************************************/

package org.javascool.tools;

import java.util.HashMap;

// Used for the sax interface
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

/** Transforme une structure XML en une autre structure XML avec un XSLT.
 * @see <a href="Xml2Xml.java.html">code source</a>
 * @serial exclude
 * <p>Note: utilise <tt>saxon.jar</tt> qui doit être dans le CLASSPATH.</p>
 */
public class Xml2Xml {
  // @factory

  private Xml2Xml() {}
  /** Convertit une chaîne XML en une autre chaîne XML selon des règles XSL.
   * @param xml La chaîne XML en entrée.
   * @param xsl La chaîne avec les règles de transformation XSL.
   * @return La chaîne en sortie.
   *
   * @throws IllegalArgumentException Si une erreur de syntaxe est détecté.
   * @throws RuntimeException  Si une erreur d'entrée-sortie s'est produite.
   */
  public static String run(String xml, String xsl) {
    // Compile the XSL tranformation
    try {
      if(!tranformers.containsKey(xsl))
        tranformers.put(xsl, tfactory.newTemplates(new StreamSource(new StringReader(xsl))).newTransformer());
    } catch(TransformerConfigurationException e) { throw new RuntimeException(e + " when compiling: " + xsl);
    }
    // Apply the transformation
    try {
      if(xml == null)
        xml = "<null/>";
      StringWriter writer = new StringWriter();
      tranformers.get(xsl).transform(new StreamSource(new StringReader(xml)), new StreamResult(writer));
      return writer.toString();
    } catch(TransformerException e) { throw new IllegalArgumentException(e.getMessageAndLocation());
    }
  }
  // Cash mechanism
  private static TransformerFactory tfactory;
  private static HashMap<String, Transformer> tranformers = new HashMap<String, Transformer>();
  static {
    try {
      tfactory = TransformerFactory.newInstance();
      System.setProperty("javax.xml.parsers.SAXParserFactory", "com.icl.saxon.aelfred.SAXParserFactoryImpl");
      System.setProperty("javax.xml.transform.TransformerFactory", "com.icl.saxon.TransformerFactoryImpl");
      System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.icl.saxon.om.DocumentBuilderFactoryImpl");
    } catch(Throwable e) {
      System.err.println("Configuration error: " + e);
    }
  }

  /** Lanceur de la transformation XML -XSLT-> XML.
   * @param usage <tt>java org.javascool.tools.Xml2Xml input-file XSL-file [output-file]</tt>
   */
  public static void main(String[] usage) {
    // @main
    if (usage.length > 1) {
      StringFile.save(usage.length > 2 ? usage[2] : "stdout:", run(StringFile.load(usage[0]), StringFile.load(usage[1])));
    }
  }
}
