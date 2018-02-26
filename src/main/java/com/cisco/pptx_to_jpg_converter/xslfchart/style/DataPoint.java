package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;

/**
 * 
 * bubble3D (3D Bubble) §5.7.2.19 explosion (Explosion) §5.7.2.61 extLst (Chart
 * Extensibility) §5.7.2.64 idx (Index) §5.7.2.84 invertIfNegative (Invert if
 * Negative) §5.7.2.86 marker (Marker) §5.7.2.106 pictureOptions (Picture
 * Options) §5.7.2.139 spPr (Shape Properties) §5.7.2.198
 * 
 * 
 * 
 * <complexType name="CT_DPt"> <sequence>
 * <element name="idx" type="CT_UnsignedInt" minOccurs="1" maxOccurs="1"/>
 * <element name="invertIfNegative" type="CT_Boolean" minOccurs="0" maxOccurs=
 * "1"/> <element name="marker" type="CT_Marker" minOccurs="0" maxOccurs="1"/>
 * <element name="bubble3D" type="CT_Boolean" minOccurs="0" maxOccurs="1"/>
 * <element name="explosion" type="CT_UnsignedInt" minOccurs="0" maxOccurs="1"/>
 * <element name="spPr" type="a:CT_ShapeProperties" minOccurs="0" maxOccurs=
 * "1"/> <element name="pictureOptions" type="CT_PictureOptions" minOccurs="0"
 * maxOccurs="1"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 *
 */

public class DataPoint {

	long id;
	boolean invertIfNegative;
	boolean bubble3D;
	ShapeProperties spPr;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getInvertIfNegative() {
		return invertIfNegative;
	}

	public void setInvertIfNegative(boolean invertIfNegative) {
		this.invertIfNegative = invertIfNegative;
	}

	public boolean getBubble3D() {
		return bubble3D;
	}

	public void setBubble3D(boolean bubble3d) {
		bubble3D = bubble3d;
	}

	public ShapeProperties getSpPr() {
		return spPr;
	}

	public void setSpPr(ShapeProperties spPr) {
		this.spPr = spPr;
	}

}
