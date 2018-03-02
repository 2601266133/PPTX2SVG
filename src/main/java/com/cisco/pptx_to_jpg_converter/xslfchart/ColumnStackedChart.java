package com.cisco.pptx_to_jpg_converter.xslfchart;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ChartArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

public class ColumnStackedChart {

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
