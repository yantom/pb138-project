<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="utf-8" method="xml" indent="yes" />

<xsl:template match="SYNSET">
<SYNSET>
      <xsl:apply-templates/>
</SYNSET><xsl:text>
</xsl:text>
</xsl:template>

<xsl:template match="LITERAL">
<LITERAL sense="{SENSE}" lnote="{LNOTE}">
<xsl:apply-templates/>
</LITERAL>
</xsl:template>

<xsl:template match="SYNONYM">
  <SYNONYM>
  <xsl:apply-templates/>
  <xsl:for-each select="LITERAL/text()">
    <xsl:call-template name="split">
      <xsl:with-param name="words" select="."/>
    </xsl:call-template>
  </xsl:for-each>
  </SYNONYM>
</xsl:template>

<xsl:template match="ILR">
<ILR type="{TYPE}">
<xsl:apply-templates/>
</ILR>
</xsl:template>

<xsl:template match="SENSE|ILR/TYPE|LITERAL/LNOTE"/>

<xsl:template match="*">
<xsl:element name="{name()}">
      <xsl:apply-templates/>
</xsl:element>
</xsl:template>

<xsl:template name="split">
  <xsl:param name="words"/>
  <xsl:variable name="first" select='substring-before($words," ")'/>
  <xsl:variable name='rest' select='substring-after($words," ")'/>
  <xsl:if test='$first'>
    <WORD><xsl:value-of select="$first"/></WORD>
  </xsl:if>

  <xsl:if test='$rest'>
    <xsl:call-template name='split'>
      <xsl:with-param name='words' select='$rest'/>
    </xsl:call-template>
  </xsl:if>
  <xsl:if test='not($rest)'>
    <WORD><xsl:value-of select='$words'/></WORD>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
