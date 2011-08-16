/*******************************************************************************
* David.Pichardie@inria.fr, Copyright (C) 2011.           All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.gogleMaps;

// Used to define the gui
import java.util.Map;
import java.util.List;

import javax.swing.SwingUtilities;
import org.javascool.tools.Macros;


/** Définit les fonctions de la proglet d'expérimenter avec des signaux sonores.
 *
 * @see <a href="Functions.java.html">code source</a>
 * @serial exclude
 */
public class Functions {
  private static final long serialVersionUID = 1L;
  // @factory 
  private Functions() {}
  /** Renvoie l'instance de la proglet pour accéder à ses éléments. */
  private static Panel getPane() {
    return Macros.getProgletPane();
  }

  public enum IntensiteRoute { 
    LEGER(1), MOYEN(2), FORT(3);
    private int value;
    IntensiteRoute(int i) {
      value = i;
    }
  }

  public final static IntensiteRoute LEGER = IntensiteRoute.LEGER;
  public final static IntensiteRoute MOYEN = IntensiteRoute.MOYEN;
  public final static IntensiteRoute FORT = IntensiteRoute.FORT;

  public static void affichePointSurCarte(double longitude, double latitude, int idx) {
    getPane().main.affichePoint(longitude, latitude, idx);
  };

  public static void affichePointSurCarte(double longitude, double latitude) {
    getPane().main.affichePoint(longitude, latitude);
  }
  public static void afficheRouteSurCarte(double longitude1, double latitude1, double longitude2, double latitude2, IntensiteRoute intensite) {
    getPane().main.afficheRoute(longitude1, latitude1, longitude2, latitude2, intensite.value);
  }
  public static void afficheRouteSurCarte(double longitude1, double latitude1, double longitude2, double latitude2) {
    getPane().main.afficheRoute(longitude1, latitude1, longitude2, latitude2);
  }
  public static int distanceEuclidienne(double longitude1, double latitude1, double longitude2, double latitude2) {
    return getPane().main.distanceEuclidienne(longitude1, latitude1, longitude2, latitude2);
  }
  public static void effaceCarte() {
    getPane().main.clearMap();
  }
  public static Map<String, Double> latitudes;
  public static Map<String, Double> longitudes;
  public static Map < String, List < String >> voisins;

  public static List<String> plusCourtCheminGogleMap(String depart, String arrivee) {
    return GogleMapCalculChemins.plusCourtChemin(getPane().main, depart, arrivee);
  }
  public static void parcoursEnLargeur(final String depart) {
    SwingUtilities.invokeLater(new Runnable() {
            @Override
                                 public void run() {
                                   getPane().main.clearMap();
                                   GogleMapParcours.afficheToutesRoutesDirectes(getPane().main);
                                   GogleMapParcours.parcoursLargeur(getPane().main, depart);
                                 }
                               }
                               );
  }
}
