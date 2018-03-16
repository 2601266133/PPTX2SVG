package com.cisco.pptx_to_jpg_converter.xslfchart;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ChartArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

public class Column100PrecntStackedChart {

	XFrame xfrm;
	ChartArea chartArea;
	Title title;

	/**
	 * percentStacked default: <c:x val="5.3567081288751948E-2"/>
	 * <c:y val="0.1285225372057974"/> <c:w val="0.9331478945566587"/>
	 * <c:h val="0.71045388797652587"/>
	 * 
	 * *default x: 0.05356708128875195 y: 0.1285225372057974 w: 0.9331478945566587
	 * h: 0.71045388797652587
	 * 
	 */
	PlotArea plotArea;

	Legend legend;
	boolean autoTitleDeleted;
	boolean plotVisOnly;
	String dispBlanksAs;
	boolean showDLblsOverMax;
}
