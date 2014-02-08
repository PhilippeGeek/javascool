<?xml version="1.0" encoding="UTF-8"?>
<Algo>
    <description texte="" courant="FIN_POUR"/>
    <extension extnom="inactif"/>
    <fonction fctcode="" fctetat="inactif"/>
    <F2 F2defaut="" F2lignes="" F2etat="inactif" F2para=""/>
    <repere repetat="inactif" repcode="-10#10#-10#10#2#2"/>
    <item algoitem="VARIABLES" code="100#declarationsvariables">
        <item algoitem="prixUnitaire EST_DU_TYPE NOMBRE" code="1#NOMBRE#prixUnitaire"/>
        <item algoitem="quantite EST_DU_TYPE NOMBRE" code="1#NOMBRE#quantite"/>
        <item algoitem="prixTotal EST_DU_TYPE NOMBRE" code="1#NOMBRE#prixTotal"/>
        <item algoitem="remise EST_DU_TYPE NOMBRE" code="1#NOMBRE#remise"/>
        <item algoitem="n EST_DU_TYPE NOMBRE" code="1#NOMBRE#n"/>
        <item algoitem="k EST_DU_TYPE NOMBRE" code="1#NOMBRE#k"/>
    </item>
    <item algoitem="DEBUT_ALGORITHME" code="101#debutalgo">
        <item algoitem="LIRE n" code="2#n#pasliste"/>
        <item algoitem="POUR k ALLANT_DE 1 A n" code="12#k#1#n">
            <item algoitem="DEBUT_POUR" code="13#debutpour"/>
            <item algoitem="LIRE prixUnitaire" code="2#prixUnitaire#pasliste"/>
            <item algoitem="LIRE quantite" code="2#quantite#pasliste"/>
            <item algoitem="SI   (quantite==1)   ALORS" code="6#(quantite==1)">
                <item algoitem="DEBUT_SI" code="7#debutsi"/>
                <item algoitem="remise PREND_LA_VALEUR 0" code="5#remise#0#pasliste"/>
                <item algoitem="FIN_SI" code="8#finsi"/>
                <item algoitem="SINON" code="9#sinon">
                    <item algoitem="DEBUT_SINON" code="10#debutsinon"/>
                    <item algoitem="SI   ( (quantite==2) OU (quantite==3) )   ALORS" code="6#( (quantite==2) OU (quantite==3) )">
                        <item algoitem="DEBUT_SI" code="7#debutsi"/>
                        <item algoitem="remise PREND_LA_VALEUR 0.1" code="5#remise#0.1#pasliste"/>
                        <item algoitem="FIN_SI" code="8#finsi"/>
                        <item algoitem="SINON" code="9#sinon">
                            <item algoitem="DEBUT_SINON" code="10#debutsinon"/>
                            <item algoitem="SI   ( (quantite==4) OU (quantite==5) )   ALORS" code="6#( (quantite==4) OU (quantite==5) )">
                                <item algoitem="DEBUT_SI" code="7#debutsi"/>
                                <item algoitem="remise PREND_LA_VALEUR 0.2" code="5#remise#0.2#pasliste"/>
                                <item algoitem="FIN_SI" code="8#finsi"/>
                                <item algoitem="SINON" code="9#sinon">
                                    <item algoitem="DEBUT_SINON" code="10#debutsinon"/>
                                    <item algoitem="remise PREND_LA_VALEUR 0.3" code="5#remise#0.3#pasliste"/>
                                    <item algoitem="FIN_SINON" code="11#finsinon"/>
                                </item>
                            </item>
                            <item algoitem="FIN_SINON" code="11#finsinon"/>
                        </item>
                    </item>
                    <item algoitem="FIN_SINON" code="11#finsinon"/>
                </item>
            </item>
            <item algoitem="prixTotal PREND_LA_VALEUR quantite*prixUnitaire*(1-remise)" code="5#prixTotal#quantite*prixUnitaire*(1-remise)#pasliste"/>
            <item algoitem="AFFICHER &quot;prixTotal : &quot;" code="4#prixTotal : #1"/>
            <item algoitem="AFFICHER prixTotal" code="3#prixTotal#1#pasliste"/>
            <item algoitem="FIN_POUR" code="14#finpour"/>
        </item>
    </item>
    <item algoitem="FIN_ALGORITHME" code="102#finalgo"/>
</Algo>
