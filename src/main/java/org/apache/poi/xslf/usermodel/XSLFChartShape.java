package org.apache.poi.xslf.usermodel;

import java.awt.geom.Rectangle2D;
import java.util.List;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.sl.draw.geom.CustomGeometry;
import org.apache.poi.sl.draw.geom.Guide;
import org.apache.poi.sl.draw.geom.IAdjustableShape;
import org.apache.poi.sl.usermodel.ChartFrame;
import org.apache.poi.sl.usermodel.FillStyle;
import org.apache.poi.sl.usermodel.LineDecoration;
import org.apache.poi.sl.usermodel.PaintStyle;
import org.apache.poi.sl.usermodel.Shadow;
import org.apache.poi.sl.usermodel.StrokeStyle;
import org.apache.poi.sl.usermodel.StrokeStyle.LineCap;
import org.apache.poi.sl.usermodel.StrokeStyle.LineCompound;
import org.apache.poi.sl.usermodel.StrokeStyle.LineDash;
import org.apache.poi.util.Beta;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.model.PropertyFetcher;
import org.apache.poi.xslf.usermodel.XSLFPropertiesDelegate.XSLFFillProperties;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBaseStyles;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGroupShapeProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTLineProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTLineStyleList;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPoint2D;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.main.CTSchemeColor;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeStyle;
import org.openxmlformats.schemas.drawingml.x2006.main.CTStyleMatrix;
import org.openxmlformats.schemas.drawingml.x2006.main.CTStyleMatrixReference;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTransform2D;
import org.openxmlformats.schemas.drawingml.x2006.main.STCompoundLine;

@Beta
public class XSLFChartShape extends XSLFShape implements ChartFrame<XSLFShape, XSLFTextParagraph>, IAdjustableShape {

	private final static POILogger _logger = POILogFactory.getLogger(XSLFChartShape.class);

	private final List<XSLFShape> _shapes;
	private final CTGroupShapeProperties _grpSpPr;

	public XSLFChartShape(CTChart shape, XSLFSheet sheet) {
		super(shape, sheet);
		_shapes = null;
		_grpSpPr = null;
	}

	@Override
	public Rectangle2D getAnchor() {
		CTTransform2D xfrm = getXfrm(false);
		if (xfrm == null) {
			return null;
		}

		CTPoint2D off = xfrm.getOff();
		double x = Units.toPoints(off.getX());
		double y = Units.toPoints(off.getY());
		CTPositiveSize2D ext = xfrm.getExt();
		double cx = Units.toPoints(ext.getCx());
		double cy = Units.toPoints(ext.getCy());
		return new Rectangle2D.Double(x, y, cx, cy);
	}

	protected CTTransform2D getXfrm(boolean create) {
		PropertyFetcher<CTTransform2D> fetcher = new PropertyFetcher<CTTransform2D>() {
			@Override
			public boolean fetch(XSLFShape shape) {
				XmlObject xo = shape.getShapeProperties();
				if (xo instanceof CTShapeProperties && ((CTShapeProperties) xo).isSetXfrm()) {
					setValue(((CTShapeProperties) xo).getXfrm());
					return true;
				}
				return false;
			}
		};
		fetchShapeProperty(fetcher);

		CTTransform2D xfrm = fetcher.getValue();
		if (!create || xfrm != null) {
			return xfrm;
		} else {
			XmlObject xo = getShapeProperties();
			if (xo instanceof CTShapeProperties) {
				return ((CTShapeProperties) xo).addNewXfrm();
			} else {
				// ... group shapes have their own getXfrm()
				_logger.log(POILogger.WARN, getClass() + " doesn't have xfrm element.");
				return null;
			}
		}
	}

