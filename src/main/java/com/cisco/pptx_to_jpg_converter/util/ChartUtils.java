package com.cisco.pptx_to_jpg_converter.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFGraphicFrame;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTheme;
import org.apache.poi.xslf.usermodel.charts.XSLFChartAxis;
import org.apache.poi.xslf.usermodel.charts.XSLFValueAxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.ui.RectangleInsets;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAreaChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAreaSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBar3DChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDPt;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLine3DChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPie3DChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTx;
import org.openxmlformats.schemas.drawingml.x2006.main.CTColorScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.model.ChartObj;
import com.cisco.pptx_to_jpg_converter.xslfchart.ColumnStackedChart;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.BarRenderer3D;
import com.orsoncharts.renderer.category.StandardCategoryColorSource;

public class ChartUtils {

	private static final Logger logger = LoggerFactory.getLogger(ChartUtils.class);

	public static void setChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {
		setBarChart(chart, graphics, slide);
		set3DBarChart(chart, graphics, slide);
		setPieChart(chart, graphics, slide);
		set3DPieChart(chart, graphics, slide);
		setLineChart(chart, graphics, slide);
		set3DLineChart(chart, graphics, slide);
		setAreaChart(chart, graphics, slide);
	}

	public static void setBarChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {
		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}

		// for test
		// List<CTGradientStop> gsLst =
		// chart.getCTChart().getPlotArea().getSpPr().getGradFill().getGsLst().getGsList();
		// for (CTGradientStop gs : gsLst) {
		// logger.info(String.valueOf(gs.getPos()));
		// }
		//
		// logger.info(chart.getCTChart().getPlotArea().getLayout().getManualLayout().getX().toString());
		// logger.info(chart.getCTChart().getPlotArea().getLayout().getManualLayout().getY().toString());
		// logger.info(chart.getCTChart().getPlotArea().getLayout().getManualLayout().getW().toString());
		// logger.info(chart.getCTChart().getPlotArea().getLayout().getManualLayout().getH().toString());

