package org.javascool.core;

/** Modèle d'interface pour les proglets.
 * <p>Cette interface montre les méthodes prises en compte pour des proglets, 
 * mais il est  <i>inutile</i> d'implémenter cet interface donnée ici pour information uniquement: 
 * les méthodes sont identifiées à l'exécution.</p>
 *
 * @see <a href="Proglet.java.html">code source</a>
 * @serial exclude
 */
public interface Proglet {
  /** Permet de construire l'applet, initialiser les objets graphiques, gestionnaires d'événements, etc.. */
  public void init();
  /** Permet, à la fermeture de la proglet, doit détruire ce que init() et l'élève ont fait. */
  public void destroy();

  /** Cette méthode est appelée que pour lancer une démonstration du fonctionnement de la proglet.
   * <p>Elle n'est pas appellée lors de son fonctionnement en Proglet sauf les proglets <a href="http://processing.org"processing</a> (qui sont en fonctionnement continu).</p>
   */
  public void start();
  /** Appellée à l'arrêt de la démonstration. */
  public void stop();
}
