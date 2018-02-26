package com.cisco.pptx_to_jpg_converter.xslfchart.FillType;

import java.util.List;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.GradientStop;

/**
 * gsLst
 **/

public class GradientFill {

	String flip;// Tile Flip
	// Part 4, 5.1.12.86
	// none (None)
	// x (Horizontal)
	// xy (Horizontal and Vertical)
	// y (Vertical)

	boolean rotWithShape; // Rotate With Shape

	List<GradientStop> gsLst;

	PathGradient path;

	LinearGradientFill lin;

}