		// -test
		String barType = "";
		List<CTBarChart> barList = chart.getCTChart().getPlotArea().getBarChartList();
		if (barList.size() > 0) {
			for (CTBarChart bar : barList) {
				// test
				ColumnStackedChart ccChart = XSLFChartFactory.createColumnStackedChart(chart, slide);
				XSLFChartDraw.drawColumnCahrt(ccChart, graphics);

				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = getBarChartTitle(chart);
				bar.getAxIdList();
				barType = bar.getGrouping().getVal().toString();
				for (CTBarSer ser : bar.getSerList()) {
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					String color = "";
					if (ser.getSpPr() != null) {
						if (ser.getSpPr().getLn() != null && ser.getSpPr().getLn().getSolidFill() != null) {
							color = ser.getSpPr().getLn().getSolidFill().getSchemeClr().getVal().toString();
						} else {
							color = ser.getSpPr().getSolidFill().getSchemeClr().getVal().toString();
						}
					} else {
						color = "accent1";
					}
					List<CTStrVal> serTxList = new ArrayList<CTStrVal>();
					List<CTStrVal> strCatList = new ArrayList<CTStrVal>();
					List<CTNumVal> numValList = new ArrayList<CTNumVal>();
					if (ser.getTx() != null) {
						serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
						strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
						numValList = ser.getVal().getNumRef().getNumCache().getPtList();
						for (CTStrVal tx : serTxList) {
							for (int i = 0; i < strCatList.size(); i++) {
								ChartObj chartObj = new ChartObj();
								chartObj.setIdx(idx);
								chartObj.setOrder(order);
								chartObj.setColor(color);
								chartObj.setSeries(tx.getV());
								chartObj.setPoint(strCatList.get(i).getV());
								chartObj.setValue(numValList.get(i).getV());
								if (ser.getDLbls() != null) {
									chartObj.setShowVal(ser.getDLbls().getShowVal().getVal());
								} else {
									chartObj.setShowVal(false);
								}
								chartObjList.add(chartObj);
							}
						}
					} else {
						strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
						numValList = ser.getVal().getNumRef().getNumCache().getPtList();
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(color);
							chartObj.setSeries("");
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							if (ser.getDLbls() != null) {
								chartObj.setShowVal(ser.getDLbls().getShowVal().getVal());
							} else {
								chartObj.setShowVal(false);
							}
							chartObjList.add(chartObj);

						}
					}

				}
				DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
				for (ChartObj chartObj : chartObjList) {
					categoryDataset.setValue(Double.parseDouble(chartObj.getValue()), chartObj.getSeries(),
							chartObj.getPoint());
				}
				JFreeChart barChart = ChartFactory.createBarChart(title, "",
						chart.getCTChart().getSideWall() == null ? "" : chart.getCTChart().getSideWall().toString(),
						categoryDataset, PlotOrientation.VERTICAL, true, true, false);
				barChart.setBackgroundPaint(slide.getBackground().getFillColor());
				barChart.setBorderVisible(false);
				CategoryPlot plot = (CategoryPlot) barChart.getPlot();
				plot.setBackgroundPaint(slide.getBackground().getFillColor());
				plot.setRangeGridlinesVisible(true);
				plot.setRangeGridlinePaint(Color.lightGray);
				plot.setRangeGridlineStroke(new BasicStroke(1.0f));
				plot.setOutlinePaint(Color.WHITE);

				barChart.getLegend().setFrame(BlockBorder.NONE);

				// plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				// plot.setDomainGridlinePosition(CategoryAnchor.MIDDLE);

				// 旋转90度
				// CategoryAxis domainAxis = plot.getDomainAxis();
				// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

				// 轴和图的边距
				plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));

				// 数据轴精度
				NumberAxis vn = (NumberAxis) plot.getRangeAxis();
				vn.setAxisLineVisible(false);

				for (XSLFChartAxis axis : chart.getAxis()) {
					if (axis instanceof XSLFValueAxis) {
						axis.getCrosses();
						axis.getMaximum();
						axis.getMinimum();
						axis.getMinorTickMark();
						axis.getNumberFormat();
					}

				}
				// 设置最大值
				// vn.setUpperBound(6);
				// 设置数据轴坐标从0开始
				// vn.setAutoRangeIncludesZero(true);

				vn.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

				vn.setNumberFormatOverride(new DecimalFormat("#0"));

				BarRenderer customBarRenderer = (BarRenderer) plot.getRenderer();
				for (ChartObj chartObj : chartObjList) {
					customBarRenderer.setSeriesPaint(Integer.valueOf(chartObj.getIdx()),
							getRgbClr(slide.getTheme(), chartObj.getColor()));
					// 设置 柱子显示值
					// customBarRenderer.setBaseItemLabelGenerator(new
					// StandardCategoryItemLabelGenerator());
					customBarRenderer.setBaseItemLabelsVisible(chartObj.isShowVal());
				}
				// 设置阴影不显示
				customBarRenderer.setShadowVisible(false);
				// showlegend
				if (chart.getCTChart().getLegend() == null) {
					customBarRenderer.setBaseSeriesVisibleInLegend(false);
				}

				customBarRenderer.setBarPainter(new StandardBarPainter());
				if (bar.getOverlap() != null) {
					double gapWith = Double.valueOf(bar.getGapWidth().xgetVal().getIntValue());
					double overlap = Double.valueOf(bar.getOverlap().xgetVal().getIntValue());
					double staticData = Double.valueOf(635);
					double percent = Double.valueOf(100);
					customBarRenderer.setItemMargin(-overlap / percent * gapWith / staticData);
				} else {
					customBarRenderer.setItemMargin(0);
				}
				// customBarRenderer.setItemMargin(rectangle.getWidth() * Double
				// .valueOf(Double.valueOf(-bar.getOverlap().xgetVal().getIntValue()) /
				// Double.valueOf(100)));
				// barChart.draw(graphics, rectangle, null);
			}

		}
	}

	public static void set3DBarChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {

		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}
		String grouping = null;
		List<CTBar3DChart> barList = chart.getCTChart().getPlotArea().getBar3DChartList();
		if (barList.size() > 0) {
			for (CTBar3DChart bar : barList) {
				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = getBarChartTitle(chart);
				grouping = bar.getGrouping().getVal().toString();
				for (CTBarSer ser : bar.getSerList()) {
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					String color = "";
					if (ser.getSpPr() != null) {
						if (ser.getSpPr().getLn() != null && ser.getSpPr().getLn().getSolidFill() != null) {
							color = ser.getSpPr().getLn().getSolidFill().getSchemeClr().getVal().toString();
						} else {
							color = ser.getSpPr().getSolidFill().getSchemeClr().getVal().toString();
						}
					} else {
						color = "accent1";
					}
					List<CTStrVal> serTxList = new ArrayList<CTStrVal>();
					List<CTStrVal> strCatList = new ArrayList<CTStrVal>();
					List<CTNumVal> numValList = new ArrayList<CTNumVal>();
					if (ser.getTx() != null) {
						serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
						strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
						numValList = ser.getVal().getNumRef().getNumCache().getPtList();
						for (CTStrVal tx : serTxList) {
							for (int i = 0; i < strCatList.size(); i++) {
								ChartObj chartObj = new ChartObj();
								chartObj.setIdx(idx);
								chartObj.setOrder(order);
								chartObj.setColor(color);
								chartObj.setSeries(tx.getV());
								chartObj.setPoint(strCatList.get(i).getV());
								chartObj.setValue(numValList.get(i).getV());
								if (ser.getDLbls() != null) {
									chartObj.setShowVal(ser.getDLbls().getShowVal().getVal());
								} else {
									chartObj.setShowVal(false);
								}
								chartObjList.add(chartObj);
							}
						}
					} else {
						strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
						numValList = ser.getVal().getNumRef().getNumCache().getPtList();
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(color);
							chartObj.setSeries("");
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							if (ser.getDLbls() != null) {
								chartObj.setShowVal(ser.getDLbls().getShowVal().getVal());
							} else {
								chartObj.setShowVal(false);
							}
							chartObjList.add(chartObj);

						}
					}

				}
				if ("clustered".equals(grouping)) {
					create3DBarClusteredChart(chart, graphics, slide, rectangle, chartObjList, title);
				}
				if ("standard".equals(grouping)) {
					create3DBarStandardChart(chart, graphics, slide, rectangle, chartObjList, title);
				}

			}

		}

	}

	private static void create3DBarClusteredChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide,
			Rectangle2D rectangle, List<ChartObj> chartObjList, String title) {
		DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
		for (ChartObj chartObj : chartObjList) {
			categoryDataset.setValue(Double.parseDouble(chartObj.getValue()), chartObj.getSeries(),
					chartObj.getPoint());
		}
		JFreeChart barChart = ChartFactory.createBarChart3D(title, "",
				chart.getCTChart().getSideWall() == null ? "" : chart.getCTChart().getSideWall().toString(),
				categoryDataset, PlotOrientation.VERTICAL, true, true, false);
		barChart.setBackgroundPaint(slide.getBackground().getFillColor());
		barChart.setBorderVisible(false);
		CategoryPlot plot = (CategoryPlot) barChart.getPlot();
		plot.setBackgroundPaint(slide.getBackground().getFillColor());
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.lightGray);
		plot.setRangeGridlineStroke(new BasicStroke(1.0f));
		plot.setOutlinePaint(Color.WHITE);

		plot.setForegroundAlpha(1.0f);

		barChart.getLegend().setFrame(BlockBorder.NONE);

		// plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		// plot.setDomainGridlinePosition(CategoryAnchor.MIDDLE);

		// 旋转90度
		// CategoryAxis domainAxis = plot.getDomainAxis();
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

		// 轴和图的边距
		plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));

		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		vn.setAxisLineVisible(false);

		// 设置最大值
		// vn.setUpperBound(6);
		// 设置数据轴坐标从0开始
		// vn.setAutoRangeIncludesZero(true);

		vn.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// vn.setNumberFormatOverride(new DecimalFormat("#0"));

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.0);
		domainAxis.setLabelAngle(0.5);

		BarRenderer customBarRenderer = (BarRenderer) plot.getRenderer();
		for (ChartObj chartObj : chartObjList) {
			customBarRenderer.setSeriesPaint(Integer.valueOf(chartObj.getIdx()),
					getRgbClr(slide.getTheme(), chartObj.getColor()));
			// 设置 柱子显示值
			// customBarRenderer.setBaseItemLabelGenerator(new
			// StandardCategoryItemLabelGenerator());
			customBarRenderer.setBaseItemLabelsVisible(chartObj.isShowVal());
		}
		// 设置阴影不显示
		customBarRenderer.setShadowVisible(false);
		// showlegend
		if (chart.getCTChart().getLegend() == null) {
			customBarRenderer.setBaseSeriesVisibleInLegend(false);
		}

		customBarRenderer.setBarPainter(new StandardBarPainter());

		customBarRenderer.setItemMargin(0);

		barChart.draw(graphics, rectangle, null);
	}

	private static void create3DBarStandardChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide,
			Rectangle2D rectangle, List<ChartObj> chartObjList, String title) {
		StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
		Set<String> serIdset = new HashSet<String>();
		for (ChartObj obj : chartObjList) {
			serIdset.add(obj.getIdx());
		}
		for (String idx : serIdset) {
			DefaultKeyedValues<String, Double> dKeyValue = new DefaultKeyedValues<String, Double>();
			String series = null;
			for (ChartObj obj : chartObjList) {
				if (idx.equals(obj.getIdx())) {
					series = obj.getSeries();
					dKeyValue.put(obj.getPoint(), Double.valueOf(obj.getValue()));
				}
			}
			dataset.addSeriesAsRow(series, dKeyValue);
		}
		CategoryDataset3D dataset3D = dataset;
		Chart3D chart3D = Chart3DFactory.createBarChart(title, null, dataset3D, null, null, null);
		chart3D.setChartBoxColor(slide.getBackground().getFillColor());
		chart3D.setLegendAnchor(LegendAnchor.CENTER_RIGHT);

		CategoryPlot3D plot = (CategoryPlot3D) chart3D.getPlot();
		plot.setGridlinePaintForValues(Color.BLACK);

		BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
		renderer.setColorSource(new StandardCategoryColorSource() {
			@Override
			public Color getColor(int series, int row, int column) {
				for (String idx : serIdset) {
					for (ChartObj obj : chartObjList) {
						if (String.valueOf(series).equals(obj.getIdx())) {
							return getRgbClr(slide.getTheme(), obj.getColor());
						}
					}
				}
				return super.getColor(series, row, column);
			}
		});

		// StandardCategoryItemLabelGenerator itemLabelGenerator = new
		// StandardCategoryItemLabelGenerator(
		// StandardCategoryItemLabelGenerator.VALUE_TEMPLATE);
		// StandardKeyedValues3DItemSelection itemSelection = new
		// StandardKeyedValues3DItemSelection();
		// itemLabelGenerator.setItemSelection(itemSelection);
		// renderer.setItemLabelGenerator(itemLabelGenerator);

		chart3D.getViewPoint().panLeftRight(Math.PI / 16);

		chart3D.draw(graphics, rectangle);
	}

	public static void setPieChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {
		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}
		List<CTPieChart> pieList = chart.getCTChart().getPlotArea().getPieChartList();
		if (pieList.size() > 0) {
			for (CTPieChart pie : pieList) {
				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = null;
				for (CTPieSer ser : pie.getSerList()) {
					title = getBarChartTitle(chart);
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					List<CTDPt> ctDPtList = ser.getDPtList();
					List<CTStrVal> serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
					List<CTStrVal> strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
					List<CTNumVal> numValList = ser.getVal().getNumRef().getNumCache().getPtList();
					for (CTStrVal tx : serTxList) {
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(
									ctDPtList.get(i).getSpPr().getSolidFill().getSchemeClr().getVal().toString());
							chartObj.setSeries(tx.getV());
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							if (ser.getDLbls() != null) {
								chartObj.setShowSerName(ser.getDLbls().getShowSerName().getVal());
							} else {
								chartObj.setShowSerName(false);
							}
							chartObjList.add(chartObj);

						}
					}
				}
				// create a dataset...
				DefaultPieDataset data = new DefaultPieDataset();
				for (ChartObj chartObj : chartObjList) {
					data.setValue(chartObj.getPoint(), Double.parseDouble(chartObj.getValue()));
				}
				// create a chart
				JFreeChart jChart = ChartFactory.createPieChart(title, data, true, false, false);
				jChart.setBackgroundPaint(slide.getBackground().getFillColor());
				jChart.setBorderVisible(false);
				jChart.getLegend().setFrame(BlockBorder.NONE);

				PiePlot plot = (PiePlot) jChart.getPlot();
				plot.setBackgroundPaint(slide.getBackground().getFillColor());
				plot.setOutlinePaint(Color.WHITE);
				plot.setShadowPaint(Color.WHITE);
				for (ChartObj chartObj : chartObjList) {
					plot.setSectionPaint(chartObj.getPoint(), getRgbClr(slide.getTheme(), chartObj.getColor()));
					plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
					plot.setLabelLinksVisible(chartObj.isShowSerName());
					plot.setForegroundAlpha(1.0f);
				}
				jChart.draw(graphics, rectangle, null);

			}

		}

	}

	public static void set3DPieChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {

		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}
		List<CTPie3DChart> pieList = chart.getCTChart().getPlotArea().getPie3DChartList();
		if (pieList.size() > 0) {
			for (CTPie3DChart pie : pieList) {
				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = null;
				for (CTPieSer ser : pie.getSerList()) {
					title = getBarChartTitle(chart);
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					List<CTDPt> ctDPtList = ser.getDPtList();
					List<CTStrVal> serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
					List<CTStrVal> strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
					List<CTNumVal> numValList = ser.getVal().getNumRef().getNumCache().getPtList();
					for (CTStrVal tx : serTxList) {
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(
									ctDPtList.get(i).getSpPr().getSolidFill().getSchemeClr().getVal().toString());
							chartObj.setSeries(tx.getV());
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							if (ser.getDLbls() != null) {
								chartObj.setShowSerName(ser.getDLbls().getShowSerName().getVal());
							} else {
								chartObj.setShowSerName(false);
							}
							chartObjList.add(chartObj);

						}
					}
				}
				// create a dataset...
				DefaultPieDataset data = new DefaultPieDataset();
				for (ChartObj chartObj : chartObjList) {
					data.setValue(chartObj.getPoint(), Double.parseDouble(chartObj.getValue()));
				}
				// create a chart
				JFreeChart jChart = ChartFactory.createPieChart3D(title, data, true, false, false);
				jChart.setBackgroundPaint(slide.getBackground().getFillColor());
				jChart.setBorderVisible(false);
				jChart.getLegend().setFrame(BlockBorder.NONE);

				PiePlot plot = (PiePlot) jChart.getPlot();
				plot.setBackgroundPaint(slide.getBackground().getFillColor());
				plot.setOutlinePaint(Color.WHITE);
				plot.setShadowPaint(Color.WHITE);
				for (ChartObj chartObj : chartObjList) {
					plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
					plot.setLabelLinksVisible(chartObj.isShowSerName());
					plot.setForegroundAlpha(1.0f);
					plot.setSectionPaint(chartObj.getPoint(), getRgbClr(slide.getTheme(), chartObj.getColor()));
				}
				plot.setSectionOutlinesVisible(false);
				jChart.draw(graphics, rectangle, null);

			}

		}

	}

	public static void setLineChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {
		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}
		List<CTLineChart> lineList = chart.getCTChart().getPlotArea().getLineChartList();
		if (lineList.size() > 0) {
			for (CTLineChart line : lineList) {
				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = null;
				for (CTLineSer ser : line.getSerList()) {
					title = getBarChartTitle(chart);
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					String color = "";
					if (ser.getSpPr() != null) {
						color = ser.getSpPr().getLn().getSolidFill().getSchemeClr().getVal().toString();
					} else {
						color = "accent1";
					}
					List<CTStrVal> serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
					List<CTStrVal> strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
					List<CTNumVal> numValList = ser.getVal().getNumRef().getNumCache().getPtList();
					for (CTStrVal tx : serTxList) {
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(color);
							chartObj.setSeries(tx.getV());
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							chartObjList.add(chartObj);
						}
					}
				}
				DefaultCategoryDataset categorydataset = new DefaultCategoryDataset();
				for (ChartObj chartObj : chartObjList) {
					categorydataset.setValue(Double.parseDouble(chartObj.getValue()), chartObj.getSeries(),
							chartObj.getPoint());
				}
				JFreeChart jChart = ChartFactory.createLineChart(title, // 图表标题
						null, // 主轴标签
						chart.getCTChart().getSideWall() == null ? "" : chart.getCTChart().getSideWall().toString(), // 范围轴标签
						categorydataset, // 数据集
						PlotOrientation.VERTICAL, // 方向
						false, // 是否包含图例
						true, // 提示信息是否显示
						false// 是否使用urls
				);
				jChart.setBackgroundPaint(slide.getBackground().getFillColor());
				jChart.setBorderVisible(false);
				jChart.getLegend().setFrame(BlockBorder.NONE);

				CategoryPlot plot = (CategoryPlot) jChart.getPlot();
				plot.setBackgroundPaint(slide.getBackground().getFillColor());
				plot.setOutlinePaint(Color.WHITE);
				LineAndShapeRenderer render = (LineAndShapeRenderer) plot.getRenderer();
				for (ChartObj chartObj : chartObjList) {
					render.setSeriesPaint(Integer.valueOf(chartObj.getIdx()),
							getRgbClr(slide.getTheme(), chartObj.getColor()));
				}
				jChart.draw(graphics, rectangle, null);
			}

		}
	}

	public static void set3DLineChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {

		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}
		List<CTLine3DChart> lineList = chart.getCTChart().getPlotArea().getLine3DChartList();
		if (lineList.size() > 0) {
			for (CTLine3DChart line : lineList) {
				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = null;
				for (CTLineSer ser : line.getSerList()) {
					title = getBarChartTitle(chart);
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					String color = "";
					if (ser.getSpPr() != null) {
						color = ser.getSpPr().getSolidFill().getSchemeClr().getVal().toString();
					} else {
						color = "accent1";
					}
					List<CTStrVal> serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
					List<CTStrVal> strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
					List<CTNumVal> numValList = ser.getVal().getNumRef().getNumCache().getPtList();
					for (CTStrVal tx : serTxList) {
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(color);
							chartObj.setSeries(tx.getV());
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							chartObjList.add(chartObj);
						}
					}
				}
				DefaultCategoryDataset categorydataset = new DefaultCategoryDataset();
				for (ChartObj chartObj : chartObjList) {
					categorydataset.setValue(Double.parseDouble(chartObj.getValue()), chartObj.getSeries(),
							chartObj.getPoint());
				}
				JFreeChart jChart = ChartFactory.createLineChart3D(title, // 图表标题
						null, // 主轴标签
						chart.getCTChart().getSideWall() == null ? "" : chart.getCTChart().getSideWall().toString(), // 范围轴标签
						categorydataset, // 数据集
						PlotOrientation.VERTICAL, // 方向
						false, // 是否包含图例
						true, // 提示信息是否显示
						false// 是否使用urls
				);
				jChart.setBackgroundPaint(slide.getBackground().getFillColor());
				jChart.setBorderVisible(false);

				CategoryPlot plot = (CategoryPlot) jChart.getPlot();
				plot.setBackgroundPaint(slide.getBackground().getFillColor());
				plot.setOutlinePaint(Color.WHITE);
				// 设置前景色透明度（0~1）
				plot.setForegroundAlpha(1.0f);

				LineAndShapeRenderer render = (LineAndShapeRenderer) plot.getRenderer();
				for (ChartObj chartObj : chartObjList) {
					render.setSeriesPaint(Integer.valueOf(chartObj.getIdx()),
							getRgbClr(slide.getTheme(), chartObj.getColor()));
				}

				jChart.draw(graphics, rectangle, null);
			}

		}

	}

	public static void setAreaChart(XSLFChart chart, SVGGraphics2D graphics, XSLFSlide slide) {

		Rectangle2D rectangle = null;
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				rectangle = shape.getAnchor();
			}
		}
		List<CTAreaChart> areaList = chart.getCTChart().getPlotArea().getAreaChartList();
		if (areaList.size() > 0) {
			for (CTAreaChart area : areaList) {
				List<ChartObj> chartObjList = new ArrayList<ChartObj>();
				String title = null;
				for (CTAreaSer ser : area.getSerList()) {
					title = getBarChartTitle(chart);
					String idx = String.valueOf(ser.getIdx().getVal());
					String order = String.valueOf(ser.getOrder().getVal());
					String color = "";
					if (ser.getSpPr() != null) {
						color = ser.getSpPr().getSolidFill().getSchemeClr().getVal().toString();
					} else {
						color = "accent1";
					}
					List<CTStrVal> serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
					List<CTStrVal> strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
					List<CTNumVal> numValList = ser.getVal().getNumRef().getNumCache().getPtList();
					for (CTStrVal tx : serTxList) {
						for (int i = 0; i < strCatList.size(); i++) {
							ChartObj chartObj = new ChartObj();
							chartObj.setIdx(idx);
							chartObj.setOrder(order);
							chartObj.setColor(color);
							chartObj.setSeries(tx.getV());
							chartObj.setPoint(strCatList.get(i).getV());
							chartObj.setValue(numValList.get(i).getV());
							chartObjList.add(chartObj);
						}
					}
				}
				DefaultCategoryDataset categorydataset = new DefaultCategoryDataset();
				for (ChartObj chartObj : chartObjList) {
					categorydataset.setValue(Double.parseDouble(chartObj.getValue()), chartObj.getSeries(),
							chartObj.getPoint());
				}
				JFreeChart jChart = ChartFactory.createAreaChart(title, // 图表标题
						null, // 主轴标签
						chart.getCTChart().getSideWall() == null ? "" : chart.getCTChart().getSideWall().toString(), // 范围轴标签
						categorydataset, // 数据集
						PlotOrientation.VERTICAL, // 方向
						false, // 是否包含图例
						true, // 提示信息是否显示
						false// 是否使用urls
				);
				jChart.setBackgroundPaint(slide.getBackground().getFillColor());
				jChart.setBorderVisible(false);

				CategoryPlot plot = (CategoryPlot) jChart.getPlot();
				plot.setBackgroundPaint(slide.getBackground().getFillColor());
				plot.setOutlinePaint(Color.WHITE);

				// 轴和图的边距
				plot.setAxisOffset(new RectangleInsets(0.0, 0.0, 0.0, 0.0));

				CategoryAxis domainAxis = plot.getDomainAxis();
				domainAxis.setLowerMargin(0.0);
				// 数据轴精度
				NumberAxis vn = (NumberAxis) plot.getRangeAxis();
				vn.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

				CategoryItemRenderer render = plot.getRenderer();
				for (ChartObj chartObj : chartObjList) {
					render.setSeriesPaint(Integer.valueOf(chartObj.getIdx()),
							getRgbClr(slide.getTheme(), chartObj.getColor()));
				}
				render.setSeriesItemLabelsVisible(0, false);

				jChart.draw(graphics, rectangle, null);
			}

		}

	}

	public static String getBarChartTitle(XSLFChart chart) {
		String title = null;
		if (chart.getCTChart().getTitle() != null) {
			CTTx tx = chart.getCTChart().getTitle().getTx();
			if (tx != null) {
				title = tx.getRich().getPList().get(0).getRList().get(0).getT();
			}
		}
		return title;
	}

	public static Color getRgbClr(XSLFTheme theme, String name) {
		Color color = null;
		CTColorScheme colorScheme = theme.getXmlObject().getThemeElements().getClrScheme();
		if ("accent1".equals(name)) {
			color = getColor(colorScheme.getAccent1().toString());
		} else if ("accent2".equals(name)) {
			color = getColor(colorScheme.getAccent2().toString());
		} else if ("accent3".equals(name)) {
			color = getColor(colorScheme.getAccent3().toString());
		} else if ("accent4".equals(name)) {
			color = getColor(colorScheme.getAccent4().toString());
		} else if ("accent5".equals(name)) {
			color = getColor(colorScheme.getAccent5().toString());
		} else if ("accent6".equals(name)) {
			color = getColor(colorScheme.getAccent6().toString());
		} else if ("lt1".equals(name)) {
			color = getColor(colorScheme.getLt1().toString());
		} else if ("lt2".equals(name)) {
			color = getColor(colorScheme.getLt2().toString());
		} else if ("dk1".equals(name)) {
			color = getColor(colorScheme.getDk1().toString());
		} else if ("dk2".equals(name)) {
			color = getColor(colorScheme.getDk2().toString());
		}
		// else if ("bg1".equals(name)) {
		// CTScRgbColor ctScRgbColor = (CTScRgbColor)
		// colorScheme.getDk2().getScrgbClr();
		// color = new Color(ctScRgbColor.getR(), ctScRgbColor.getG(),
		// ctScRgbColor.getB());
		// } else if ("bg2".equals(name)) {
		// CTScRgbColor ctScRgbColor = (CTScRgbColor)
		// colorScheme.getAccent2().getScrgbClr();
		// color = new Color(ctScRgbColor.getR(), ctScRgbColor.getG(),
		// ctScRgbColor.getB());
		// } else if ("tx1".equals(name)) {
		// CTScRgbColor ctScRgbColor = (CTScRgbColor)
		// colorScheme.getAccent2().getScrgbClr();
		// color = new Color(ctScRgbColor.getR(), ctScRgbColor.getG(),
		// ctScRgbColor.getB());
		// }
		else if ("hlink".equals(name)) {
			color = getColor(colorScheme.getHlink().toString());
		}
		// else if ("bg2".equals(name)) {
		// CTScRgbColor ctScRgbColor = (CTScRgbColor)
		// colorScheme.getAccent2().getScrgbClr();
		// color = new Color(ctScRgbColor.getR(), ctScRgbColor.getG(),
		// ctScRgbColor.getB());
		// }

		return color;
	}

	private static Color getColor(String colorQ) {
		Color color = null;
		String colorType = colorQ.substring(colorQ.indexOf("a:") + 2, colorQ.indexOf(" val"));
		if ("srgbClr".equals(colorType)) {
			String val = colorQ.substring(colorQ.indexOf("val=\"") + 5, colorQ.indexOf("\" xmlns"));
			color = Color.decode("#" + val);
		}
		return color;
	}
}
