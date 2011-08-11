package org.javascool.proglets.sampleCode;
import java.awt.Panel;
import java.awt.Label;
import java.awt.BorderLayout;
import java.lang.String;

public class ProgletPanel extends Panel {
  /** Correspond au label affiché dans le panel de la proglet */
  private Label label;

  /** Construit un nouveau panel */
  public ProgletPanel() {
    // On crée un label
    label = new Label("");
    // Et on l'ajoute au panel
    add(label, BorderLayout.NORTH);
  }
  /** Change le texte affiché dans le label */
  public void setText(String text) {
    label.setText(text);
  }
}
