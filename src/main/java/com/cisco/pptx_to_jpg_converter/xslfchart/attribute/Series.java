package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

import com.cisco.pptx_to_jpg_converter.xslfchart.style.DataPoint;

/**
 * Bar Chart Series cat (Category Axis Data) §5.7.2.24 dLbls (Data Labels)
 * §5.7.2.49 dPt (Data Point) §5.7.2.52 errBars (Error Bars) §5.7.2.55 extLst
 * (Chart Extensibility) §5.7.2.64 idx (Index) §5.7.2.84 invertIfNegative
 * (Invert if Negative) §5.7.2.86 order (Order) §5.7.2.129 pictureOptions
 * (Picture Options) §5.7.2.139 shape (Shape) §5.7.2.178 spPr (Shape Properties)
 * §5.7.2.198 trendline (Trendlines) §5.7.2.212 tx (Series Text) §5.7.2.216 val
 * (Values) §5.7.2.225 <complexType name="CT_BarSer"> <sequence>
 * <group ref="EG_SerShared" minOccurs="1" maxOccurs="1"/>
 * <element name="invertIfNegative" type="CT_Boolean" minOccurs="0" maxOccurs=
 * "1"/> <element name="pictureOptions" type="CT_PictureOptions" minOccurs="0"
 * maxOccurs="1"/>
 * <element name="dPt" type="CT_DPt" minOccurs="0" maxOccurs="unbounded"/>
 * <element name="dLbls" type="CT_DLbls" minOccurs="0" maxOccurs="1"/>
 * <element name="trendline" type="CT_Trendline" minOccurs="0" maxOccurs=
 * "unbounded"/>
 * <element name="errBars" type="CT_ErrBars" minOccurs="0" maxOccurs="1"/>
 * <element name="cat" type="CT_AxDataSource" minOccurs="0" maxOccurs="1"/>
 * <element name="val" type="CT_NumDataSource" minOccurs="0" maxOccurs="1"/>
 * <element name="shape" type="CT_Shape" minOccurs="0" maxOccurs="1"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 *
 *
 * Line Chart series cat (Category Axis Data) §5.7.2.24 dLbls (Data Labels)
 * §5.7.2.49 dPt (Data Point) §5.7.2.52 errBars (Error Bars) §5.7.2.55 extLst
 * (Chart Extensibility) §5.7.2.64 idx (Index) §5.7.2.84 marker (Marker)
 * §5.7.2.106 order (Order) §5.7.2.129 smooth (Smoothing) §5.7.2.195 spPr (Shape
 * Properties) §5.7.2.198 trendline (Trendlines) §5.7.2.212 tx (Series Text)
 * §5.7.2.216 val (Values) §5.7.2.225 <complexType name="CT_LineSer"> <sequence>
 * <group ref="EG_SerShared" minOccurs="1" maxOccurs="1"/>
 * <element name="marker" type="CT_Marker" minOccurs="0" maxOccurs="1"/>
 * <element name="dPt" type="CT_DPt" minOccurs="0" maxOccurs="unbounded"/>
 * <element name="dLbls" type="CT_DLbls" minOccurs="0" maxOccurs="1"/>
 * <element name="trendline" type="CT_Trendline" minOccurs="0" maxOccurs=
 * "unbounded"/>
 * <element name="errBars" type="CT_ErrBars" minOccurs="0" maxOccurs="1"/>
 * <element name="cat" type="CT_AxDataSource" minOccurs="0" maxOccurs="1"/>
 * <element name="val" type="CT_NumDataSource" minOccurs="0" maxOccurs="1"/>
 * <element name="smooth" type="CT_Boolean" minOccurs="0" maxOccurs="1"/>
 * <element name="extLst" type="CT_ExtensionList" minOccurs="0" maxOccurs="1"/>
 * </sequence> </complexType>
 *
 *
 */

public class Series {

	long id;
	long order;
	SeriesText tx;
	ShapeProperties spPr;
	boolean invertIfNegative;
	DataPoint dPt; // Data point
	CategoryAxisData cat;
	ValueAxisData val;

	Marker marker;
	boolean smooth;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public SeriesText getTx() {
		return tx;
	}

	public void setTx(SeriesText tx) {
		this.tx = tx;
	}

	public ShapeProperties getSpPr() {
		return spPr;
	}

	public void setSpPr(ShapeProperties spPr) {
		this.spPr = spPr;
	}

	public boolean getInvertIfNegative() {
		return invertIfNegative;
	}

	public void setInvertIfNegative(boolean invertIfNegative) {
		this.invertIfNegative = invertIfNegative;
	}

	public DataPoint getdPt() {
		return dPt;
	}

	public void setdPt(DataPoint dPt) {
		this.dPt = dPt;
	}

	public CategoryAxisData getCat() {
		return cat;
	}

	public void setCat(CategoryAxisData cat) {
		this.cat = cat;
	}

	public ValueAxisData getVal() {
		return val;
	}

	public void setVal(ValueAxisData val) {
		this.val = val;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public void setSmooth(boolean smooth) {
		this.smooth = smooth;
	}

}
