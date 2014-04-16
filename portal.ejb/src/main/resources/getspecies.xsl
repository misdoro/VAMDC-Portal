<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:xsams="http://vamdc.org/xml/xsams/1.0">

	<xsl:output method="html" indent="no" />
	<xsl:strip-space elements="*" />

	<xsl:decimal-format name="fixnan" NaN="" />
	<xsl:variable name="has_atom"
		select="/xsams:XSAMSData/xsams:Species/xsams:Atoms/xsams:Atom" />
	<xsl:variable name="has_molecule"
		select="/xsams:XSAMSData/xsams:Species/xsams:Molecules/xsams:Molecule" />

	<xsl:template match="/xsams:XSAMSData">
		<xsl:if test="$has_atom = false() and $has_molecule = false()">
			<p>Species information is not available</p>
		</xsl:if>
		<xsl:if test="$has_atom">			
			<xsl:if test="$has_molecule">
				<div>
					<a href="#molecules">Go to molecules</a>
				</div>				
			</xsl:if>			
			<div>
				<table border="1" cellpadding="0" cellspacing="0" width="75%">
					<thead>
						<tr>
							<th colspan="5"><a id="atoms">Atoms</a></th>
						</tr>
						<tr>
							<th>Element symbol</th>
							<th>Nuclear charge</th>
							<th>Ion charge</th>
							<th>InChI</th>
							<th>InChIKey</th>
						</tr>
					</thead>
					<tbody>
						<xsl:for-each
							select="./xsams:Species/xsams:Atoms/xsams:Atom/xsams:Isotope/xsams:Ion">
							<xsl:sort select="../../xsams:ChemicalElement/xsams:ElementSymbol" />
							<xsl:call-template name="ion">
								<xsl:with-param name="ion" select="." />
							</xsl:call-template>
						</xsl:for-each>
					</tbody>
				</table>
			</div>
		</xsl:if>
		<p/><p/><p/>
		<xsl:if test="$has_molecule">
			<xsl:if test="$has_atom">
				<div>
					<a href="#atoms">Go to atoms</a>
				</div>				
			</xsl:if>	
			<div>				
				<table border="1" cellpadding="0" cellspacing="0" width="75%">
					<thead>
						<tr>
							<th colspan="7"><a id="molecules">Molecules</a></th>
						</tr>
						<tr>
							<th>Stoichiometric formula</th>
							<th>Ordinary structural formula</th>
							<th>Chemical name</th>
							<th>Ion charge</th>
							<th>Molecular weight</th>
							<th>InChI</th>
							<th>InChIKey</th>
						</tr>
					</thead>
					<tbody>
						<xsl:for-each
							select="./xsams:Species/xsams:Molecules/xsams:Molecule/xsams:MolecularChemicalSpecies">
							<xsl:sort select="xsams:StoichiometricFormula" />
							<xsl:call-template name="molecule">
								<xsl:with-param name="molecule" select="." />
							</xsl:call-template>
						</xsl:for-each>
					</tbody>
				</table>
			</div>
		</xsl:if>
	</xsl:template>


	<xsl:template name="ion">
		<xsl:param name="ion" />
		<tr>
			<td>
				<xsl:value-of select="$ion/../../xsams:ChemicalElement/xsams:ElementSymbol" />
			</td>
			<td>
				<xsl:value-of select="$ion/../../xsams:ChemicalElement/xsams:NuclearCharge" />
			</td>
			<td>
				<xsl:value-of select="$ion/xsams:IonCharge" />
			</td>
			<td>
				<xsl:value-of select="$ion//xsams:InChI" />
			</td>
			<td>
				<xsl:value-of select="$ion/xsams:InChIKey" />
				<xsl:if test="$ion/xsams:InChIKey != '' "> 
					( <a href="http://webbook.nist.gov/cgi/cbook.cgi?InChI={$ion/xsams:InChIKey}&amp;Units=SI" target="_blank">Search in NIST database</a> )
				</xsl:if>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="molecule">
		<xsl:param name="molecule" />
		<tr>
			<td>
				<xsl:value-of select="$molecule/xsams:StoichiometricFormula" />
			</td>
			<td>
				<xsl:value-of select="$molecule/xsams:OrdinaryStructuralFormula" />
			</td>
			<td>
				<xsl:value-of select="$molecule/xsams:ChemicalName/xsams:Value" />
			</td>
			<td>
				<xsl:value-of select="$molecule/xsams:IonCharge" />
			</td>
			<td>
				<xsl:value-of
					select="$molecule/xsams:StableMolecularProperties/xsams:MolecularWeight/xsams:Value" />
			</td>
			<td>
				<xsl:value-of select="$molecule/xsams:InChI" />
			</td>
			<td>
				<xsl:value-of select="$molecule/xsams:InChIKey" /> 
				<xsl:if test="$molecule/xsams:InChIKey != ''">
					( <a href="http://webbook.nist.gov/cgi/cbook.cgi?InChI={$molecule/xsams:InChIKey}&amp;Units=SI" target="_blank">Search in NIST database</a> )
				</xsl:if>
			</td>
		</tr>
	</xsl:template>


</xsl:stylesheet>
