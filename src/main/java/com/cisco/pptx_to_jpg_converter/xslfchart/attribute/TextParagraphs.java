package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

import java.util.List;

/**
 * 
 * 5.1.5.2.6 p (Text Paragraphs)
 * 
 * child elements: br (Text Line Break) §5.1.5.2.1 endParaRPr (End Paragraph Run
 * Properties) §5.1.5.2.3 fld (Text Field) §5.1.5.2.4 pPr (Text Paragraph
 * Properties) §5.1.5.2.7 r (Text Run) §5.1.5.3.8
 *
 */

public class TextParagraphs {

	TextParagraphProperties pPr;
	List<TextRun> r;

	public List<TextRun> getR() {
		return r;
	}

	public void setR(List<TextRun> r) {
		this.r = r;
	}

	public TextParagraphProperties getpPr() {
		return pPr;
	}

	public void setpPr(TextParagraphProperties pPr) {
		this.pPr = pPr;
	}

}
