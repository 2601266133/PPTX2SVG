package com.cisco.pptx_to_jpg_converter.xslfchart.FillType;

import java.util.List;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.GradientStop;

/**
 * gsLst
 * 
 * 5.1.10.33 gradFill (Gradient Fill)
 * 
 * 
 * gsLst (Gradient Stop List) §5.1.10.37 lin (Linear Gradient Fill) §5.1.10.41
 * path (Path Gradient) §5.1.10.46 tileRect (Tile Rectangle) §5.1.10.59
 * 
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
