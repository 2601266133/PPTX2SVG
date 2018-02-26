package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * extLst (Chart Extensibility) §5.7.2.64 size (Size) §5.7.2.193 spPr (Shape
 * Properties) §5.7.2.198 symbol (Symbol) §5.7.2.206
 *
 * <complexType name="CT_Marker"> <sequence>
 * <element name="symbol" type="CT_MarkerStyle" minOccurs="0" maxOccurs="1"/>
 * <element name="size" type="CT_MarkerSize" minOccurs="0" maxOccurs="1"/>
 * <element name="spPr" type="a:CT_ShapeProperties" minOccurs="0" maxOccurs=
 * "1"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 *
 */

public class Marker {

	String size;
	ShapeProperties spPr;
	String symbol;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public ShapeProperties getSpPr() {
		return spPr;
	}

	public void setSpPr(ShapeProperties spPr) {
		this.spPr = spPr;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
