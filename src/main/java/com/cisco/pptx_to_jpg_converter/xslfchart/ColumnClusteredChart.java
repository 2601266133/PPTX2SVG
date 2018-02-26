package com.cisco.pptx_to_jpg_converter.xslfchart;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ChartArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

/**
 * reference version 1 Part4, 5.7.2.16
 * 
 * <complexType name="CT_BarChart"> <sequence>
 * <group ref="EG_BarChartShared" minOccurs="1" maxOccurs="1"/>
 * <element name="gapWidth" type="CT_GapAmount" minOccurs="0" maxOccurs="1"/>
 * <element name="overlap" type="CT_Overlap" minOccurs="0" maxOccurs="1"/>
 * <element name="serLines" type="CT_ChartLines" minOccurs="0" maxOccurs=
 * "unbounded"/>
 * <element name="axId" type="CT_UnsignedInt" minOccurs="2" maxOccurs="2"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 */

public class ColumnClusteredChart {

	XFrame xfrm;
	ChartArea chartArea;
	Title title;
	PlotArea plotArea;
	Legend legend;
	boolean autoTitleDeleted;
	boolean plotVisOnly;
	String dispBlanksAs;
	boolean showDLblsOverMax;

	public XFrame getXfrm() {
		return xfrm;
	}

	public void setXfrm(XFrame xfrm) {
		this.xfrm = xfrm;
	}

	public ChartArea getChartArea() {
		return chartArea;
	}

	public void setChartArea(ChartArea chartArea) {
		this.chartArea = chartArea;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public PlotArea getPlotArea() {
		return plotArea;
	}

	public void setPlotArea(PlotArea plotArea) {
		this.plotArea = plotArea;
	}

	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	public boolean isAutoTitleDeleted() {
		return autoTitleDeleted;
	}

	public void setAutoTitleDeleted(boolean autoTitleDeleted) {
		this.autoTitleDeleted = autoTitleDeleted;
	}

	public boolean isPlotVisOnly() {
		return plotVisOnly;
	}

	public void setPlotVisOnly(boolean plotVisOnly) {
		this.plotVisOnly = plotVisOnly;
	}

	public String getDispBlanksAs() {
		return dispBlanksAs;
	}

	public void setDispBlanksAs(String dispBlanksAs) {
		this.dispBlanksAs = dispBlanksAs;
	}

	public boolean isShowDLblsOverMax() {
		return showDLblsOverMax;
	}

	public void setShowDLblsOverMax(boolean showDLblsOverMax) {
		this.showDLblsOverMax = showDLblsOverMax;
	}

}
