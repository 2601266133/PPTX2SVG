package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;

/**
 * 
 * auto (Automatic Category Axis) §5.7.2.6 axId (Axis ID) §5.7.2.9 axPos (Axis
 * Position) §5.7.2.10 crossAx (Crossing Axis ID) §5.7.2.31 crosses (Crosses)
 * §5.7.2.33 crossesAt (Crossing Value) §5.7.2.34 delete (Delete) §5.7.2.40
 * extLst (Chart Extensibility) §5.7.2.64 lblAlgn (Label Alignment) §5.7.2.90
 * lblOffset (Label Offset) §5.7.2.91 majorGridlines (Major Gridlines)
 * §5.7.2.101 majorTickMark (Major Tick Mark) §5.7.2.102 minorGridlines (Minor
 * Gridlines) §5.7.2.110 minorTickMark (Minor Tick Mark) §5.7.2.111
 * noMultiLvlLbl (No Multi-level Labels) §5.7.2.120 numFmt (Number Format)
 * §5.7.2.122 scaling (Scaling) §5.7.2.161 spPr (Shape Properties) §5.7.2.198
 * tickLblPos (Tick Label Position) §5.7.2.208 tickLblSkip (Tick Label Skip)
 * §5.7.2.209 tickMarkSkip (Tick Mark Skip) §5.7.2.210 title (Title) §5.7.2.211
 * txPr (Text Properties) §5.7.2.217
 *
 */

public class CategoryAxis {
	String axId;

	// scaling
	String orientation;

	boolean delete;
	String axPos;

	// numFmt
	String sourceLinked;
	String formatCode;

	String majorTickMark;
	String minorTickMark;
	String tickLblPos;

	ShapeProperties spPr;
	TextProperties txPr;

	String crossAxis;
	String crosssers;

	boolean auto;
	String lblAlgn;
	String lblOffset;
	boolean noMultiLvlLbl;

	public String getAxId() {
		return axId;
	}

	public void setAxId(String axId) {
		this.axId = axId;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getAxPos() {
		return axPos;
	}

	public void setAxPos(String axPos) {
		this.axPos = axPos;
	}

	public String getSourceLinked() {
		return sourceLinked;
	}

	public void setSourceLinked(String sourceLinked) {
		this.sourceLinked = sourceLinked;
	}

	public String getFormatCode() {
		return formatCode;
	}

	public void setFormatCode(String formatCode) {
		this.formatCode = formatCode;
	}

	public String getMajorTickMark() {
		return majorTickMark;
	}

	public void setMajorTickMark(String majorTickMark) {
		this.majorTickMark = majorTickMark;
	}

	public String getMinorTickMark() {
		return minorTickMark;
	}

	public void setMinorTickMark(String minorTickMark) {
		this.minorTickMark = minorTickMark;
	}

	public String getTickLblPos() {
		return tickLblPos;
	}

	public void setTickLblPos(String tickLblPos) {
		this.tickLblPos = tickLblPos;
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

	public String getCrossAxis() {
		return crossAxis;
	}

	public void setCrossAxis(String crossAxis) {
		this.crossAxis = crossAxis;
	}

	public String getCrosssers() {
		return crosssers;
	}

	public void setCrosssers(String crosssers) {
		this.crosssers = crosssers;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getLblAlgn() {
		return lblAlgn;
	}

	public void setLblAlgn(String lblAlgn) {
		this.lblAlgn = lblAlgn;
	}

	public String getLblOffset() {
		return lblOffset;
	}

	public void setLblOffset(String lblOffset) {
		this.lblOffset = lblOffset;
	}

	public boolean isNoMultiLvlLbl() {
		return noMultiLvlLbl;
	}

	public void setNoMultiLvlLbl(boolean noMultiLvlLbl) {
		this.noMultiLvlLbl = noMultiLvlLbl;
	}

}
