package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;

/**
 * 
 * axId (Axis ID) §5.7.2.9 axPos (Axis Position) §5.7.2.10 crossAx (Crossing
 * Axis ID) §5.7.2.31 crossBetween (Cross Between) §5.7.2.32 crosses (Crosses)
 * §5.7.2.33 crossesAt (Crossing Value) §5.7.2.34 delete (Delete) §5.7.2.40
 * dispUnits (Display Units) §5.7.2.45 extLst (Chart Extensibility) §5.7.2.64
 * majorGridlines (Major Gridlines) §5.7.2.101 majorTickMark (Major Tick Mark)
 * §5.7.2.102 majorUnit (Major Unit) §5.7.2.104 minorGridlines (Minor Gridlines)
 * §5.7.2.110 minorTickMark (Minor Tick Mark) §5.7.2.111 minorUnit (Minor Unit)
 * §5.7.2.113 numFmt (Number Format) §5.7.2.122 scaling (Scaling) §5.7.2.161
 * spPr (Shape Properties) §5.7.2.198 tickLblPos (Tick Label Position)
 * §5.7.2.208 title (Title) §5.7.2.211 txPr (Text Properties) §5.7.2.217
 *
 *
 * <complexType name="CT_ValAx"> <sequence>
 * <group ref="EG_AxShared" minOccurs="1" maxOccurs="1"/>
 * <element name="crossBetween" type="CT_CrossBetween" minOccurs="0" maxOccurs=
 * "1"/>
 * <element name="majorUnit" type="CT_AxisUnit" minOccurs="0" maxOccurs="1"/>
 * <element name="minorUnit" type="CT_AxisUnit" minOccurs="0" maxOccurs="1"/>
 * <element name="dispUnits" type="CT_DispUnits" minOccurs="0" maxOccurs="1"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 */

public class ValueAxis {

	String axId;

	// scaling
	String orientation;
	String min;
	String max;

	boolean delete;
	String axPos;

	GridlineMajor majorGridlines;

	// numFrt
	String sourceLinked;//
	String formatCode;//

	String majorTickMark;
	String minorTickMark;
	String tickLblPos;

	ShapeProperties spPr;
	TextProperties txPr;

	String crossAxis;
	String crosssers;
	String crossBetween;

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

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
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

	public GridlineMajor getMajorGridlines() {
		return majorGridlines;
	}

	public void setMajorGridlines(GridlineMajor majorGridlines) {
		this.majorGridlines = majorGridlines;
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

	public String getCrossBetween() {
		return crossBetween;
	}

	public void setCrossBetween(String crossBetween) {
		this.crossBetween = crossBetween;
	}

}
