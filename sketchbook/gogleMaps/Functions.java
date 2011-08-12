
package org.javascool.proglets.gogleMaps;

import javax.swing.SwingUtilities;

// Used to define the gui
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import java.awt.Dimension;

// Used to define an icon/label
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;

// Used to define a button
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static org.javascool.tools.Macros.assertion;

import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

import org.javascool.tools.Macros;

public class Functions {
  private static int distance(GogleMapPanel g, String ville1, String ville2) {
    if(g.arcs.get(ville1).contains(ville2)) {
      Macros.assertion(g.latitudes.containsKey(ville1), ville1 + " n'est pas une ville connue");
      Macros.assertion(g.latitudes.containsKey(ville2), ville2 + " n'est pas une ville connue");
      return g.distanceEuclidienne(g.longitudes.get(ville1), g.latitudes.get(ville1),
                                   g.longitudes.get(ville2), g.latitudes.get(ville2));
    } else
      return Integer.MAX_VALUE;
  }
  private static String PlusProche(List<String> groupe, Map<String, Integer> distMap) {
    String res = null;
    int distMin = Integer.MAX_VALUE;
    for(String ville : groupe) {
      int distance = distMap.get(ville);
      if(distance < distMin) {
        distMin = distance;
        res = ville;
      }
    }
    return res;
  }
  private static void MiseAjourDistance(GogleMapPanel g, String ville0, Map<String, Integer> distMap, Map<String, String> pred) {
    int distance_ville0 = distMap.get(ville0);
    for(String ville : g.arcs.get(ville0)) {
      int nouvelle_distance = distance_ville0 + distance(g, ville0, ville);
      if(nouvelle_distance < distMap.get(ville)) {
        distMap.put(ville, nouvelle_distance);
        pred.put(ville, ville0);
      }
    }
  }
  public static List<String> plusCourtChemin(GogleMapPanel g, String depart, String arrivee) {
    Map<String, Integer> distanceAuDepart = new HashMap<String, Integer>();
    List<String> aTraite = new ArrayList<String>(g.latitudes.keySet());
    Map<String, String> predecesseur = new HashMap<String, String>();
    int nb_ville = aTraite.size();
    for(String ville : aTraite) {
      if(ville.equals(depart))
        distanceAuDepart.put(ville, 0);
      else if(g.arcs.get(ville).contains(depart)) {
        distanceAuDepart.put(ville, distance(g, ville, depart));
        predecesseur.put(ville, depart);
      } else
        distanceAuDepart.put(ville, Integer.MAX_VALUE);
    }
    aTraite.remove(depart);
    // System.out.println("dist = "+distanceAuDepart);
    for(int i = 1; i < nb_ville; i++) {
      String prochain = PlusProche(aTraite, distanceAuDepart);
      MiseAjourDistance(g, prochain, distanceAuDepart, predecesseur);
      aTraite.remove(prochain);
      // System.out.println("prochain = "+prochain+" dist = "+distanceAuDepart);
    }
    // construction du plus court chemin
    List<String> chemin = new ArrayList<String>();
    String finDuChemin = arrivee;
    while(!finDuChemin.equals(depart)) {
      Macros.sleep(0);
      chemin.add(0, finDuChemin);
      finDuChemin = predecesseur.get(finDuChemin);
    }
    chemin.add(0, depart);
    return chemin;
  }
  //
  // This defines the javascool interface
  //
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
    panel.main.affichePoint(longitude, latitude, idx);
  };

  public static void affichePointSurCarte(double longitude, double latitude) {
    panel.main.affichePoint(longitude, latitude);
  }
  public static void afficheRouteSurCarte(double longitude1, double latitude1, double longitude2, double latitude2, IntensiteRoute intensite) {
    panel.main.afficheRoute(longitude1, latitude1, longitude2, latitude2, intensite.value);
  }
  public static void afficheRouteSurCarte(double longitude1, double latitude1, double longitude2, double latitude2) {
    panel.main.afficheRoute(longitude1, latitude1, longitude2, latitude2);
  }
  public static int distanceEuclidienne(double longitude1, double latitude1, double longitude2, double latitude2) {
    return panel.main.distanceEuclidienne(longitude1, latitude1, longitude2, latitude2);
  }
  public static void effaceCarte() {
    panel.main.clearMap();
  }
  public static Map<String, Double> latitudes;
  public static Map<String, Double> longitudes;
  public static Map<String, List<String> > voisins;

  public static List<String> plusCourtCheminGogleMap(String depart, String arrivee) {
    return plusCourtChemin(panel.main, depart, arrivee);
  }
  public static void parcoursEnLargeur(final String depart) {
    SwingUtilities.invokeLater(new Runnable() {
                                 public void run() {
                                   panel.main.clearMap();
                                   afficheToutesRoutesDirectes(panel.main);
                                   parcoursLargeur(panel.main, depart);
                                 }
                               }
                               );
  }
  //
  // This defines the tests on the panel
  //
  public static void test() {
    /*  Console.clear();
     *  Console.println("Pour la démo . . utiliser les boutons \"Parcours en profondeur\" +  \"Parcours en largeur\" !");*/
  }
  /** Définition de l'interface graphique de la proglet. */
  public static final Panel panel = new Panel();

  private static int numVisite;

  private static void afficheVille(GogleMapPanel g, String s) {
    double latitude = latitudes.get(s);
    double longitude = longitudes.get(s);
    g.affichePoint(longitude, latitude);
  }
  // affiche une ville de nom s en indiquant le num�ro num
  static void afficheVilleAvecNumero(GogleMapPanel g, String s, int num) {
    double latitude = latitudes.get(s);
    double longitude = longitudes.get(s);
    g.affichePoint(longitude, latitude, num);
  }
  static void afficheRouteDirecte(GogleMapPanel g, String ville1, String ville2) {
    g.afficheRoute(longitudes.get(ville1),
                   latitudes.get(ville1),
                   longitudes.get(ville2),
                   latitudes.get(ville2),
                   1);
  }
  // Affiche toutes les routes directes
  static void afficheToutesRoutesDirectes(GogleMapPanel g) {
    for(String depart : g.arcs.keySet())
      for(String arrivee : g.arcs.get(depart))
        if(depart.compareTo(arrivee) > 0)
          afficheRouteDirecte(g, depart, arrivee);
  }
  static void parcoursRec(GogleMapPanel g, Set<String> vu, String ville1) {
    // Macros.sleep(500);
    vu.add(ville1);
    afficheVilleAvecNumero(g, ville1, numVisite++);
    for(String ville2 : g.arcs.get(ville1))
      afficheVille(g, ville2);
    Macros.sleep(500);
    for(String ville2 : g.arcs.get(ville1))
      if(!vu.contains(ville2))
        parcoursRec(g, vu, ville2);
  }
  static void parcoursProfondeur(GogleMapPanel g, String depart) {
    Set<String> vu = new HashSet<String>();
    numVisite = 1;
    parcoursRec(g, vu, depart);
  }
  static void parcoursLargeur(GogleMapPanel g, String depart) {
    Set<String> vu = new HashSet<String>();
    Queue<String> aVoir = new LinkedList<String>();
    aVoir.offer(depart);
    int numVisite = 1;
    while(!aVoir.isEmpty()) {
      String ville1 = aVoir.remove();
      vu.add(ville1);
      afficheVilleAvecNumero(g, ville1, numVisite++);
      Macros.sleep(500);
      for(String ville2 : g.arcs.get(ville1))
        if(!vu.contains(ville2)) {
          afficheVille(g, ville2);
          if(!aVoir.contains(ville2))
            aVoir.offer(ville2);
        }
    }
  }
}