	@Override
	public void setAnchor(Rectangle2D anchor) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRotation(double theta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlipHorizontal(boolean flip) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlipVertical(boolean flip) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getFlipHorizontal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFlipVertical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public XSLFChartShape getChartShape() {
		// TODO Auto-generated method stub
		return null;
	}

	public FillStyle getFillStyle() {
		return new FillStyle() {
			@Override
			public PaintStyle getPaint() {
				return getFillPaint();
			}
		};
	}

	public StrokeStyle getStrokeStyle() {

		return new StrokeStyle() {
			@Override
			public PaintStyle getPaint() {
				return XSLFChartShape.this.getLinePaint();
			}

			@Override
			public LineCap getLineCap() {
				return XSLFChartShape.this.getLineCap();
			}

			@Override
			public LineDash getLineDash() {
				return XSLFChartShape.this.getLineDash();
			}

			@Override
			public double getLineWidth() {
				return XSLFChartShape.this.getLineWidth();
			}

			@Override
			public LineCompound getLineCompound() {
				return XSLFChartShape.this.getLineCompound();
			}

		};

	}

	public LineCap getLineCap() {
		PropertyFetcher<LineCap> fetcher = new PropertyFetcher<LineCap>() {
			@Override
			public boolean fetch(XSLFShape shape) {
				CTLineProperties ln = getLn(shape, false);
				if (ln != null && ln.isSetCap()) {
					setValue(LineCap.fromOoxmlId(ln.getCap().intValue()));
					return true;
				}
				return false;
			}
		};
		fetchShapeProperty(fetcher);

		LineCap cap = fetcher.getValue();
		if (cap == null) {
			CTLineProperties defaultLn = getDefaultLineProperties();
			if (defaultLn != null && defaultLn.isSetCap()) {
				cap = LineCap.fromOoxmlId(defaultLn.getCap().intValue());
			}
		}
		return cap;
	}

	public double getLineWidth() {
		PropertyFetcher<Double> fetcher = new PropertyFetcher<Double>() {
			@Override
			public boolean fetch(XSLFShape shape) {
				CTLineProperties ln = getLn(shape, false);
				if (ln != null) {
					if (ln.isSetNoFill()) {
						setValue(0.);
						return true;
					}

					if (ln.isSetW()) {
						setValue(Units.toPoints(ln.getW()));
						return true;
					}
				}
				return false;
			}
		};
		fetchShapeProperty(fetcher);

		double lineWidth = 0;
		if (fetcher.getValue() == null) {
			CTLineProperties defaultLn = getDefaultLineProperties();
			if (defaultLn != null) {
				if (defaultLn.isSetW()) {
					lineWidth = Units.toPoints(defaultLn.getW());
				}
			}
		} else {
			lineWidth = fetcher.getValue();
		}

		return lineWidth;
	}

	CTLineProperties getDefaultLineProperties() {
		CTShapeStyle style = getSpStyle();
		if (style == null) {
			return null;
		}
		CTStyleMatrixReference lnRef = style.getLnRef();
		if (lnRef == null) {
			return null;
		}
		// 1-based index of a line style within the style matrix
		int idx = (int) lnRef.getIdx();

		XSLFTheme theme = getSheet().getTheme();
		if (theme == null) {
			return null;
		}
		CTBaseStyles styles = theme.getXmlObject().getThemeElements();
		if (styles == null) {
			return null;
		}
		CTStyleMatrix styleMatrix = styles.getFmtScheme();
		if (styleMatrix == null) {
			return null;
		}
		CTLineStyleList lineStyles = styleMatrix.getLnStyleLst();
		if (lineStyles == null || lineStyles.sizeOfLnArray() < idx) {
			return null;
		}

		return lineStyles.getLnArray(idx - 1);
	}

	protected PaintStyle getLinePaint() {
		XSLFSheet sheet = getSheet();
		final XSLFTheme theme = sheet.getTheme();
		final boolean hasPlaceholder = getPlaceholder() != null;
		PropertyFetcher<PaintStyle> fetcher = new PropertyFetcher<PaintStyle>() {
			@Override
			public boolean fetch(XSLFShape shape) {
				CTLineProperties spPr = getLn(shape, false);
				XSLFFillProperties fp = XSLFPropertiesDelegate.getFillDelegate(spPr);

				if (fp != null && fp.isSetNoFill()) {
					setValue(null);
					return true;
				}

				PackagePart pp = shape.getSheet().getPackagePart();
				PaintStyle paint = selectPaint(fp, null, pp, theme, hasPlaceholder);
				if (paint != null) {
					setValue(paint);
					return true;
				}

				CTShapeStyle style = shape.getSpStyle();
				if (style != null) {
					fp = XSLFPropertiesDelegate.getFillDelegate(style.getLnRef());
					paint = selectPaint(fp, null, pp, theme, hasPlaceholder);

					// line color was not found, check if it is defined in the theme
					if (paint == null) {
						paint = getThemePaint(style, pp);
					}
				}

				if (paint != null) {
					setValue(paint);
					return true;
				}

				return false;
			}

			PaintStyle getThemePaint(CTShapeStyle style, PackagePart pp) {
				// get a reference to a line style within the style matrix.
				CTStyleMatrixReference lnRef = style.getLnRef();
				if (lnRef == null) {
					return null;
				}
				int idx = (int) lnRef.getIdx();
				CTSchemeColor phClr = lnRef.getSchemeClr();
				if (idx <= 0) {
					return null;
				}

				CTLineProperties props = theme.getXmlObject().getThemeElements().getFmtScheme().getLnStyleLst()
						.getLnArray(idx - 1);
				XSLFFillProperties fp = XSLFPropertiesDelegate.getFillDelegate(props);
				return selectPaint(fp, phClr, pp, theme, hasPlaceholder);
			}
		};
		fetchShapeProperty(fetcher);

		return fetcher.getValue();
	}

	private static CTLineProperties getLn(XSLFShape shape, boolean create) {
		XmlObject pr = shape.getShapeProperties();
		if (!(pr instanceof CTShapeProperties)) {
			_logger.log(POILogger.WARN, shape.getClass() + " doesn't have line properties");
			return null;
		}

		CTShapeProperties spr = (CTShapeProperties) pr;
		return (spr.isSetLn() || !create) ? spr.getLn() : spr.addNewLn();
	}

	public LineCompound getLineCompound() {
		PropertyFetcher<Integer> fetcher = new PropertyFetcher<Integer>() {
			@Override
			public boolean fetch(XSLFShape shape) {
				CTLineProperties ln = getLn(shape, false);
				if (ln != null) {
					STCompoundLine.Enum stCmpd = ln.getCmpd();
					if (stCmpd != null) {
						setValue(stCmpd.intValue());
						return true;
					}
				}
				return false;
			}
		};
		fetchShapeProperty(fetcher);

		Integer cmpd = fetcher.getValue();
		if (cmpd == null) {
			CTLineProperties defaultLn = getDefaultLineProperties();
			if (defaultLn != null && defaultLn.isSetCmpd()) {
				switch (defaultLn.getCmpd().intValue()) {
				default:
				case STCompoundLine.INT_SNG:
					return LineCompound.SINGLE;
				case STCompoundLine.INT_DBL:
					return LineCompound.DOUBLE;
				case STCompoundLine.INT_THICK_THIN:
					return LineCompound.THICK_THIN;
				case STCompoundLine.INT_THIN_THICK:
					return LineCompound.THIN_THICK;
				case STCompoundLine.INT_TRI:
					return LineCompound.TRIPLE;
				}
			}
		}

		return null;
	}

	public LineDash getLineDash() {

		PropertyFetcher<LineDash> fetcher = new PropertyFetcher<LineDash>() {
			@Override
			public boolean fetch(XSLFShape shape) {
				CTLineProperties ln = getLn(shape, false);
				if (ln == null || !ln.isSetPrstDash()) {
					return false;
				}

				setValue(LineDash.fromOoxmlId(ln.getPrstDash().getVal().intValue()));
				return true;
			}
		};
		fetchShapeProperty(fetcher);

		LineDash dash = fetcher.getValue();
		if (dash == null) {
			CTLineProperties defaultLn = getDefaultLineProperties();
			if (defaultLn != null && defaultLn.isSetPrstDash()) {
				dash = LineDash.fromOoxmlId(defaultLn.getPrstDash().getVal().intValue());
			}
		}
		return dash;
	}

	public LineDecoration getLineDecoration() {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomGeometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guide getAdjustValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Shadow<?, ?> getShadow() {
		// TODO Auto-generated method stub
		return null;
	}

}
