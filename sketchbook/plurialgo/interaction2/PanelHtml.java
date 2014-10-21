/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.javascool.gui.EditorWrapper;
import org.javascool.proglets.plurialgo.divers.Divers;



/**
 * Cette classe correspond à l'onglet Documentation de l'interface graphique.
*/
public class PanelHtml extends JPanel implements ActionListener, HyperlinkListener {
	private static final long serialVersionUID = 1L;
	
	private String accueil_page = "accueil.html";	
	private PanelInteraction pInter;	
	private JEditorPane htmlEdit;
	private JButton accueilButton;
	private JButton precedentButton;
	private JButton contactButton;
	private JButton boutonsButton;
	private String[] sites;
	private int i_site;
	private JPopupMenu popup;
	
	public PanelHtml (PanelInteraction pInter) {
		this.setLayout( new BorderLayout() );
		this.setPreferredSize(new Dimension(200,200));
		this.pInter = pInter;
		initEdition ();
		initBoutons();
		initPopupMenus();
		sites = new String[50];
		i_site = 0;
		sites[0] = PanelInteraction.urlDoc + accueil_page;
		this.showHtml(PanelInteraction.urlDoc + accueil_page);
	}

	private void initEdition() {
		htmlEdit = new JEditorPane(); htmlEdit.setBackground(null);
		htmlEdit.setEditable(false);
		this.setBackground(null);
		this.add(new JScrollPane(htmlEdit),"Center");
		htmlEdit.addHyperlinkListener(this);
	}
	
	private void initBoutons() {
		precedentButton = new JButton("Precedent"); precedentButton.addActionListener(this);
		accueilButton = new JButton("Accueil"); accueilButton.addActionListener(this);
		boutonsButton = new JButton("?"); boutonsButton.addActionListener(this);
		contactButton = new JButton("Auteur"); contactButton.addActionListener(this);
		JPanel p = new JPanel();
        p.add(precedentButton); p.add(Box.createHorizontalStrut(5)); 
        p.add(accueilButton); p.add(Box.createHorizontalStrut(5));
        p.add(boutonsButton); p.add(Box.createHorizontalStrut(5));
        p.add(contactButton); p.add(Box.createHorizontalStrut(5));
		this.add(p,"South");
	}	
	
