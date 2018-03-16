package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.1.5.2.10 spcPct (Spacing Percent)
 * 
 * This element specifies the amount of white space that is to be used between
 * lines and paragraphs in the form of a percentage of the text size. The text
 * size that is used to calculate the spacing here is the text for each run,
 * with the largest text size having precedence. That is if there is a run of
 * text with 10 point font and within the same paragraph on the same line there
 * is a run of text with a 12 point font size then the 12 point should be used
 * to calculate the spacing to be used
 * 
 * <a:spcPct val="200000"/> This spacing will be 200% of the size of the largest
 * text on each line
 *
 */

public class SpacingPercent {

	int val;

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

}
