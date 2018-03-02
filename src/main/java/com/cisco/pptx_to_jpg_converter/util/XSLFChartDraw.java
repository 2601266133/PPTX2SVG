package com.cisco.pptx_to_jpg_converter.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.block.BlockParams;
import org.jfree.chart.block.EntityBlockResult;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.util.ParamChecks;
import org.jfree.data.Range;
import org.jfree.text.G2TextMeasurer;
import org.jfree.text.TextBlock;
import org.jfree.text.TextUtilities;
import org.jfree.ui.Align;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.ui.VerticalAlignment;

import com.cisco.pptx_to_jpg_converter.xslfchart.ColumnStackedChart;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;

public class XSLFChartDraw {

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

	public void draw(Graphics2D g2, Rectangle2D chartArea, Point2D anchor, ChartRenderingInfo info) {
		// notifyListeners(new ChartProgressEvent(this, this,
		// ChartProgressEvent.DRAWING_STARTED, 0));

		EntityCollection entities = null;
		// record the chart area, if info is requested...
		if (info != null) {
			info.clear();
			info.setChartArea(chartArea);
			entities = info.getEntityCollection();
		}
		if (entities != null) {
			// entities.add(new JFreeChartEntity((Rectangle2D) chartArea.clone(), this));
		}

		// ensure no drawing occurs outside chart area...
		Shape savedClip = g2.getClip();
		g2.clip(chartArea);

		g2.addRenderingHints(this.renderingHints);

		// draw the chart background...
		if (this.backgroundPaint != null) {
			g2.setPaint(this.backgroundPaint);
			g2.fill(chartArea);
		}

		if (this.backgroundImage != null) {
			Composite originalComposite = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.backgroundImageAlpha));
			Rectangle2D dest = new Rectangle2D.Double(0.0, 0.0, this.backgroundImage.getWidth(null),
					this.backgroundImage.getHeight(null));
			Align.align(dest, chartArea, this.backgroundImageAlignment);
			g2.drawImage(this.backgroundImage, (int) dest.getX(), (int) dest.getY(), (int) dest.getWidth(),
					(int) dest.getHeight(), null);
			g2.setComposite(originalComposite);
		}

		if (isBorderVisible()) {
			Paint paint = getBorderPaint();
			Stroke stroke = getBorderStroke();
			if (paint != null && stroke != null) {
				Rectangle2D borderArea = new Rectangle2D.Double(chartArea.getX(), chartArea.getY(),
						chartArea.getWidth() - 1.0, chartArea.getHeight() - 1.0);
				g2.setPaint(paint);
				g2.setStroke(stroke);
				g2.draw(borderArea);
			}
		}

		// draw the title and subtitles...
		Rectangle2D nonTitleArea = new Rectangle2D.Double();
		nonTitleArea.setRect(chartArea);
		this.padding.trim(nonTitleArea);

		if (this.title != null && this.title.isVisible()) {
			EntityCollection e = drawTitle(this.title, g2, nonTitleArea, (entities != null));
			if (e != null && entities != null) {
				entities.addAll(e);
			}
		}

		Iterator iterator = this.subtitles.iterator();
		while (iterator.hasNext()) {
			Title currentTitle = (Title) iterator.next();
			if (currentTitle.isVisible()) {
				EntityCollection e = drawTitle(currentTitle, g2, nonTitleArea, (entities != null));
				if (e != null && entities != null) {
					entities.addAll(e);
				}
			}
		}

		Rectangle2D plotArea = nonTitleArea;

		// draw the plot (axes and data visualisation)
		PlotRenderingInfo plotInfo = null;
		if (info != null) {
			plotInfo = info.getPlotInfo();
		}
		this.plot.draw(g2, plotArea, anchor, null, plotInfo);

		g2.setClip(savedClip);

		// notifyListeners(new ChartProgressEvent(this, this,
		// ChartProgressEvent.DRAWING_FINISHED, 100));
	}

	/**
	 * Returns a flag that controls whether or not a border is drawn around the
	 * outside of the chart.
	 *
	 * @return A boolean.
	 *
	 * @see #setBorderVisible(boolean)
	 */
	public boolean isBorderVisible() {
		return this.borderVisible;
	}

	/**
	 * Returns the paint used to draw the chart border (if visible).
	 *
	 * @return The border paint.
	 *
	 * @see #setBorderPaint(Paint)
	 */
	public Paint getBorderPaint() {
		return this.borderPaint;
	}

	/**
	 * Returns the stroke used to draw the chart border (if visible).
	 *
	 * @return The border stroke.
	 *
	 * @see #setBorderStroke(Stroke)
	 */
	public Stroke getBorderStroke() {
		return this.borderStroke;
	}

	protected EntityCollection drawTitle(Title t, Graphics2D g2, Rectangle2D area, boolean entities) {

		ParamChecks.nullNotPermitted(t, "t");
		ParamChecks.nullNotPermitted(area, "area");
		Rectangle2D titleArea;
		RectangleEdge position = t.getPosition();
		double ww = area.getWidth();
		if (ww <= 0.0) {
			return null;
		}
		double hh = area.getHeight();
		if (hh <= 0.0) {
			return null;
		}
		RectangleConstraint constraint = new RectangleConstraint(ww, new Range(0.0, ww), LengthConstraintType.RANGE, hh,
				new Range(0.0, hh), LengthConstraintType.RANGE);
		Object retValue = null;
		BlockParams p = new BlockParams();
		p.setGenerateEntities(entities);
		if (position == RectangleEdge.TOP) {
			Size2D size = t.arrange(g2, constraint);
			titleArea = createAlignedRectangle2D(size, area, t.getHorizontalAlignment(), VerticalAlignment.TOP);
			retValue = t.draw(g2, titleArea, p);
			area.setRect(area.getX(), Math.min(area.getY() + size.height, area.getMaxY()), area.getWidth(),
					Math.max(area.getHeight() - size.height, 0));
		} else if (position == RectangleEdge.BOTTOM) {
			Size2D size = t.arrange(g2, constraint);
			titleArea = createAlignedRectangle2D(size, area, t.getHorizontalAlignment(), VerticalAlignment.BOTTOM);
			retValue = t.draw(g2, titleArea, p);
			area.setRect(area.getX(), area.getY(), area.getWidth(), area.getHeight() - size.height);
		} else if (position == RectangleEdge.RIGHT) {
			Size2D size = t.arrange(g2, constraint);
			titleArea = createAlignedRectangle2D(size, area, HorizontalAlignment.RIGHT, t.getVerticalAlignment());
			retValue = t.draw(g2, titleArea, p);
			area.setRect(area.getX(), area.getY(), area.getWidth() - size.width, area.getHeight());
		}

		else if (position == RectangleEdge.LEFT) {
			Size2D size = t.arrange(g2, constraint);
			titleArea = createAlignedRectangle2D(size, area, HorizontalAlignment.LEFT, t.getVerticalAlignment());
			retValue = t.draw(g2, titleArea, p);
			area.setRect(area.getX() + size.width, area.getY(), area.getWidth() - size.width, area.getHeight());
		} else {
			throw new RuntimeException("Unrecognised title position.");
		}
		EntityCollection result = null;
		if (retValue instanceof EntityBlockResult) {
			EntityBlockResult ebr = (EntityBlockResult) retValue;
			result = ebr.getEntityCollection();
		}
		return result;
	}

	private Rectangle2D createAlignedRectangle2D(Size2D dimensions, Rectangle2D frame, HorizontalAlignment hAlign,
			VerticalAlignment vAlign) {
		double x = Double.NaN;
		double y = Double.NaN;
		if (hAlign == HorizontalAlignment.LEFT) {
			x = frame.getX();
		} else if (hAlign == HorizontalAlignment.CENTER) {
			x = frame.getCenterX() - (dimensions.width / 2.0);
		} else if (hAlign == HorizontalAlignment.RIGHT) {
			x = frame.getMaxX() - dimensions.width;
		}
		if (vAlign == VerticalAlignment.TOP) {
			y = frame.getY();
		} else if (vAlign == VerticalAlignment.CENTER) {
			y = frame.getCenterY() - (dimensions.height / 2.0);
		} else if (vAlign == VerticalAlignment.BOTTOM) {
			y = frame.getMaxY() - dimensions.height;
		}

		return new Rectangle2D.Double(x, y, dimensions.width, dimensions.height);
	}

	public void drawColumnCahrt(ColumnStackedChart chart, Graphics2D graphic) {
		drawTitle(chart.getTitle(), graphic, chart.getXfrm());
	}

	public static void drawTitle(com.cisco.pptx_to_jpg_converter.xslfchart.style.Title title, Graphics2D graphic,
			XFrame xframe) {

		Rectangle2D titleArea;
		double ww = xframe.getWidth();
		Object retValue = null;
		BlockParams p = new BlockParams();

		TextBlock content;
		Font font = new Font(title.getTitleFontStyle(), Font.PLAIN, Integer.valueOf(title.getTitleFontSize() / 100));
		Paint paint = Color.black;
		String text = title.getTitleContentText();
		int maximumLinesToDisplay = Integer.MAX_VALUE;
		HorizontalAlignment textAlignment = HorizontalAlignment.CENTER;
		graphic.getFontMetrics(font).stringWidth(text);

		RectangleEdge position = RectangleEdge.TOP;
		if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
			float maxWidth = Float.MAX_VALUE;
			graphic.setFont(font);
			content = TextUtilities.createTextBlock(text, font, paint, maxWidth, maximumLinesToDisplay,
					new G2TextMeasurer(graphic));
			content.setLineAlignment(textAlignment);
			Size2D contentSize = content.calculateDimensions(graphic);
		}
	}

}