	private void initPopupMenus() {
		popup = new JPopupMenu();
		JMenuItem menu;
		menu = new JMenuItem("Copier"); menu.addActionListener(this); menu.setActionCommand("copier");
		popup.add(menu);
		htmlEdit.setComponentPopupMenu(popup);
		this.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent Me){
				if(Me.isPopupTrigger()){
					popup.show(Me.getComponent(), Me.getX(), Me.getY());
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
		if (e.getSource() == this.precedentButton) {
			if (i_site > 0) {
				i_site = i_site - 1;
				this.showHtml(sites[i_site]);	
			}
		}
		else if (e.getSource() == this.accueilButton) {
			i_site = 0;
			sites[0] = PanelInteraction.urlDoc + accueil_page;
			this.showHtml(PanelInteraction.urlDoc + accueil_page);
		}
		else if (e.getSource() == this.boutonsButton) {
			consulter("boutons.html");
		}
		else if (e.getSource() == this.contactButton) {
			this.contacter();
		}
        else if ("copier".equals(cmd)) {
            htmlEdit.copy();
        }
	}
	
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			String desc = e.getDescription();
			if (desc.startsWith("http")) {
				if(java.awt.Desktop.isDesktopSupported()){
					if (java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)){
						java.awt.Desktop dt = java.awt.Desktop.getDesktop();
						try {
							dt.browse(new URI(desc));
						} catch (Exception ex) {
						} 
					}
				}
			}
			else if (desc.endsWith(".html")) {
				pInter.clearConsole();
				if (i_site < sites.length - 1) i_site = i_site+1;
				sites[i_site] = PanelInteraction.urlDoc + desc;
				this.showHtml(PanelInteraction.urlDoc + desc);	
			}
			else if (desc.endsWith(".txt")||desc.endsWith(".jvs")||desc.endsWith(".bas")||desc.endsWith(".alg")) {
				String nom_url = PanelInteraction.urlDoc + desc;
				pInter.clearConsole();
				StringBuffer buf = this.ouvrir(nom_url);
				if (buf!=null) {
					this.pInter.setText(buf.toString());
					if (desc.equals("ex_intro_nom_sel_jvs.txt")) {
						try {
							JTextArea editArea = EditorWrapper.getRTextArea();
							editArea.requestFocusInWindow();
							int i_start = editArea.getLineStartOffset(7);
							editArea.setCaretPosition(i_start);
							int i_end = editArea.getLineEndOffset(14);
							editArea.select(i_start, i_end-1);
							
						}
						catch(Exception ex) {
							System.out.println(ex);
						}						
					}
					if (desc.startsWith("ex_note_lire_sel") && desc.endsWith(".txt")) {
						try {
							JTextArea editArea = EditorWrapper.getRTextArea();
							editArea.requestFocusInWindow();
							int lig_start = 3;
							int lig_end = 3;
							if (desc.endsWith("_py.txt")) {
								lig_start = 1;
								lig_end = 1;
							}
							if (desc.endsWith("_larp.txt")) {
								lig_start = 2;
								lig_end = 3;
							}
							int i_start = editArea.getLineStartOffset(lig_start);
							editArea.setCaretPosition(i_start);
							int i_end = editArea.getLineEndOffset(lig_end);
							editArea.select(i_start, i_end-1);
							
						}
						catch(Exception ex) {
							System.out.println(ex);
						}						
					}
					if (desc.startsWith("ex_demo_sel") && desc.endsWith(".txt")) {
						try {
							JTextArea editArea = EditorWrapper.getRTextArea();
							editArea.requestFocusInWindow();
							int lig_start = 14;
							int lig_end = 25;
							if (desc.endsWith("_py.txt")) {
								lig_start = 5;
								lig_end = 13;
							}
							if (desc.endsWith("_larp.txt")) {
								lig_start = 9;
								lig_end = 17;
							}
							if (desc.endsWith("_bas.txt")) {
								lig_start = 13;
								lig_end = 21;
							}
							if (desc.endsWith("_xcas.txt")) {
								lig_start = 5;
								lig_end = 17;
							}
							int i_start = editArea.getLineStartOffset(lig_start);
							editArea.setCaretPosition(i_start);
							int i_end = editArea.getLineEndOffset(lig_end);
							editArea.select(i_start, i_end-1);
							
						}
						catch(Exception ex) {
							System.out.println(ex);
						}						
					}
					if (desc.startsWith("ex_si_sel") && desc.endsWith(".txt")) {
						try {
							JTextArea editArea = EditorWrapper.getRTextArea();
							editArea.requestFocusInWindow();
							int lig_start = 12;
							int lig_end = 20;
							if (desc.endsWith("_py.txt")) {
								lig_start = 4;
								lig_end = 10;
							}
							if (desc.endsWith("_larp.txt")) {
								lig_start = 9;
								lig_end = 15;
							}
							if (desc.endsWith("_bas.txt")) {
								lig_start = 12;
								lig_end = 19;
							}
							if (desc.endsWith("_xcas.txt")) {
								lig_start = 4;
								lig_end = 12;
							}
							int i_start = editArea.getLineStartOffset(lig_start);
							editArea.setCaretPosition(i_start);
							int i_end = editArea.getLineEndOffset(lig_end);
							editArea.select(i_start, i_end-1);
							
						}
						catch(Exception ex) {
							System.out.println(ex);
						}						
					}
				}				
			}
			else if (desc.endsWith(".xml")) {
				desc = desc.substring(0, desc.length()-4);
				String nom_url = PanelInteraction.urlDoc + desc + ".xml";
				StringBuffer buf = this.ouvrir(nom_url);
				if (buf!=null) {
					Divers.remplacerSpeciaux(buf);
					pInter.setXml(buf.toString());
					pInter.traduireXml();
					pInter.clearConsole();
					pInter.writeConsole("ouverture de " + nom_url);
				}
				else {
					pInter.clearConsole();
					pInter.writeConsole("url inexistante ou indisponible : " + nom_url + "\n");
				}
			}
			else if (desc.endsWith(".si")) {
				desc = desc.substring(0, desc.length()-3);	
				pInter.pSi.effacer();
				if (desc.equals("ex_tri_1")) {
					pInter.pSi.setSi(0, 0, "x1", "<=", "x2", "ET", "x2", "<=", "x3");
					pInter.pSi.setSi(1, 0, "x1", "<=", "x3", "ET", "x3", "<=", "x2");
					pInter.pSi.setSi(2, 0, "x2", "<=", "x1", "ET", "x1", "<=", "x3");
					pInter.pSi.setSi(3, 0, "x2", "<=", "x3", "ET", "x3", "<=", "x1");
					pInter.pSi.setSi(4, 0, "x3", "<=", "x1", "ET", "x1", "<=", "x2");
					pInter.pSi.setSi(5, 0, "");
				}	
				else if (desc.equals("ex_tri_2")) {
					pInter.pSi.setSi(0, 0, "x1", "<=", "x2");
					pInter.pSi.setSi(1, 1, "x3", "<=", "x1");
					pInter.pSi.setSi(2, 1, "x3", "<=", "x2");
					pInter.pSi.setSi(3, 1, "");
					pInter.pSi.setSi(4, 0, "");
					pInter.pSi.setSi(5, 1, "x3", "<=", "x2");
					pInter.pSi.setSi(6, 1, "x3", "<=", "x1");
					pInter.pSi.setSi(7, 1, "");
				}
				if (desc.equals("ex_si")) {
					pInter.pSi.setSi(0, 0, "quantite", "==", "1");
					pInter.pSi.setSi(1, 0, "quantite", "==", "2", "OU", "quantite", "==", "3");
					pInter.pSi.setSi(2, 0, "quantite", "==", "4", "OU", "quantite", "==", "5");
					pInter.pSi.setSi(3, 0, "");
				}		
				pInter.selectPanel(pInter.pSi);			
			}
			else if (desc.endsWith(".boucle")) {
				desc = desc.substring(0, desc.length()-7);		
				pInter.pBoucles.effacer();
				if (desc.equals("ex_impair")) {
					pInter.pBoucles.setPour("k", "1", "99", "2");
					pInter.pBoucles.setSomme(true, "som:1/k");
				}	
				else if (desc.equals("ex_intro_nom_jvs")) {
					pInter.pBoucles.setPour("k", "1", "n", "1");
					pInter.pBoucles.setSomme(true, "totalCommande:prixTotal");
				}			
				else if (desc.equals("ex_tab_bon_form")) {
					pInter.pBoucles.setPour("k", "1", "n", "1");
					pInter.pBoucles.setSomme(true, "totalCommande:quantite[k]*prixUnitaire[k]");						
				}		
				else if (desc.equals("ex_tab_bon_fich") || desc.equals("ex_tab_bon_sql")) {
					pInter.pBoucles.setPour("k", "1", "n_lig", "1");
					pInter.pBoucles.setSomme(true, "totalCommande:quantite[k]*prixUnitaire[k]");						
				}
				else if (desc.equals("ex_intro")) {
					pInter.pBoucles.setPour("k", "1", "n", "1");
				}
				else if (desc.equals("ex_note_somme")) {
					pInter.pBoucles.setPour("k", "1", "n", "1");
					pInter.pBoucles.setSomme(true, "total:note");
				}
				else if (desc.equals("ex_note_comptage")) {
					pInter.pBoucles.setPour("k", "1", "n", "1");
					pInter.pBoucles.setCompterVar(true, "nbAdmis");
					pInter.pBoucles.setCompterCondition("note",">=","10");
				}
				else if (desc.equals("ex_note_minmax")) {
					pInter.pBoucles.setPour("k", "1", "n", "1");
					//pInter.pBoucles.setPourOption("tantque");
					pInter.pBoucles.setMinimum(true, "mini:note");
					pInter.pBoucles.setMaximum(true, "maxi:note");
				}
				else if (desc.equals("ex_note_rech")) {
					pInter.pBoucles.setPour("", "", "", "");
					pInter.pBoucles.setBoucle("tantque");
					pInter.pBoucles.setBoucleCondition("note","<","0","OU","note",">","20");
				}				
				pInter.selectPanel(pInter.pBoucles);			
			}
			else if (desc.endsWith(".princ")) {
				desc = desc.substring(0, desc.length()-6);
				pInter.pPrincipal.algoField.setText("exemple");
				pInter.pPrincipal.donneesField.setText(""); 
				pInter.pPrincipal.resultatsField.setText("");
				pInter.pPrincipal.entiersField.setText(""); 
				pInter.pPrincipal.reelsField.setText(""); 
				pInter.pPrincipal.textesField.setText("");
				pInter.pPrincipal.booleensField.setText("");
				pInter.pPrincipal.tab_entiersField.setText(""); 
				pInter.pPrincipal.tab_reelsField.setText(""); 
				pInter.pPrincipal.tab_textesField.setText("");
				pInter.pPrincipal.tab_booleensField.setText("");
				pInter.pPrincipal.mat_entiersField.setText(""); 
				pInter.pPrincipal.mat_reelsField.setText(""); 
				pInter.pPrincipal.mat_textesField.setText("");
				pInter.pPrincipal.mat_booleensField.setText("");
				pInter.pPrincipal.niv_saisieList.setSelectedIndex(0);
				pInter.pPrincipal.niv_affichageList.setSelectedIndex(0);
				pInter.pPrincipal.niv_calculList.setSelectedIndex(0);
				pInter.pPrincipal.niv_groupementList.setSelectedIndex(0);
				pInter.pPrincipal.groupeField.setText("");
				// instructions conditionnelles et sous-programmes
				if (desc.startsWith("ex_si") || desc.startsWith("ex_fonct") || desc.startsWith("ex_proc") || desc.equals("ex_intro")) {
					pInter.pPrincipal.donneesField.setText("prixUnitaire quantite"); 
					pInter.pPrincipal.resultatsField.setText("prixTotal");
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("prixTotal prixUnitaire remise"); 
				}	
				if (desc.equals("ex_fonct")) {
					pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
				}
				if (desc.equals("ex_fonct_remise")) {
					pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
					pInter.pPrincipal.donneesField.setText("quantite"); 
					pInter.pPrincipal.resultatsField.setText("remise");
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("remise"); 
				}		
				if (desc.equals("ex_proc_out")) {
					pInter.pPrincipal.resultatsField.setText("prixTotal remise");
					pInter.pPrincipal.niv_calculList.setSelectedIndex(1);	// procedure
				}	
				if (desc.equals("ex_proc_in")) {
					pInter.pPrincipal.resultatsField.setText("");
					pInter.pPrincipal.niv_calculList.setSelectedIndex(1);	// procedure
				}
				if (desc.equals("ex_proc_inout")) {
					pInter.pPrincipal.donneesField.setText("quantite prixTotal"); 
					pInter.pPrincipal.resultatsField.setText("prixTotal");
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("prixTotal remise"); 
					pInter.pPrincipal.niv_calculList.setSelectedIndex(1);	// procedure
				}	
				if (desc.equals("ex_intro")) {
					pInter.pPrincipal.reelsField.setText("prixTotal prixUnitaire"); 
				}
				// vecteurs
				if (desc.startsWith("ex_tab_bon")) {
					pInter.pPrincipal.donneesField.setText("n nom prixUnitaire quantite"); 
					pInter.pPrincipal.resultatsField.setText("totalCommande");
					pInter.pPrincipal.entiersField.setText("n"); 
					pInter.pPrincipal.reelsField.setText("totalCommande");
					pInter.pPrincipal.tab_entiersField.setText("quantite"); 
					pInter.pPrincipal.tab_reelsField.setText("prixUnitaire"); 
					pInter.pPrincipal.tab_textesField.setText("nom"); 
					pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
				}		
				if (desc.equals("ex_tab_bon_form")) {
					pInter.pPrincipal.niv_saisieList.setSelectedIndex(2);	// formulaire
				}
				if (desc.equals("ex_tab_bon_fich")) {
					pInter.pPrincipal.donneesField.setText("nom prixUnitaire quantite"); 
					pInter.pPrincipal.entiersField.setText(""); 
					pInter.pPrincipal.niv_saisieList.setSelectedIndex(3);	// sql
					pInter.pPrincipal.niv_calculList.setSelectedIndex(0);	// elementaire
				}
				if (desc.equals("ex_tab_bon_sql")) {
					pInter.pPrincipal.donneesField.setText("nom prixUnitaire quantite"); 
					pInter.pPrincipal.entiersField.setText(""); 
					pInter.pPrincipal.niv_saisieList.setSelectedIndex(4);	// sql
					pInter.pPrincipal.niv_calculList.setSelectedIndex(0);	// elementaire
				}
				if (desc.startsWith("ex_tab_intro")) {
					pInter.pPrincipal.donneesField.setText("n tab"); 
					pInter.pPrincipal.resultatsField.setText("total");
					pInter.pPrincipal.entiersField.setText("n"); 
					pInter.pPrincipal.reelsField.setText("total");
					pInter.pPrincipal.tab_reelsField.setText("tab");		
					if (desc.equals("ex_tab_intro_fonct")) { 
						pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
					}
				}			
				// matrices
				if (desc.startsWith("ex_mat_intro")) {
					pInter.pPrincipal.donneesField.setText("nbLignes nbColonnes tab"); 
					pInter.pPrincipal.resultatsField.setText("total");
					pInter.pPrincipal.entiersField.setText("nbLignes nbColonnes");
					pInter.pPrincipal.reelsField.setText("total"); 
					pInter.pPrincipal.mat_reelsField.setText("tab"); 
				}
				// enregistrements : point
				if (desc.startsWith("ex_enreg_point")||desc.startsWith("ex_objet_point")) {
					pInter.pPrincipal.donneesField.setText("x1 y1 x2 y2"); 
					pInter.pPrincipal.resultatsField.setText("distance");
					pInter.pPrincipal.reelsField.setText("x1 y1 x2 y2 distance");
					pInter.pPrincipal.groupeField.setText("Point");
					if (desc.startsWith("ex_enreg_point")) {
						pInter.pPrincipal.niv_groupementList.setSelectedIndex(1);	// enregistrement
					}
					if (desc.startsWith("ex_objet_point")) {
						pInter.pPrincipal.niv_groupementList.setSelectedIndex(2);	// classe
					}
				}
				if (desc.equals("ex_enreg_point_proc")||desc.equals("ex_enreg_point_proc_transfo")) {
					pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
					pInter.pPrincipal.niv_saisieList.setSelectedIndex(1);	// procedure
				}
				if (desc.equals("ex_objet_point_proc")||desc.equals("ex_objet_point_proc_transfo")) {
					pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
					pInter.pPrincipal.niv_saisieList.setSelectedIndex(1);	// procedure
				}
				// enregistrements : bon de commande
				if (desc.startsWith("ex_enreg_bon")||desc.startsWith("ex_objet_bon")) {
					pInter.pPrincipal.donneesField.setText("n nom prixUnitaire quantite"); 
					pInter.pPrincipal.resultatsField.setText("totalCommande");
					pInter.pPrincipal.entiersField.setText("n,i"); 
					pInter.pPrincipal.reelsField.setText("totalCommande");
					pInter.pPrincipal.tab_entiersField.setText("quantite"); 
					pInter.pPrincipal.tab_reelsField.setText("prixUnitaire"); 
					pInter.pPrincipal.tab_textesField.setText("nom"); 
					pInter.pPrincipal.groupeField.setText("Bon:n,nom,prixUnitaire,quantite");	
					if (desc.startsWith("ex_enreg_bon")) {
						pInter.pPrincipal.niv_groupementList.setSelectedIndex(1);	// enregistrement
					}
					if (desc.startsWith("ex_objet_bon")) {
						pInter.pPrincipal.niv_groupementList.setSelectedIndex(2);	// classes
					}
					if (desc.equals("ex_enreg_bon_proc")||desc.equals("ex_enreg_bon_proc_transfo")) {
						pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
						pInter.pPrincipal.niv_saisieList.setSelectedIndex(1);	// procedure
					}
					if (desc.equals("ex_objet_bon_proc")||desc.equals("ex_objet_bon_proc_transfo")) {
						pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
						pInter.pPrincipal.niv_saisieList.setSelectedIndex(1);	// procedure
					}
				}
				// enregistrements : articles
				if (desc.startsWith("ex_enreg_article")||desc.startsWith("ex_objet_article")) {
					pInter.pPrincipal.donneesField.setText("nom prixUnitaire quantite"); 
					pInter.pPrincipal.resultatsField.setText("prixTotal");
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("prixTotal prixUnitaire"); 
					pInter.pPrincipal.textesField.setText("nom"); 
					pInter.pPrincipal.groupeField.setText("Article:nom,prixUnitaire");	
					if (desc.startsWith("ex_enreg_article")) {
						pInter.pPrincipal.niv_groupementList.setSelectedIndex(1);	// enregistrement
					}
					if (desc.startsWith("ex_objet_article")) {
						pInter.pPrincipal.niv_groupementList.setSelectedIndex(2);	// classes
					}
					if (desc.endsWith("_proc")) {
						pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
						pInter.pPrincipal.niv_saisieList.setSelectedIndex(1);	// procedure
					}
				}
				// civilite
				if (desc.startsWith("ex_civilite")) {
					pInter.pPrincipal.donneesField.setText("nom age sexe"); 
					pInter.pPrincipal.resultatsField.setText("nom age sexe");
					pInter.pPrincipal.textesField.setText("nom sexe"); 
					pInter.pPrincipal.reelsField.setText("age");
					pInter.pPrincipal.niv_saisieList.setSelectedIndex(2); // formulaire
				}
				// exemples pour le bouton Inserer
				if (desc.equals("ex_inserer_var")) {
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("prixTotal prixUnitaire"); 
				}
				if (desc.equals("ex_inserer_var_nom")) {
					pInter.pPrincipal.textesField.setText("nom"); 
				}
				if (desc.equals("ex_inserer_entree")) {
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("prixUnitaire"); 
					pInter.pPrincipal.donneesField.setText("prixUnitaire quantite"); 
				}
				if (desc.equals("ex_inserer_entree_nom")) {
					pInter.pPrincipal.textesField.setText("nom"); 
					pInter.pPrincipal.donneesField.setText("nom"); 
				}
				if (desc.equals("ex_inserer_sortie")) {
					pInter.pPrincipal.reelsField.setText("prixTotal"); 
					pInter.pPrincipal.resultatsField.setText("prixTotal"); 
				}
				if (desc.equals("ex_inserer_fonct")) {
					pInter.pPrincipal.entiersField.setText("quantite"); 
					pInter.pPrincipal.reelsField.setText("prixTotal prixUnitaire"); 
					pInter.pPrincipal.donneesField.setText("prixUnitaire quantite");
					pInter.pPrincipal.resultatsField.setText("prixTotal");  
					pInter.pPrincipal.niv_calculList.setSelectedIndex(2);	// fonction
				}
				// exemple pour options de transformation 1-n
				if (desc.equals("ex_note_lire")) {
					pInter.pPrincipal.donneesField.setText("note"); 
					pInter.pPrincipal.reelsField.setText("note"); 
				}	
				// selection du panel principal
				pInter.selectPanel(pInter.pPrincipal);
			}
		}
	}
	
	// ---------------------------------------------
	// Commandes
	// ---------------------------------------------
	
	private void showHtml(String url) {
		try {
			if (PanelInteraction.urlDoc.startsWith("/")) {
				htmlEdit.setPage(getClass().getResource(url));
			}
			else {
				htmlEdit.setPage(url);
			}
		}
		catch(Exception ex){
			pInter.clearConsole();
			pInter.writeConsole("url inexistante ou indisponible : " + url + "\n");
			ex.printStackTrace();
		}
	}	
	
	private void contacter() {
		pInter.clearConsole();
		pInter.writeConsole("Patrick Raffinat\n");
		pInter.writeConsole("email : patrick.raffinat@univ-pau.fr\n");
		pInter.writeConsole("web : http://web.univ-pau.fr/~raffinat/plurialgo\n");
		pInter.writeConsole("IUT des Pays de l'Adour\n");
		pInter.writeConsole("Universite de Pau et des Pays de l'Adour\n");
		pInter.writeConsole("Avenue de l'universite 64000 PAU (France)\n");
	}
	
	void consulter(String page) {
		if (i_site < sites.length - 1) i_site = i_site+1;
		sites[i_site] = PanelInteraction.urlDoc + page;
		this.showHtml(PanelInteraction.urlDoc + page);			
	}
	
	private StringBuffer ouvrir(String nom_f) {
		StringBuffer buf = new StringBuffer();
		try {
			BufferedReader in = null;
			if (nom_f.startsWith("http") || nom_f.startsWith("file:")) {
				URL url = new URL(nom_f);
				InputStreamReader  reader =  new InputStreamReader( url.openStream());
				in = new BufferedReader( reader );
			}
			else if (PanelInteraction.urlDoc.startsWith("/")){
				InputStream is = getClass().getResourceAsStream(nom_f);
				InputStreamReader isr = new InputStreamReader(is);
				in = new BufferedReader(isr);
			}
			else {
				in = new BufferedReader(new FileReader(nom_f));
			}
			String ligne;
			while ((ligne=in.readLine())!=null) {
				buf.append(ligne + "\n");
			}
		} 
		catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
		return buf;
	}
		
}
