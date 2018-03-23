package com.cisco.pptx_to_jpg_converter.xslfchart;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.util.XSLFChartDraw;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.CategoryAxis;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ChartArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

public class ColumnStackedChart {

	private static final Logger logger = LoggerFactory.getLogger(ColumnStackedChart.class);

	XFrame xfrm;
	ChartArea chartArea;
	Title title;

	/**
	 * stacked default <c:x val="3.5909981361025523E-2"/>
	 * <c:y val="0.1285225372057974"/> <c:w val="0.95080499448438516"/>
	 * <c:h val="0.71045388797652587"/>
	 * 
	 * 
	 * default x: 0.03590998136102552 Y: 0.1285225372057974 w: 0.95080499448438516
	 * h: 0.71045388797652587
	 * 
	 */
	PlotArea plotArea;

	Legend legend;
	boolean autoTitleDeleted;
	boolean plotVisOnly;
	String dispBlanksAs;
	boolean showDLblsOverMax;

	public XFrame getXfrm() {
		return xfrm;
	}

	public void setXfrm(XFrame xfrm) {
		this.xfrm = xfrm;
	}

	public ChartArea getChartArea() {
		return chartArea;
	}

	public void setChartArea(ChartArea chartArea) {
		this.chartArea = chartArea;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public PlotArea getPlotArea() {
		return plotArea;
	}

	public void setPlotArea(PlotArea plotArea) {
		this.plotArea = plotArea;
	}

	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	public boolean isAutoTitleDeleted() {
		return autoTitleDeleted;
	}

	public void setAutoTitleDeleted(boolean autoTitleDeleted) {
		this.autoTitleDeleted = autoTitleDeleted;
	}

	public boolean isPlotVisOnly() {
		return plotVisOnly;
	}

	public void setPlotVisOnly(boolean plotVisOnly) {
		this.plotVisOnly = plotVisOnly;
	}

	public String getDispBlanksAs() {
		return dispBlanksAs;
	}

	public void setDispBlanksAs(String dispBlanksAs) {
		this.dispBlanksAs = dispBlanksAs;
	}

	public boolean isShowDLblsOverMax() {
		return showDLblsOverMax;
	}

	public void setShowDLblsOverMax(boolean showDLblsOverMax) {
		this.showDLblsOverMax = showDLblsOverMax;
	}

	public static void drawChartPlotArea(PlotArea plotArea, Graphics2D graphic, XFrame xframe) {

		double width = xframe.getWidth() * plotArea.getManualLayout().getW();
		double height = xframe.getHeight() * plotArea.getManualLayout().getH();

		double x = xframe.getOffX() + xframe.getWidth() * plotArea.getManualLayout().getX();
		double y = xframe.getOffY() + xframe.getHeight() * plotArea.getManualLayout().getY();
		logger.info("x: " + String.valueOf(plotArea.getManualLayout().getX()));
		logger.info("Y: " + String.valueOf(plotArea.getManualLayout().getY()));
		logger.info("w: " + String.valueOf(plotArea.getManualLayout().getW()));
		logger.info("h: " + String.valueOf(plotArea.getManualLayout().getH()));

		Rectangle2D rtc = new Rectangle2D.Double(x, y, width, height);
		graphic.setColor(Color.WHITE);
		graphic.fill(rtc);

		int categoryNum = Integer.valueOf(String.valueOf(plotArea.getSerList().get(0).getCat().getpCount()));
		int seriesNum = Integer.valueOf(String.valueOf(plotArea.getSerList().size()));
		double gapWidth = Double.valueOf(String.valueOf(plotArea.getGapWidth()));
		double overlap = plotArea.getOverlap();

		double columnWidth = width / (categoryNum * (seriesNum + (seriesNum - 1) * (-overlap / 100) + gapWidth / 100));
		double gapWidthPt = columnWidth * (gapWidth / 100);
		double overlapPt = columnWidth * (-overlap / 100);
		logger.info("column width is: " + String.valueOf(columnWidth) + " point");
		logger.info("overlapPt is: " + String.valueOf(overlapPt));

		double oneblockWidth = gapWidthPt + seriesNum * columnWidth + (seriesNum - 1) * overlapPt;

		double sum = 0.0;
		int valsize = 0;
		for (int i = 0; i < seriesNum; i++) {
			Map<Long, String> validxVMap = plotArea.getSerList().get(i).getVal().getIdxVMap();
			valsize = validxVMap.size();
			for (int j = 0; j < validxVMap.size(); j++) {
				sum += Double.valueOf(validxVMap.get(Long.valueOf(j)));
			}
		}
		double average = sum / (seriesNum * valsize);

		double varianceSum = 0.0;
		for (int i = 0; i < seriesNum; i++) {
			Map<Long, String> validxVMap = plotArea.getSerList().get(i).getVal().getIdxVMap();
			for (int j = 0; j < validxVMap.size(); j++) {
				varianceSum += (Math.pow((Double.valueOf(validxVMap.get(Long.valueOf(j))) - average), 2));
			}
		}

		double variance = varianceSum / (seriesNum * valsize);

		logger.info("平均数是： " + String.valueOf(average) + ". 方差是: " + String.valueOf(variance) + ". 标准差是： "
				+ String.valueOf(Math.sqrt(variance)));

		List<Double> valuesList = new ArrayList<Double>();
		// draw value axis for number data
		// 1.default module
		for (int i = 0; i < seriesNum; i++) {
			Map<Long, String> validxVMap = plotArea.getSerList().get(i).getVal().getIdxVMap();
			for (Map.Entry<Long, String> entry : validxVMap.entrySet()) {
				valuesList.add(Double.valueOf(entry.getValue()));
			}
		}
		double maxVal = valuesList.get(0);
		double minVal = valuesList.get(0);
		for (Double val : valuesList) {
			if (val >= maxVal) {
				maxVal = val;
			}
			if (val <= minVal) {
				minVal = val;
			}
		}

		double k = maxVal + (maxVal - minVal) / 20;
		double majorUnits = XSLFChartDraw.getMajorUnitsForNumber(k);
		double minorUnits = majorUnits / 5;

		double majorNum = Math.floor((k + majorUnits) / majorUnits);
		double Ytop = majorUnits * majorNum;

		// draw grid line and value axis data
		for (int i = 0; i <= majorNum; i++) {
			int x1 = (int) x;
			int y1 = (int) (i * height / majorNum + y);
			int x2 = (int) (x + width);
			int y2 = (int) (i * height / majorNum + y);

			graphic.setColor(Color.GRAY);
			graphic.drawLine(x1, y1, x2, y2);// draw major grid line

			Font font = new Font(plotArea.getValAxList().get(0).getTextFont(), Font.PLAIN,
					Integer.valueOf(plotArea.getValAxList().get(0).getTextSize() / 100));
			FontMetrics fm = graphic.getFontMetrics(font);
			double valFontHeight = fm.getHeight();

			String currentValStr = "";
			if (majorUnits >= 1.0) {
				int valFontWidth = fm.stringWidth(String.valueOf((int) Ytop));
				currentValStr = String.valueOf((int) Ytop - (int) (i * majorUnits));
				// Rectangle2D rtcVal = new Rectangle2D.Double(x - valFontHeight - valFontWidth,
				// y - valFontHeight / 2,
				// valFontHeight + valFontWidth, height + valFontHeight);
				// graphic.setColor(Color.WHITE);
				// graphic.fill(rtcVal);

				graphic.setColor(Color.BLACK);
				graphic.setFont(font);
				graphic.drawString(currentValStr, (int) (x - valFontHeight - valFontWidth),
						y1 + Integer.valueOf(plotArea.getValAxList().get(0).getTextSize() / 100) / 2);
				logger.info("ValueAxis : " + currentValStr);
			} else if (majorUnits < 1.0) {
				double valFontWidth = fm.stringWidth(String.valueOf(Ytop));
				currentValStr = String.valueOf(Ytop - i * majorUnits);
				Rectangle2D rtcVal = new Rectangle2D.Double(x - valFontHeight - valFontWidth, y - valFontHeight / 2,
						valFontHeight + valFontWidth, y + valFontHeight);
				graphic.setColor(Color.WHITE);
				graphic.fill(rtcVal);

				graphic.setColor(Color.BLACK);
				graphic.setFont(font);
				graphic.drawString(currentValStr, Float.valueOf(String.valueOf(x - valFontHeight - valFontWidth)),
						Float.valueOf(String.valueOf(
								y1 + Integer.valueOf(plotArea.getValAxList().get(0).getTextSize() / 100) / 2)));
			}

		}

		// 2.custom module

		logger.info("Ymax is: " + String.valueOf(maxVal) + ". Ymin is:" + String.valueOf(minVal) + ". k is: "
				+ String.valueOf(k) + ". major units is: " + String.valueOf(majorUnits));

		// draw series columns
		for (int i = 0; i < seriesNum; i++) {

			Map<Long, String> catidxVMap = plotArea.getSerList().get(i).getCat().getIdxVMap();
			Map<Long, String> validxVMap = plotArea.getSerList().get(i).getVal().getIdxVMap();
			for (int j = 0; j < Math.max(catidxVMap.size(), validxVMap.size()); j++) {
				double columnHeight = Double.valueOf(validxVMap.get((long) j)) / Ytop * height;
				double x1 = x + gapWidthPt / 2 + j * oneblockWidth + i * (columnWidth + overlapPt);
				double y1 = y + height - columnHeight;
				Rectangle2D rtc1 = new Rectangle2D.Double(x1, y1, columnWidth, columnHeight);
				graphic.setColor(Color.BLACK);
				graphic.fill(rtc1);

			}
		}

		// draw category axis area
		for (CategoryAxis catAx : plotArea.getCatAxList()) {
			if ("b".equals(catAx.getAxPos())) {

				Font font = new Font(catAx.getTextFont(), Font.PLAIN, Integer.valueOf(catAx.getTextSize() / 100));
				FontMetrics fm = graphic.getFontMetrics(font);
				double catFontheight = fm.getHeight();
				int spcpct = 100000;
				if (catAx.getTxPr().getP() != null) {
					spcpct = catAx.getTxPr().getP().getpPr().getLnSpc().getSpcPct().getVal();
				}

				double cvHeight = catFontheight + Double.valueOf(catAx.getLblOffset()) / 100
						+ Double.valueOf(spcpct / 100000) * 1 / 2 * catFontheight;
				// Rectangle2D rtcCatArea = new Rectangle2D.Double(x, y + height, width,
				// cvHeight);
				// graphic.setColor(Color.WHITE);
				// graphic.fill(rtcCatArea);
				for (int i = 0; i < seriesNum; i++) {
					Map<Long, String> catidxVMap = plotArea.getSerList().get(i).getCat().getIdxVMap();
					Map<Long, String> validxVMap = plotArea.getSerList().get(i).getVal().getIdxVMap();
					for (int j = 0; j < Math.max(catidxVMap.size(), validxVMap.size()); j++) {
						String catStr = catidxVMap.get((long) j);
						double strWidth = fm.stringWidth(catStr);
						graphic.setColor(Color.BLACK);
						graphic.setFont(font);
						graphic.drawString(catStr,
								Float.valueOf(String.valueOf(x - strWidth / 2 + oneblockWidth / 2 + j * oneblockWidth)),
								Float.valueOf(String.valueOf(y + height + cvHeight)));
					}
				}
			}
		}

	}
}
