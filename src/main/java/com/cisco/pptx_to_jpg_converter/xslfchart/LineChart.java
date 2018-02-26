package com.cisco.pptx_to_jpg_converter.xslfchart;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ChartArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

/**
 * reference version 1 Part 4, 5.7.2.98
 * 
 * axId (Axis ID) §5.7.2.9 dLbls (Data Labels) §5.7.2.49 dropLines (Drop Lines)
 * §5.7.2.53 extLst (Chart Extensibility) §5.7.2.64 grouping (Grouping)
 * §5.7.2.76 hiLowLines (High Low Lines) §5.7.2.80 marker (Show Marker)
 * §5.7.2.107 ser (Line Chart Series) §5.7.2.169 smooth (Smoothing) §5.7.2.195
 * upDownBars (Up/Down Bars) §5.7.2.219 varyColors (Vary Colors by Point)
 * §5.7.2.228
 *
 *
 *
 *
 * <complexType name="CT_LineChart"> <sequence>
 * <group ref="EG_LineChartShared" minOccurs="1" maxOccurs="1"/>
 * <element name="hiLowLines" type="CT_ChartLines" minOccurs="0" maxOccurs="1"/>
 * <element name="upDownBars" type="CT_UpDownBars" minOccurs="0" maxOccurs="1"/>
 * <element name="marker" type="CT_Boolean" minOccurs="0" maxOccurs="1"/>
 * <element name="smooth" type="CT_Boolean" minOccurs="0" maxOccurs="1"/>
 * <element name="axId" type="CT_UnsignedInt" minOccurs="2" maxOccurs="2"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 *
 */

public class LineChart {

	XFrame xfrm;
	ChartArea chartArea;
	Title title;
	PlotArea plotArea;
	Legend legend;
	boolean autoTitleDeleted;
	boolean plotVisOnly;
	String dispBlanksAs;
	boolean showDLblsOverMax;

	boolean showMarker;

}
