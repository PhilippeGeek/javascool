<?xml version="1.0" encoding="UTF-8"?>
<Algo>
    <description texte="" courant="AFFICHER total"/>
    <extension extnom="inactif"/>
    <fonction fctcode="" fctetat="inactif"/>
    <F2 F2defaut="" F2lignes="" F2etat="inactif" F2para=""/>
    <repere repetat="inactif" repcode="-10#10#-10#10#2#2"/>
    <item algoitem="VARIABLES" code="100#declarationsvariables">
        <item algoitem="tab EST_DU_TYPE LISTE" code="1#LISTE#tab"/>
        <item algoitem="n EST_DU_TYPE NOMBRE" code="1#NOMBRE#n"/>
        <item algoitem="total EST_DU_TYPE NOMBRE" code="1#NOMBRE#total"/>
        <item algoitem="k EST_DU_TYPE NOMBRE" code="1#NOMBRE#k"/>
    </item>
    <item algoitem="DEBUT_ALGORITHME" code="101#debutalgo">
        <item algoitem="tab[1] PREND_LA_VALEUR 12.5" code="5#tab#12.5#1"/>
        <item algoitem="tab[2] PREND_LA_VALEUR 10.5" code="5#tab#10.5#2"/>
        <item algoitem="tab[3] PREND_LA_VALEUR 10" code="5#tab#10#3"/>
        <item algoitem="n PREND_LA_VALEUR 3" code="5#n#3#pasliste"/>
        <item algoitem="total PREND_LA_VALEUR 0" code="5#total#0#pasliste"/>
        <item algoitem="POUR k ALLANT_DE 1 A n" code="12#k#1#n">
            <item algoitem="DEBUT_POUR" code="13#debutpour"/>
            <item algoitem="total PREND_LA_VALEUR total + tab[k]" code="5#total#total + tab[k]#pasliste"/>
            <item algoitem="FIN_POUR" code="14#finpour"/>
        </item>
        <item algoitem="AFFICHER &quot;total : &quot;" code="4#total : #0"/>
        <item algoitem="AFFICHER total" code="3#total#0#pasliste"/>
    </item>
    <item algoitem="FIN_ALGORITHME" code="102#finalgo"/>
</Algo>
