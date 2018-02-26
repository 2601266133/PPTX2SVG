package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

public class ManualLayout {

	boolean manualLayout;

	String xMode;
	String yMode;
	double x;
	double y;
	double w;
	double h;

	public boolean isManualLayout() {
		return manualLayout;
	}

	public void setManualLayout(boolean manualLayout) {
		this.manualLayout = manualLayout;
	}

	public String getxMode() {
		return xMode;
	}

	public void setxMode(String xMode) {
		this.xMode = xMode;
	}

	public String getyMode() {
		return yMode;
	}

	public void setyMode(String yMode) {
		this.yMode = yMode;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

}
