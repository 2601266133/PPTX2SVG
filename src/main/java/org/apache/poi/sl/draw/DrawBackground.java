package org.apache.poi.sl.draw;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import org.apache.poi.sl.usermodel.Background;
import org.apache.poi.sl.usermodel.PlaceableShape;
import org.apache.poi.sl.usermodel.ShapeContainer;
import org.apache.poi.sl.usermodel.Sheet;

public class DrawBackground extends DrawShape {
	public DrawBackground(Background<?, ?> shape) {
		super(shape);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void draw(Graphics2D graphics) {
		Dimension pg = shape.getSheet().getSlideShow().getPageSize();
		final Rectangle2D anchor = new Rectangle2D.Double(0, 0, pg.getWidth(), pg.getHeight());

		PlaceableShape<?, ?> ps = new PlaceableShape() {
			@Override
			public ShapeContainer<?, ?> getParent() {
				return null;
			}

			@Override
			public Rectangle2D getAnchor() {
				return anchor;
			}

			@Override
			public void setAnchor(Rectangle2D newAnchor) {
			}

			@Override
			public double getRotation() {
				return 0;
			}

			@Override
			public void setRotation(double theta) {
			}

			@Override
			public void setFlipHorizontal(boolean flip) {
			}

			@Override
			public void setFlipVertical(boolean flip) {
			}

			@Override
			public boolean getFlipHorizontal() {
				return false;
			}

			@Override
			public boolean getFlipVertical() {
				return false;
			}

			@Override
			public Sheet<?, ?> getSheet() {
				return shape.getSheet();
			}
		};

		DrawFactory drawFact = DrawFactory.getInstance(graphics);
		DrawPaint dp = drawFact.getPaint(ps);
		Paint fill = dp.getPaint(graphics, getShape().getFillStyle().getPaint());
		Rectangle2D anchor2 = getAnchor(graphics, anchor);

		if (fill != null) {
			graphics.setRenderingHint(Drawable.GRADIENT_SHAPE, anchor);
			graphics.setPaint(fill);
			graphics.fill(anchor2);
		}
	}

	@Override
	protected Background<?, ?> getShape() {
		return (Background<?, ?>) shape;
	}
}
