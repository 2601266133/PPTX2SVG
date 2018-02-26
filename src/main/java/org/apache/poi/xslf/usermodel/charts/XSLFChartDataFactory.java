package org.apache.poi.xslf.usermodel.charts;

import org.apache.poi.ss.usermodel.charts.ChartDataFactory;

public class XSLFChartDataFactory implements ChartDataFactory {

	private static XSLFChartDataFactory instance;

	private XSLFChartDataFactory() {
		super();
	}

	/**
	 * @return new scatter charts data instance
	 */
	@Override
	public XSLFScatterChartData createScatterChartData() {
		return new XSLFScatterChartData();
	}

	/**
	 * @return new line charts data instance
	 */
	@Override
	public XSLFLineChartData createLineChartData() {
		return new XSLFLineChartData();
	}

	/**
	 * @return factory instance
	 */
	public static XSLFChartDataFactory getInstance() {
		if (instance == null) {
			instance = new XSLFChartDataFactory();
		}
		return instance;
	}

}
