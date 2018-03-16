package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

public class TextProperties {

	BodyProperties bodyPr;
	TextParagraphs p;

	public String getTextFont() {
		return p.getpPr().getDefRPr().getEa() != null ? p.getpPr().getDefRPr().getEa().getTypeface() : "等线";
	}

	public int getTextSize() {
		return p.getpPr().getDefRPr().getSz();
	}

	public BodyProperties getBodyPr() {
		return bodyPr;
	}

	public void setBodyPr(BodyProperties bodyPr) {
		this.bodyPr = bodyPr;
	}

	public TextParagraphs getP() {
		return p;
	}

	public void setP(TextParagraphs p) {
		this.p = p;
	}

}
