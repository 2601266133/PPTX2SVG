package org.apache.poi.xslf.usermodel.charts;

import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisOrientation;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.AxisTickMark;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.util.Internal;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxPos;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTCrosses;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDateAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumFmt;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScaling;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTickMark;
import org.openxmlformats.schemas.drawingml.x2006.chart.STTickLblPos;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties;

public class XSLFDateAxis extends XSLFChartAxis {

	private CTDateAx ctDateAx;

	public XSLFDateAxis(XSLFChart chart, long id, AxisPosition pos) {
		super(chart);
		createAxis(id, pos);
	}

	public XSLFDateAxis(XSLFChart chart, CTDateAx ctDateAx) {
		super(chart);
		this.ctDateAx = ctDateAx;
	}

	@Override
	public long getId() {
		return ctDateAx.getAxId().getVal();
	}

	@Override
	@Internal
	public CTShapeProperties getLine() {
		return ctDateAx.getSpPr();
	}

	@Override
	protected CTAxPos getCTAxPos() {
		return ctDateAx.getAxPos();
	}

	@Override
	protected CTNumFmt getCTNumFmt() {
		if (ctDateAx.isSetNumFmt()) {
			return ctDateAx.getNumFmt();
		}
		return ctDateAx.addNewNumFmt();
	}

	@Override
	protected CTScaling getCTScaling() {
		return ctDateAx.getScaling();
	}

	@Override
	protected CTCrosses getCTCrosses() {
		return ctDateAx.getCrosses();
	}

	@Override
	protected CTBoolean getDelete() {
		return ctDateAx.getDelete();
	}

	@Override
	protected CTTickMark getMajorCTTickMark() {
		return ctDateAx.getMajorTickMark();
	}

	@Override
	protected CTTickMark getMinorCTTickMark() {
		return ctDateAx.getMinorTickMark();
	}

	@Override
	@Internal
	public CTChartLines getMajorGridLines() {
		return ctDateAx.getMajorGridlines();
	}

	@Override
	public void crossAxis(ChartAxis axis) {
		ctDateAx.getCrossAx().setVal(axis.getId());
	}

	private void createAxis(long id, AxisPosition pos) {
		ctDateAx = chart.getCTChart().getPlotArea().addNewDateAx();
		ctDateAx.addNewAxId().setVal(id);
		ctDateAx.addNewAxPos();
		ctDateAx.addNewScaling();
		ctDateAx.addNewCrosses();
		ctDateAx.addNewCrossAx();
		ctDateAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
		ctDateAx.addNewDelete();
		ctDateAx.addNewMajorTickMark();
		ctDateAx.addNewMinorTickMark();

		setPosition(pos);
		setOrientation(AxisOrientation.MIN_MAX);
		setCrosses(AxisCrosses.AUTO_ZERO);
		setVisible(true);
		setMajorTickMark(AxisTickMark.CROSS);
		setMinorTickMark(AxisTickMark.NONE);
	}

	@Override
	public boolean hasNumberFormat() {
		return ctDateAx.isSetNumFmt();
	}

}
