<?xml version="1.0"?>
<!--
                 Sun Public License Notice
 
 The contents of this file are subject to the Sun Public License
 Version 1.0 (the "License"). You may not use this file except in
 compliance with the License. A copy of the License is available at
 http://www.sun.com/
 
 The Original Code is NetBeans. The Initial Developer of the Original
 Code is Sun Microsystems, Inc. Portions Copyright 1997-2000 Sun
 Microsystems, Inc. All Rights Reserved.

-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:include href="library.xsl"/>

<xsl:template match="/">
	<xsl:call-template name="html-page">
		<xsl:with-param name="html-title">TestSuite <xsl:value-of select="/UnitTestSuite/@name"/></xsl:with-param>
	</xsl:call-template>
</xsl:template>



<xsl:template match="UnitTestSuite">	
	<H2>Test Suite <xsl:value-of select="@name"/></H2>
	<P>
		<H3>Summary:</H3>
		<TABLE width="95%" cellspacing="2" cellpadding="5" border="0">
			<TR valign="top" bgcolor="#A6CAF0">
				<TD><B>Total tests</B></TD>
				<TD><B>Passed</B></TD>
				<TD><B>Failed</B></TD>
				<TD><B>Error</B></TD>
				<TD><B>Success Rate</B></TD>
				<TD><B>Run(when)</B></TD>
				<TD><B>Time(ms)</B></TD>
			</TR>
			<TR class="pass">			
				<TD><xsl:value-of select="@testsTotal"/></TD>
				<TD><xsl:value-of select="@testsPass"/></TD>
				<TD><xsl:value-of select="@testsFail"/></TD>
				<TD><xsl:value-of select="@testsError"/></TD>				
				<TD><xsl:value-of select="format-number(@testsPass div @testsTotal,'0.00%')"/></TD>
				<TD><xsl:value-of select="@timeStamp"/></TD>
				<TD><xsl:value-of select="@time"/></TD>
			</TR>
		</TABLE>
	</P>
	<HR/>
	<P>
		<H3>Individual Tests:</H3>
		<TABLE width="95%" cellspacing="2" cellpadding="5" border="0">
			<TR valign="top" bgcolor="#A6CAF0">
				<TD width="18%"><B>Name</B></TD>
				<TD width="7%"><B>Status</B></TD>
				<TD width="70%"><B>Message</B></TD>
				<TD width="5%"><B>Workdir</B></TD>
				<TD nowrap="nowrap" width="5%"><B>Time(ms)</B></TD>
			</TR>
				<xsl:apply-templates select="UnitTestCase" mode="table"/>		
		</TABLE>				
	</P>
	<P>
		<BR/>
		<xsl:if test="@testsTotal!=@testsPass">
			<H3>Details for failed test/tests with errors:</H3>
			<BR/>
			<xsl:apply-templates select="UnitTestCase" mode="innerText"/>
		</xsl:if>
	</P>
</xsl:template>

<xsl:template match="UnitTestCase" mode="table">
	<A NAME="@name"/>
	<TR valign="top">
		<xsl:attribute name="class">
		 	<xsl:value-of select="@result"/>
		</xsl:attribute>
		<TD><xsl:value-of select="@name"/></TD>
		<TD>
			<xsl:if test="text()">
		 		<A><xsl:attribute name="href">#<xsl:value-of select="@class"/>.<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@result"/></A>
		 	</xsl:if>
		 	<xsl:if test="not(text())">
				<xsl:value-of select="@result"/>
			</xsl:if>
		</TD>
		<TD><xsl:value-of select="@message"/></TD>
		<TD>
			<xsl:if test="@workdir">
				<A><xsl:attribute name="href">../../userworkdir/<xsl:value-of select="@workdir"/>/</xsl:attribute>Yes</A>
			</xsl:if>
			<xsl:if test="not(@workdir)">
			No
			</xsl:if>
		</TD>
		<TD><xsl:value-of select="@time"/></TD>
	</TR>
</xsl:template>

<xsl:template match="UnitTestCase" mode="innerText">
	<xsl:if test="text()">
	<P>
		<A>
		 	<xsl:attribute name="name">
		 		<xsl:value-of select="@class"/>.<xsl:value-of select="@name"/>
		 	</xsl:attribute>
		 </A>
		 <H5><xsl:value-of select="@name"/>:</H5>
		 <PRE>		 
		<xsl:value-of select="."/>
		</PRE>
	</P>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>