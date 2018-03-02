package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.7.2.157 rich (Rich Text)
 * 
 * bodyPr (Body Properties) §5.1.5.1.1 lstStyle (Text List Styles) §5.1.5.4.12 p
 * (Text Paragraphs) §5.1.5.2.6
 *
 */

public class RichText {

	BodyProperties bodyPr;

	TextParagraphs p;

	public TextParagraphs getP() {
		return p;
	}

	public void setP(TextParagraphs p) {
		this.p = p;
	}

	public BodyProperties getBodyPr() {
		return bodyPr;
	}

	public void setBodyPr(BodyProperties bodyPr) {
		this.bodyPr = bodyPr;
	}

}
