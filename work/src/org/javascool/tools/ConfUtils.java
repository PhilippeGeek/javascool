/*******************************************************************************
*           Philippe.Vienne, Copyright (C) 2011.  All rights reserved.         *
*******************************************************************************/

package org.javascool.tools;

import javax.swing.ImageIcon;

/** Fournit les primitives pour lier une application à son environnement.
 * @see <a href="ConfUtils.java.html">code source</a>
 * @serial exclude
 */
public class ConfUtils {
  // @factory
  private ConfUtils() {}


  /** Renvoie une icone stockée dans le JAR de l'application.
   * @param path Emplacement de l'icone, par exemple <tt>"org/javascool/widget/icons/play.png"</tt>
   */
  public static ImageIcon getIcon(String path) {
    return new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(path));
  }
}
