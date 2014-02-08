<?xml version="1.0" encoding="UTF-8"?>
<Algo>
    <description texte="Calcul de la valeur approchée d'une intégrale sur [a,b] par la méthode des trapèzes.&#xa;La fonction est définie dans l'onglet &quot;Utiliser une fonction numérique&quot;" courant="FIN_ALGORITHME" />
    <fonction fctcode="x*x" fctetat="actif" />
    <repere repetat="inactif" repcode="-10#10#-10#10#2#2" />
    <item algoitem="VARIABLES" code="100#declarationsvariables" >
        <item algoitem="a EST_DU_TYPE NOMBRE" code="1#NOMBRE#a" />
        <item algoitem="b EST_DU_TYPE NOMBRE" code="1#NOMBRE#b" />
        <item algoitem="nbtrapezes EST_DU_TYPE NOMBRE" code="1#NOMBRE#nbtrapezes" />
        <item algoitem="pas EST_DU_TYPE NOMBRE" code="1#NOMBRE#pas" />
        <item algoitem="i EST_DU_TYPE NOMBRE" code="1#NOMBRE#i" />
        <item algoitem="approxintegrale EST_DU_TYPE NOMBRE" code="1#NOMBRE#approxintegrale" />
    </item>
    <item algoitem="DEBUT_ALGORITHME" code="101#debutalgo" >
        <item algoitem="LIRE a" code="2#a#pasliste" />
        <item algoitem="LIRE b" code="2#b#pasliste" />
        <item algoitem="LIRE nbtrapezes" code="2#nbtrapezes#pasliste" />
        <item algoitem="SI (nbtrapezes>0 ET b>a) ALORS" code="6#nbtrapezes>0 ET b>a" >
            <item algoitem="DEBUT_SI" code="7#debutsi" />
            <item algoitem="approxintegrale PREND_LA_VALEUR 0" code="5#approxintegrale#0#pasliste" />
            <item algoitem="pas PREND_LA_VALEUR (b-a)/nbtrapezes" code="5#pas#(b-a)/nbtrapezes#pasliste" />
            <item algoitem="POUR i ALLANT_DE 0 A nbtrapezes-1" code="12#i#0#nbtrapezes-1" >
                <item algoitem="DEBUT_POUR" code="13#debutpour" />
                <item algoitem="approxintegrale PREND_LA_VALEUR approxintegrale+pas/2*(F1(a+i*pas)+F1(a+(i+1)*pas))" code="5#approxintegrale#approxintegrale+pas/2*(F1(a+i*pas)+F1(a+(i+1)*pas))#pasliste" />
                <item algoitem="FIN_POUR" code="14#finpour" />
            </item>
            <item algoitem="AFFICHER approxintegrale" code="3#approxintegrale#0#pasliste" />
            <item algoitem="FIN_SI" code="8#finsi" />
        </item>
    </item>
    <item algoitem="FIN_ALGORITHME" code="102#finalgo" />
</Algo>
