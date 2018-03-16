package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * <complexType name="CT_SerTx"> <sequence> <choice minOccurs="1" maxOccurs="1">
 * <element name="strRef" type="CT_StrRef" minOccurs="1" maxOccurs="1"/>
 * <element name="v" type="ST_Xstring" minOccurs="1" maxOccurs="1"/> </choice>
 * </sequence> </complexType>
 **/
public class SeriesText {

	String f;

	// strCache
	String ptCount;
	String id;
	String v;

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getPtCount() {
		return ptCount;
	}

	public void setPtCount(String ptCount) {
		this.ptCount = ptCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

}
