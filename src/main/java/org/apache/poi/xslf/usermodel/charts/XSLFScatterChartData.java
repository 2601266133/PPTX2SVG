package org.apache.poi.xslf.usermodel.charts;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ScatterChartData;
import org.apache.poi.ss.usermodel.charts.ScatterChartSeries;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.charts.AbstractXSSFChartSeries;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScatterChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScatterSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScatterStyle;
import org.openxmlformats.schemas.drawingml.x2006.chart.STScatterStyle;

public class XSLFScatterChartData implements ScatterChartData {

	/**
	 * List of all data series.
	 */
	private List<Series> series;

	public XSLFScatterChartData() {
		series = new ArrayList<Series>();
	}

	/**
	 * Package private ScatterChartSerie implementation.
	 */
	static class Series extends AbstractXSSFChartSeries implements ScatterChartSeries {
		private int id;
		private int order;
		private ChartDataSource<?> xs;
		private ChartDataSource<? extends Number> ys;

		protected Series(int id, int order, ChartDataSource<?> xs, ChartDataSource<? extends Number> ys) {
			super();
			this.id = id;
			this.order = order;
			this.xs = xs;
			this.ys = ys;
		}

		/**
		 * Returns data source used for X axis values.
		 * 
		 * @return data source used for X axis values
		 */
		@Override
		public ChartDataSource<?> getXValues() {
			return xs;
		}

		/**
		 * Returns data source used for Y axis values.
		 * 
		 * @return data source used for Y axis values
		 */
		@Override
		public ChartDataSource<? extends Number> getYValues() {
			return ys;
		}

		protected void addToChart(CTScatterChart ctScatterChart) {
			CTScatterSer scatterSer = ctScatterChart.addNewSer();
			scatterSer.addNewIdx().setVal(this.id);
			scatterSer.addNewOrder().setVal(this.order);

			CTAxDataSource xVal = scatterSer.addNewXVal();
			XSLFChartUtil.buildAxDataSource(xVal, xs);

			CTNumDataSource yVal = scatterSer.addNewYVal();
			XSLFChartUtil.buildNumDataSource(yVal, ys);

			if (isTitleSet()) {
				scatterSer.setTx(getCTSerTx());
			}
		}
	}

	@Override
	public ScatterChartSeries addSerie(ChartDataSource<?> xs, ChartDataSource<? extends Number> ys) {
		if (!ys.isNumeric()) {
			throw new IllegalArgumentException("Y axis data source must be numeric.");
		}
		int numOfSeries = series.size();
		Series newSerie = new Series(numOfSeries, numOfSeries, xs, ys);
		series.add(newSerie);
		return newSerie;
	}

	@Override
	public void fillChart(Chart chart, ChartAxis... axis) {
		if (!(chart instanceof XSSFChart)) {
			throw new IllegalArgumentException("Chart must be instance of XSSFChart");
		}

		XSSFChart xssfChart = (XSSFChart) chart;
		CTPlotArea plotArea = xssfChart.getCTChart().getPlotArea();
		CTScatterChart scatterChart = plotArea.addNewScatterChart();
		addStyle(scatterChart);

		for (Series s : series) {
			s.addToChart(scatterChart);
		}

		for (ChartAxis ax : axis) {
			scatterChart.addNewAxId().setVal(ax.getId());
		}
	}

	@Override
	public List<? extends Series> getSeries() {
		return series;
	}

	private void addStyle(CTScatterChart ctScatterChart) {
		CTScatterStyle scatterStyle = ctScatterChart.addNewScatterStyle();
		scatterStyle.setVal(STScatterStyle.LINE_MARKER);
	}

}
