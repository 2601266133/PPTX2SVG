package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

import com.cisco.pptx_to_jpg_converter.xslfchart.FillType.SolidFill;

/**
 * reference version 1 Part4, 5.7.2.198
 *
 * blipFill (Picture Fill) §5.1.10.14 custGeom (Custom Geometry) §5.1.11.8
 * effectDag (Effect Container) §5.1.10.25 effectLst (Effect Container)
 * §5.1.10.26 extLst (Extension List) §5.1.2.1.15 gradFill (Gradient Fill)
 * §5.1.10.33 grpFill (Group Fill) §5.1.10.35 ln (Outline) §5.1.2.1.24 noFill
 * (No Fill) §5.1.10.44 pattFill (Pattern Fill) §5.1.10.47 prstGeom (Preset
 * geometry) §5.1.11.18 scene3d (3D Scene Properties) §5.1.4.1.26 solidFill
 * (Solid Fill) §5.1.10.54 sp3d (Apply 3D shape properties) §5.1.7.12 xfrm (2D
 * Transform for Individual Objects) §5.1.9.6
 */

public class ShapeProperties {

	// custom
	String fillType;// fill type define : solid , gradient etc.. under FillType package

	String effectLst;
	OutLine ln;

	SolidFill solidFill;

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getEffectLst() {
		return effectLst;
	}

	public void setEffectLst(String effectLst) {
		this.effectLst = effectLst;
	}

	public OutLine getLn() {
		return ln;
	}

	public void setLn(OutLine ln) {
		this.ln = ln;
	}

	public SolidFill getSolidFill() {
		return solidFill;
	}

	public void setSolidFill(SolidFill solidFill) {
		this.solidFill = solidFill;
	}

}
