package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ManualLayout;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;

public class Title {

	ManualLayout manualLayout;
	boolean overlay;
	ShapeProperties spPr;
	TextProperties txPr;

	public ManualLayout getManualLayout() {
		return manualLayout;
	}

	public void setManualLayout(ManualLayout manualLayout) {
		this.manualLayout = manualLayout;
	}

	public boolean isOverlay() {
		return overlay;
	}

	public void setOverlay(boolean overlay) {
		this.overlay = overlay;
	}

	public ShapeProperties getSpPr() {
		return spPr;
	}

	public void setSpPr(ShapeProperties spPr) {
		this.spPr = spPr;
	}

	public TextProperties getTxPr() {
		return txPr;
	}

	public void setTxPr(TextProperties txPr) {
		this.txPr = txPr;
	}

}
