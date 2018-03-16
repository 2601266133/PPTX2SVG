package com.cisco.pptx_to_jpg_converter.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.jfree.chart.plot.Plot;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.Align;
import org.jfree.ui.RectangleInsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.xslfchart.ColumnStackedChart;
import com.cisco.pptx_to_jpg_converter.xslfchart.XSLFChartConstants;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;

public class XSLFChartDraw {

	private static final Logger logger = LoggerFactory.getLogger(XSLFChartDraw.class);

	/**
	 * Rendering hints that will be used for chart drawing. This should never be
	 * <code>null</code>.
	 */
	private transient RenderingHints renderingHints;

	/** A flag that controls whether or not the chart border is drawn. */
	private boolean borderVisible;

	/** The stroke used to draw the chart border (if visible). */
	private transient Stroke borderStroke;

	/** The paint used to draw the chart border (if visible). */
	private transient Paint borderPaint;

	/** The padding between the chart border and the chart drawing area. */
	private RectangleInsets padding;

	/** The chart title (optional). */
	private TextTitle title;

	/**
	 * The chart subtitles (zero, one or many). This field should never be
	 * <code>null</code>.
	 */
	private List subtitles;

	/** Draws the visual representation of the data. */
	private Plot plot;

	/** Paint used to draw the background of the chart. */
	private transient Paint backgroundPaint;

	/** An optional background image for the chart. */
	private transient Image backgroundImage; // todo: not serialized yet

	/** The alignment for the background image. */
	private int backgroundImageAlignment = Align.FIT;

	/** The alpha transparency for the background image. */
	private float backgroundImageAlpha = 0.5f;

	/** Storage for registered change listeners. */
	private transient EventListenerList changeListeners;

	/** Storage for registered progress listeners. */
	private transient EventListenerList progressListeners;

	/**
	 * A flag that can be used to enable/disable notification of chart change
	 * events.
	 */
	private boolean notify;

	public static void drawColumnCahrt(ColumnStackedChart chart, Graphics2D graphic) {
		drawChartArea(graphic, chart.getXfrm());
		drawTitle(chart.getTitle(), graphic, chart.getXfrm());
		ColumnStackedChart.drawChartPlotArea(chart.getPlotArea(), graphic, chart.getXfrm());
		drawChartLegendArea(chart.getLegend(), chart.getPlotArea(), graphic, chart.getXfrm());
	}

	public static void drawChartArea(Graphics2D graphic, XFrame xframe) {
		Rectangle2D rtc = new Rectangle2D.Double(xframe.getOffX(), xframe.getOffY(), xframe.getWidth(),
				xframe.getHeight());
		graphic.setColor(Color.WHITE);
		graphic.fill(rtc);

	}

	public static void drawTitle(com.cisco.pptx_to_jpg_converter.xslfchart.style.Title title, Graphics2D graphic,
			XFrame xframe) {

		if (title != null) {

			String text = title.getTitleContentText();

			Font font = new Font(title.getTitleFontStyle(), Font.PLAIN,
					Integer.valueOf(title.getTitleFontSize() / 100));
			FontMetrics fm = graphic.getFontMetrics(font);
			double width = fm.stringWidth(text);
			double height = fm.getHeight();

			double x = xframe.getOffX() + xframe.getWidth() / 2 - width / 2;
			double y = xframe.getOffY() + XSLFChartConstants.TITLE_DEFAULT_OFFSET_Y;
			if (title.getManualLayout() != null) {
				x = xframe.getOffX() + xframe.getWidth() * title.getManualLayout().getX();
				y = xframe.getOffY() + xframe.getHeight() * title.getManualLayout().getY();
			}

			Rectangle2D rtc = new Rectangle2D.Double(x, y, width, height);
			graphic.setColor(Color.WHITE);
			graphic.fill(rtc);

			graphic.setColor(Color.BLACK);
			graphic.setFont(font);
			graphic.drawString(text, Float.valueOf(String.valueOf(x)), Float.valueOf(String.valueOf(y))
					+ Float.valueOf(String.valueOf(Integer.valueOf(title.getTitleFontSize() / 100))));

		}
		// RectangleEdge position = RectangleEdge.TOP;
		// if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
		// float maxWidth = Float.MAX_VALUE;
		// graphic.setFont(font);
		// content = TextUtilities.createTextBlock(text, font, paint, maxWidth,
		// maximumLinesToDisplay,
		// new G2TextMeasurer(graphic));
		// content.setLineAlignment(textAlignment);
		// Size2D contentSize = content.calculateDimensions(graphic);
		// }

	}

