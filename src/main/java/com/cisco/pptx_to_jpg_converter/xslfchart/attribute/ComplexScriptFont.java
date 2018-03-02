package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.1.5.3.1 cs (Complex Script Font)
 * 
 * <attribute name="typeface" type="ST_TextTypeface"/> 4
 * <attribute name="panose" type="ST_Panose" use="optional"/> 5
 * <attribute name="pitchFamily" type="xsd:byte" use="optional" default="0"/> 6
 * <attribute name="charset" type="xsd:byte" use="optional" default="1"/>
 *
 */

public class ComplexScriptFont {

	String typeface;
	String panose;
	String pitchFamily;
	String charset;

	public String getTypeface() {
		return typeface;
	}

	public void setTypeface(String typeface) {
		this.typeface = typeface;
	}

	public String getPanose() {
		return panose;
	}

	public void setPanose(String panose) {
		this.panose = panose;
	}

	public String getPitchFamily() {
		return pitchFamily;
	}

	public void setPitchFamily(String pitchFamily) {
		this.pitchFamily = pitchFamily;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
