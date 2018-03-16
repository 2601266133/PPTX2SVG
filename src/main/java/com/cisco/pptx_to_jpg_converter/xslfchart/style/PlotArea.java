package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import java.util.List;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ManualLayout;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.Series;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;

/**
 * 
 * reference version 1 Part 4, 5.7.2.146
 * 
 * chart (§5.7.2.27) Child Elements Subclause: area3DChart (3D Area Charts)
 * §5.7.2.4 areaChart (Area Charts) §5.7.2.5 bar3DChart (3D Bar Charts)
 * §5.7.2.15 barChart (Bar Charts) §5.7.2.16 bubbleChart (Bubble Charts)
 * §5.7.2.20 catAx (Category Axis Data) §5.7.2.25 dateAx (Date Axis) §5.7.2.39
 * doughnutChart (Doughnut Charts) §5.7.2.50 dTable (Data Table) §5.7.2.54
 * extLst (Chart Extensibility) §5.7.2.64 layout (Layout) §5.7.2.88 line3DChart
 * (3D Line Charts) §5.7.2.97 lineChart (Line Charts) §5.7.2.98 ofPieChart (Pie
 * of Pie or Bar of Pie Charts) §5.7.2.127 pie3DChart (3D Pie Charts) §5.7.2.141
 * pieChart (Pie Charts) §5.7.2.142 radarChart (Radar Charts) §5.7.2.154
 * scatterChart (Scatter Charts) §5.7.2.162 serAx (Series Axis) §5.7.2.176 spPr
 * (Shape Properties) §5.7.2.198 stockChart (Stock Charts) §5.7.2.199
 * surface3DChart (3D Surface Charts) §5.7.2.204 surfaceChart (Surface Charts)
 * §5.7.2.205 valAx (Value Axis) §5.7.2.227
 * 
 *
 */

public class PlotArea {

	ManualLayout manualLayout;

	String barDir; // Bar Direction a bar (horizontal) or a column (vertical)
	String grouping;
	boolean varyColors;
	List<Series> serList;
	DataLabel dLbls;

	int gapWidth;
	int overlap;// 列的百分数

	List<CategoryAxis> catAxList;
	List<ValueAxis> valAxList;
	ShapeProperties spPr;

	public List<ValueAxis> getValAxList() {
		return valAxList;
	}

	public void setValAxList(List<ValueAxis> valAxList) {
		this.valAxList = valAxList;
	}

	public ManualLayout getManualLayout() {
		return manualLayout;
	}

	public void setManualLayout(ManualLayout manualLayout) {
		this.manualLayout = manualLayout;
	}

	public String getBarDir() {
		return barDir;
	}

	public void setBarDir(String barDir) {
		this.barDir = barDir;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public boolean getVaryColors() {
		return varyColors;
	}

	public void setVaryColors(boolean varyColors) {
		this.varyColors = varyColors;
	}

	public List<Series> getSerList() {
		return serList;
	}

	public void setSerList(List<Series> serList) {
		this.serList = serList;
	}

	public DataLabel getdLbls() {
		return dLbls;
	}

	public void setdLbls(DataLabel dLbls) {
		this.dLbls = dLbls;
	}

	public int getGapWidth() {
		return gapWidth;
	}

	public void setGapWidth(int gapWidth) {
		this.gapWidth = gapWidth;
	}

	public int getOverlap() {
		return overlap;
	}

	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}

	public List<CategoryAxis> getCatAxList() {
		return catAxList;
	}

	public void setCatAxList(List<CategoryAxis> catAxList) {
		this.catAxList = catAxList;
	}

	public ShapeProperties getSpPr() {
		return spPr;
	}

	public void setSpPr(ShapeProperties spPr) {
		this.spPr = spPr;
	}

}