	public static void drawChartPlotArea(PlotArea plotArea, Graphics2D graphic, XFrame xframe) {
	}

	public static void drawChartLegendArea(Legend legend, PlotArea plotArea, Graphics2D graphic, XFrame xframe) {
		if (legend != null && legend.getManualLayout() != null) {
			double width = xframe.getWidth() * legend.getManualLayout().getW();
			double height = xframe.getHeight() * legend.getManualLayout().getH();

			double x = xframe.getOffX() + xframe.getWidth() * legend.getManualLayout().getX();
			double y = xframe.getOffY() + xframe.getHeight() * legend.getManualLayout().getY();

			Rectangle2D rtc = new Rectangle2D.Double(x, y, width, height);
			graphic.setColor(Color.WHITE);
			graphic.fill(rtc);

			int seriesNum = plotArea.getSerList().size();
			for (int i = 0; i < seriesNum; i++) {

				String currentSerStr = plotArea.getSerList().get(i).getTx().getV();
				int fontSize = Integer.valueOf(legend.getTxPr().getTextSize() / 100);
				Font font = new Font(legend.getTxPr().getTextFont(), Font.PLAIN, fontSize);
				FontMetrics fm = graphic.getFontMetrics(font);
				double currentSerStrWidth = fm.stringWidth(currentSerStr);
				double x1 = x + width / seriesNum / 2 - currentSerStrWidth / 2 + i * width / seriesNum;
				double y1 = y + height / 2 - fontSize / 2;
				Rectangle2D rtcCatArea = new Rectangle2D.Double(x1, y1, fontSize, fontSize);
				graphic.setColor(Color.BLACK);
				graphic.fill(rtcCatArea);

				graphic.setColor(Color.BLACK);
				graphic.setFont(font);
				graphic.drawString(currentSerStr, (int) (x1 + fontSize), (int) (y1 + fontSize));
			}
		}
	}

	public static double getMajorUnitsForNumber(Double k) {
		double defaultMajorUnits = 1.0;
		if (k < 5.0) {

		} else if (k >= 5) {
			int length = (int) Math.log10(k) + 1;
			double scaledK = k;
			if (length > 2) {
				length = length - 2;
				scaledK = scaledK / Math.pow(10, length);
			} else {
				length = 0;
			}

			if (scaledK < 10.0) {
				defaultMajorUnits = 1 * Math.pow(10, length);
			} else if (10 <= scaledK && scaledK < 20) {
				defaultMajorUnits = 2 * Math.pow(10, length);
			} else if (20 <= scaledK && scaledK < 50) {
				defaultMajorUnits = 5 * Math.pow(10, length);
			}
		}
		return defaultMajorUnits;
	}

	private static void drawGradientColor(int x, int y, int width, int height, int colorAreasNum, Graphics2D graphic) {
		int w = height; // 分成六个部分进行绘制
		for (int i = 0; i < w; i++) {
			int d = (int) (i * (255.0 / w)); // 使d从0递增到255，实际可能只是接近255
			// 画第一部分颜色---红色到黄色
			graphic.setColor(new Color(255, d, 0)); // 设置颜色
			graphic.drawLine(x, y + i, x + width, y + i); // 画直线---一条单色竖线
		}
	}
}
