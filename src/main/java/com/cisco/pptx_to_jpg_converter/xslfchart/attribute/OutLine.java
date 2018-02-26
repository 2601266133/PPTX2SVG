package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

import com.cisco.pptx_to_jpg_converter.xslfchart.FillType.SolidFill;

/**
 * reference version 1 Part4, 5.1.2.1.24
 * 
 * child elements:
 * 
 * bevel (Line Join Bevel) §5.1.10.9 custDash (Custom Dash) §5.1.10.21 extLst
 * (Extension List) §5.1.2.1.15 gradFill (Gradient Fill) §5.1.10.33 headEnd
 * (Line Head/End Style) §5.1.10.38 miter (Miter Line Join) §5.1.10.43 noFill
 * (No Fill) §5.1.10.44 pattFill (Pattern Fill) §5.1.10.47 prstDash (Preset
 * Dash) §5.1.10.48 round (Round Line Join) §5.1.10.52 solidFill (Solid Fill)
 * §5.1.10.54 tailEnd (Tail line end style) §5.1.10.57
 */

public class OutLine {

	// custom
	String fillType; // fill type

	// attributes
	String algn; // Stroke Alignment
	String cmpd; // Compound Line Type
	String cap; // Line Ending Cap Type
	String w; // Line width

	// child elements
	String round;
	SolidFill solidFill;

}
