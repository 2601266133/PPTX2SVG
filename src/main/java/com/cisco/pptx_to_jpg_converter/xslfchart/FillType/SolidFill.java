package com.cisco.pptx_to_jpg_converter.xslfchart.FillType;

import com.cisco.pptx_to_jpg_converter.xslfchart.color.RGBHexVariantColor;
import com.cisco.pptx_to_jpg_converter.xslfchart.color.SchemeColor;

public class SolidFill {

	SchemeColor schemeClr;

	RGBHexVariantColor srgbClr;

	public SchemeColor getSchemeClr() {
		return schemeClr;
	}

	public void setSchemeClr(SchemeColor schemeClr) {
		this.schemeClr = schemeClr;
	}

	public RGBHexVariantColor getSrgbClr() {
		return srgbClr;
	}

	public void setSrgbClr(RGBHexVariantColor srgbClr) {
		this.srgbClr = srgbClr;
	}

}
