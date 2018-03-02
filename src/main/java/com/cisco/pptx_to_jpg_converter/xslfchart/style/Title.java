package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ChartText;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ManualLayout;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextRun;

public class Title {

	/**
	 * 5.7.2.211 title (Title)
	 * 
	 * 
	 * extLst (Chart Extensibility) §5.7.2.64 layout (Layout) §5.7.2.88 overlay
	 * (Overlay) §5.7.2.133 spPr (Shape Properties) §5.7.2.198 tx (Chart Text)
	 * §5.7.2.215 txPr (Text Properties) §5.7.2.217
	 */

	ManualLayout manualLayout;
	boolean overlay;
	ShapeProperties spPr;
	TextProperties txPr;
	ChartText tx;

	public ChartText getTx() {
		return tx;
	}

	public void setTx(ChartText tx) {
		this.tx = tx;
	}

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

	public String getTitleFontStyle() {
		String fontStyle = "";
		if (tx != null && tx.getRich().getP().getR() != null) {
			fontStyle = tx.getRich().getP().getR().get(0).getrPr().getEa().getTypeface();
		} else {
			fontStyle = "";
		}
		return fontStyle;
	}

	public String getTitleContentText() {
		String textContent = "";
		if (tx != null && tx.getRich().getP().getR() != null) {
			for (TextRun r : tx.getRich().getP().getR()) {
				textContent = textContent.trim() + " " + r.getT();
			}
		} else {
			textContent = "default text judged by chart type";
		}
		return textContent.trim();
	}

	public int getTitleFontSize() {
		int fontSize = 0;
		if (tx != null) {
			if (tx.getRich().getP().getR() != null) {
				fontSize = tx.getRich().getP().getR().get(0).getrPr().getSz();
			} else {
				fontSize = tx.getRich().getP().getpPr().getDefRPr().getSz();
			}
		} else {
			fontSize = txPr.getP().getpPr().getDefRPr().getSz();
		}

		return fontSize;
	}
}
