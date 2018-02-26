package org.apache.poi.xslf.usermodel.charts;

import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.util.Internal;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xssf.usermodel.charts.XSSFManualLayout;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLegend;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLegendPos;
import org.openxmlformats.schemas.drawingml.x2006.chart.STLegendPos;

public class XSLFChartLegend implements ChartLegend {

	/**
	 * Underlaying CTLagend bean
	 */
	private CTLegend legend;

	/**
	 * Create a new SpreadsheetML chart legend
	 */
	public XSLFChartLegend(XSLFChart chart) {
		CTChart ctChart = chart.getCTChart();
		this.legend = (ctChart.isSetLegend()) ? ctChart.getLegend() : ctChart.addNewLegend();

		setDefaults();
	}

	/**
	 * Set sensible default styling.
	 */
	private void setDefaults() {
		if (!legend.isSetOverlay()) {
			legend.addNewOverlay();
		}
		legend.getOverlay().setVal(false);
	}

	/**
	 * Return the underlying CTLegend bean.
	 *
	 * @return the underlying CTLegend bean
	 */
	@Internal
	public CTLegend getCTLegend() {
		return legend;
	}

	@Override
	public void setPosition(LegendPosition position) {
		if (!legend.isSetLegendPos()) {
			legend.addNewLegendPos();
		}
		legend.getLegendPos().setVal(fromLegendPosition(position));
	}

	/*
	 * According to ECMA-376 default position is RIGHT.
	 */
	@Override
	public LegendPosition getPosition() {
		if (legend.isSetLegendPos()) {
			return toLegendPosition(legend.getLegendPos());
		} else {
			return LegendPosition.RIGHT;
		}
	}

	@Override
	public XSSFManualLayout getManualLayout() {
		if (!legend.isSetLayout()) {
			legend.addNewLayout();
		}
		return new XSSFManualLayout(legend.getLayout());
	}

	@Override
	public boolean isOverlay() {
		return legend.getOverlay().getVal();
	}

	@Override
	public void setOverlay(boolean value) {
		legend.getOverlay().setVal(value);
	}

	private STLegendPos.Enum fromLegendPosition(LegendPosition position) {
		switch (position) {
		case BOTTOM:
			return STLegendPos.B;
		case LEFT:
			return STLegendPos.L;
		case RIGHT:
			return STLegendPos.R;
		case TOP:
			return STLegendPos.T;
		case TOP_RIGHT:
			return STLegendPos.TR;
		default:
			throw new IllegalArgumentException();
		}
	}

	private LegendPosition toLegendPosition(CTLegendPos ctLegendPos) {
		switch (ctLegendPos.getVal().intValue()) {
		case STLegendPos.INT_B:
			return LegendPosition.BOTTOM;
		case STLegendPos.INT_L:
			return LegendPosition.LEFT;
		case STLegendPos.INT_R:
			return LegendPosition.RIGHT;
		case STLegendPos.INT_T:
			return LegendPosition.TOP;
		case STLegendPos.INT_TR:
			return LegendPosition.TOP_RIGHT;
		default:
			throw new IllegalArgumentException();
		}
	}

}
