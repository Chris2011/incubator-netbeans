<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:include href="library.xsl"/>

<xsl:template match="/">
	<xsl:call-template name="html-page">
		<xsl:with-param name="html-title">Summary</xsl:with-param>
	</xsl:call-template>
</xsl:template>


<xsl:template match="/XTestResultsReport/SystemInfo">
	<H2>System Info</H2>
	<P>
	<TABLE>
		<TR><TD>Host</TD><TD>:</TD><TD><xsl:value-of select="@host"/></TD></TR>
		<TR><TD>Operating System Name</TD><TD>:</TD><TD><xsl:value-of select="@osName"/></TD></TR>
		<TR><TD>Operating System Version</TD><TD>:</TD><TD><xsl:value-of select="@osVersion"/></TD></TR>
		<TR><TD>System Architecture</TD><TD>:</TD><TD><xsl:value-of select="@osArch"/></TD></TR>
		<TR><TD>Java Version</TD><TD>:</TD><TD><xsl:value-of select="@javaVersion"/></TD></TR>
		<TR><TD>Java Vendor</TD><TD>:</TD><TD><xsl:value-of select="@javaVendor"/></TD></TR>
		<TR><TD>User Language</TD><TD>:</TD><TD><xsl:value-of select="@userLanguage"/></TD></TR>
	</TABLE>
	</P>
	<!-- this needs to be redone !!!! -->
	<xsl:if test="false()">
		<P>
		<H3>Additional Information</H3>
		<TABLE>
			<xsl:apply-templates select="SystemInfoExtra"/>
		</TABLE>
		</P>
	</xsl:if>
</xsl:template>


<xsl:template match="SystemInfoExtra">
<TR><TD><xsl:value-of select="@name"/></TD><TD>:</TD><TD><xsl:value-of select="@value"/></TD></TR>		
</xsl:template>

</xsl:stylesheet>