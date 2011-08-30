<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output 
  method="text"
  encoding="UTF-8"/>

<!-- A la racine: il faut le tag keywords -->

<xsl:template match="/">
  <xsl:apply-templates select="*[not (name(.) = 'keywords')]" mode="error"/>
  <xsl:apply-templates/>
</xsl:template>

<!-- keywords : ne doit contenir que (0 à n) keyword -->

<xsl:template match="keywords">
  <xsl:apply-templates select="@*|*[not (name(.) = 'keyword')]|text()" mode="error"/>
  <xsl:apply-templates/>
</xsl:template>

<!-- keyword doit contenir les attributs name et title et les elements code et doc (optionel) -->

<xsl:template match="keyword">
  <xsl:if test="count(@name) = 0"><xsl:call-template name="no-attribute"><xsl:with-param name="name" select="'name'"/></xsl:call-template></xsl:if>
  <xsl:if test="count(@title) = 0"><xsl:call-template name="no-attribute"><xsl:with-param name="name" select="'title'"/></xsl:call-template></xsl:if>
  <xsl:if test="count(code) = 0"><xsl:call-template name="no-element"><xsl:with-param name="name" select="'code'"/></xsl:call-template></xsl:if>
  <xsl:if test="count(doc) = 0"><xsl:call-template name="no-element"><xsl:with-param name="name" select="'doc'"/></xsl:call-template></xsl:if>
  <xsl:apply-templates select="@*[not (name(.) = 'name' or name(.) = 'title')]" mode="error"/>
  <xsl:apply-templates select="*[not (name(.) = 'doc' or name(.) = 'code')]|text()" mode="error"/>
  <xsl:apply-templates/>
</xsl:template>

<!-- doc|code ne doivent contenir que du texte -->

<xsl:template match="code|doc">
  <xsl:apply-templates select="@*|*" mode="error"/>
</xsl:template>

<!-- Skip des espaces et affichage des erreurs -->

<xsl:template match="text()"/>

<xsl:template name="no-attribute">
  <xsl:param name="name"/>
  <xsl:message terminate="yes">Missing attribute: <xsl:value-of select="$name"/> </xsl:message>
</xsl:template>

<xsl:template name="no-element">
  <xsl:param name="name"/>
  <xsl:message terminate="yes">Missing element: <xsl:value-of select="$name"/> </xsl:message>
</xsl:template>

<xsl:template match="*" mode="error">
  <xsl:message terminate="yes">Unexpected tag: <xsl:value-of select="name(.)"/> </xsl:message>
</xsl:template>

<xsl:template match="text()" mode="error">
  <xsl:if test="not (normalize-space(.) = '')">
    <xsl:message terminate="yes">Unexpected text: <xsl:value-of select="."/> </xsl:message>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>


<!--
<keywords>
    <keyword name="readString" title="Stocke une chaîne de caractères dans une variable">
        <doc>Crée une variable s qui va contenir une chaîne de caractères demandé à l'utilisateur.
Il peut prendre un paramètre "question" de type String qui décrit la valeur à entrer (optionel).</doc>
        <code>String s = readString("question");</code>
-->
