package com.cisco.pptx_to_jpg_converter.xslfchart;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.BackWall;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.SideWall;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ChartArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Floor;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

public class Column3DStackedChart {

	XFrame xfrm;
	ChartArea chartArea;
	Title title;
	PlotArea plotArea;
	Legend legend;
	boolean autoTitleDeleted;
	boolean plotVisOnly;
	String dispBlanksAs;
	boolean showDLblsOverMax;

	Floor floor;
	SideWall sideWall;
	BackWall backWall;

}
