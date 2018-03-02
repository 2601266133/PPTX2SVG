package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 5.1.5.3.8 r (Text Run)
 * 
 * rPr (Text Run Properties) §5.1.5.3.9 t (Text String) §5.1.5.3.11
 * 
 * <complexType name="CT_RegularTextRun"> <sequence>
 * <element name="rPr" type="CT_TextCharacterProperties" minOccurs="0" maxOccurs
 * ="1"/> <element name="t" type="xsd:string" minOccurs="1" maxOccurs="1"/>
 * </sequence> 9 </complexType>
 *
 */

public class TextRun {
	TextRunProperties rPr;

	String t;

	public TextRunProperties getrPr() {
		return rPr;
	}

	public void setrPr(TextRunProperties rPr) {
		this.rPr = rPr;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

}
