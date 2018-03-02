package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.1.5.3.9 rPr (Text Run Properties)
 *
 *
 * Child Elements Subclause --------------------------------
 * --------------------------blipFill (Picture Fill) §5.1.10.14
 * --------------------------cs (Complex Script Font) §5.1.5.3.1
 * --------------------------ea (East Asian Font) §5.1.5.3.3
 * -------------------------- effectDag (Effect Container) §5.1.10.25
 * --------------------------effectLst (Effect Container) §5.1.10.26
 * -------------------------- extLst (Extension List) §5.1.2.1.15
 * --------------------------gradFill (Gradient Fill) §5.1.10.33
 * --------------------------grpFill (Group Fill) §5.1.10.35
 * --------------------------highlight (Highlight Color) §5.1.5.3.4
 * --------------------------hlinkClick (Click Hyperlink) §5.1.5.3.5
 * --------------------------hlinkMouseOver (Mouse-Over Hyperlink) §5.1.5.3.6
 * --------------------------latin (Latin Font) §5.1.5.3.7
 * --------------------------ln (Outline) §5.1.2.1.24
 * --------------------------noFill (No Fill) §5.1.10.44
 * --------------------------pattFill (Pattern Fill) §5.1.10.47
 * --------------------------solidFill (Solid Fill) §5.1.10.54
 * -------------------------- sym (Symbol Font) §5.1.5.3.10
 * --------------------------uFill (Underline Fill) §5.1.5.3.12
 * -------------uFillTx (Underline Fill Properties Follow
 * --------------------------uLn (Underline Stroke) §5.1.5.3.14
 * --------------------------uLnTx (Underline Follows Text) §5.1.5.3.15
 * 
 * 
 * <attribute name="kumimoji" type="xsd:boolean" use="optional"/>
 * <attribute name="lang" type="ST_TextLanguageID" use="optional"/>
 * <attribute name="altLang" type="ST_TextLanguageID" use="optional"/>
 * <attribute name="sz" type="ST_TextFontSize" use="optional"/>
 * <attribute name="b" type="xsd:boolean" use="optional"/>
 * <attribute name="i" type="xsd:boolean" use="optional"/>
 * <attribute name="u" type="ST_TextUnderlineType" use="optional"/>
 * <attribute name="strike" type="ST_TextStrikeType" use="optional"/>
 * <attribute name="kern" type="ST_TextNonNegativePoint" use="optional"/>
 * <attribute name="cap" type="ST_TextCapsType" use="optional"/>
 * <attribute name="spc" type="ST_TextPoint" use="optional"/>
 * <attribute name="normalizeH" type="xsd:boolean" use="optional"/>
 * <attribute name="baseline" type="ST_Percentage" use="optional"/>
 * <attribute name="noProof" type="xsd:boolean" use="optional"/>
 * <attribute name="dirty" type="xsd:boolean" use="optional" default="true"/>
 * <attribute name="err" type="xsd:boolean" use="optional" default="false"/>
 * <attribute name="smtClean" type="xsd:boolean" use="optional" default="true"/>
 * <attribute name="smtId" type="xsd:unsignedInt" use="optional" default="0"/>
 * <attribute name="bmk" type="xsd:string" use="optional"/>
 */

public class TextRunProperties {

	ComplexScriptFont cs;
	EastAsianFont ea;
	LatinFont latin;

	int baseline;
	String spc;
	String kern;
	String strike;
	String u;
	String i;
	boolean b;
	int sz;
	String endParaRPr;

	String fillType;

	public ComplexScriptFont getCs() {
		return cs;
	}

	public void setCs(ComplexScriptFont cs) {
		this.cs = cs;
	}

	public EastAsianFont getEa() {
		return ea;
	}

	public void setEa(EastAsianFont ea) {
		this.ea = ea;
	}

	public LatinFont getLatin() {
		return latin;
	}

	public void setLatin(LatinFont latin) {
		this.latin = latin;
	}

	public int getBaseline() {
		return baseline;
	}

	public void setBaseline(int baseline) {
		this.baseline = baseline;
	}

	public String getSpc() {
		return spc;
	}

	public void setSpc(String spc) {
		this.spc = spc;
	}

	public String getKern() {
		return kern;
	}

	public void setKern(String kern) {
		this.kern = kern;
	}

	public String getStrike() {
		return strike;
	}

	public void setStrike(String strike) {
		this.strike = strike;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public boolean getB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public int getSz() {
		return sz;
	}

	public void setSz(int sz) {
		this.sz = sz;
	}

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getEndParaRPr() {
		return endParaRPr;
	}

	public void setEndParaRPr(String endParaRPr) {
		this.endParaRPr = endParaRPr;
	}

}
