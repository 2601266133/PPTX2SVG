package com.cisco.pptx_to_jpg_converter.model;

public class ChartObj {
	String idx;
	String order;
	String series;
	String point;
	String value;
	String color;
	boolean showVal;
	boolean showLegendKey;
	boolean showCatName;
	boolean showSerName;
	boolean showPercent;
	boolean showBubbleSize;
	boolean showLeaderLines;

	public boolean isShowLegendKey() {
		return showLegendKey;
	}

	public void setShowLegendKey(boolean showLegendKey) {
		this.showLegendKey = showLegendKey;
	}

	public boolean isShowCatName() {
		return showCatName;
	}

	public void setShowCatName(boolean showCatName) {
		this.showCatName = showCatName;
	}

	public boolean isShowSerName() {
		return showSerName;
	}

	public void setShowSerName(boolean showSerName) {
		this.showSerName = showSerName;
	}

	public boolean isShowPercent() {
		return showPercent;
	}

	public void setShowPercent(boolean showPercent) {
		this.showPercent = showPercent;
	}

	public boolean isShowBubbleSize() {
		return showBubbleSize;
	}

	public void setShowBubbleSize(boolean showBubbleSize) {
		this.showBubbleSize = showBubbleSize;
	}

	public boolean isShowLeaderLines() {
		return showLeaderLines;
	}

	public void setShowLeaderLines(boolean showLeaderLines) {
		this.showLeaderLines = showLeaderLines;
	}

	public boolean isShowVal() {
		return showVal;
	}

	public void setShowVal(boolean showVal) {
		this.showVal = showVal;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
