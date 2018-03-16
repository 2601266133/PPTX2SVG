package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.1.5.2.7 pPr (Text Paragraph Properties)
 *
 * Child Elements Subclause : buAutoNum (Auto-Numbered Bullet) §5.1.5.4.1 buBlip
 * (Picture Bullet) §5.1.5.4.2 buChar (Character Bullet) §5.1.5.4.3 buClr (Color
 * Specified) §5.1.5.4.4 buClrTx (Follow Text) §5.1.5.4.5 buFont (Specified)
 * §5.1.5.4.6 buFontTx (Follow text) §5.1.5.4.7 buNone (No Bullet) §5.1.5.4.8
 * buSzPct (Bullet Size Percentage) §5.1.5.4.9 buSzPts (Bullet Size Points)
 * §5.1.5.4.10 buSzTx (Bullet Size Follows Text) §5.1.5.4.11 defRPr (Default
 * Text Run Properties) §5.1.5.3.2 extLst (Extension List) §5.1.2.1.15 lnSpc
 * (Line Spacing) §5.1.5.2.5 spcAft (Space After) §5.1.5.2.8 spcBef (Space
 * Before) §5.1.5.2.9 tabLst (Tab List) §5.1.5.2.13
 *
 *
 */

public class TextParagraphProperties {

	DefaultTextRunProperties defRPr;
	LineSpace lnSpc;

	public LineSpace getLnSpc() {
		return lnSpc;
	}

	public void setLnSpc(LineSpace lnSpc) {
		this.lnSpc = lnSpc;
	}

	public DefaultTextRunProperties getDefRPr() {
		return defRPr;
	}

	public void setDefRPr(DefaultTextRunProperties defRPr) {
		this.defRPr = defRPr;
	}

}
