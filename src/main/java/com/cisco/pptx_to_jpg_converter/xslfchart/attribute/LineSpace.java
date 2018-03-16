package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.1.5.2.5 lnSpc (Line Spacing)
 * 
 * This element specifies the vertical line spacing that is to be used within a
 * paragraph. This may be specified in two different ways, percentage spacing
 * and font point spacing. If this element is omitted then the spacing between
 * two lines of text should be determined by the point size of the largest piece
 * of text within a line.
 * 
 * This paragraph will have two lines of text that will have percentage based
 * vertical spacing. This kind of spacing should change based on the size of the
 * text involved as its size is calculated as a percentage of this. end
 *
 */

public class LineSpace {

	SpacingPoints spcPts;
	SpacingPercent spcPct;

	public SpacingPoints getSpcPts() {
		return spcPts;
	}

	public void setSpcPts(SpacingPoints spcPts) {
		this.spcPts = spcPts;
	}

	public SpacingPercent getSpcPct() {
		return spcPct;
	}

	public void setSpcPct(SpacingPercent spcPct) {
		this.spcPct = spcPct;
	}

}
