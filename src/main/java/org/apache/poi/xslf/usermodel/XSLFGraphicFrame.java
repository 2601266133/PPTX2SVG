//*    Licensed to the Apache Software Foundation (ASF) under one or more

package org.apache.poi.xslf.usermodel;

import java.awt.geom.Rectangle2D;

import javax.xml.namespace.QName;

import org.apache.poi.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.sl.usermodel.GraphicalFrame;
import org.apache.poi.sl.usermodel.ShapeType;
import org.apache.poi.util.Beta;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.util.Units;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObjectData;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPoint2D;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTransform2D;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGraphicalObjectFrame;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;

@Beta
public class XSLFGraphicFrame extends XSLFShape implements GraphicalFrame<XSLFShape, XSLFTextParagraph> {
	private static final POILogger LOG = POILogFactory.getLogger(XSLFGraphicFrame.class);

	/* package */ XSLFGraphicFrame(CTGraphicalObjectFrame shape, XSLFSheet sheet) {
		super(shape, sheet);
	}

	public ShapeType getShapeType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Rectangle2D getAnchor() {
		CTTransform2D xfrm = ((CTGraphicalObjectFrame) getXmlObject()).getXfrm();
		CTPoint2D off = xfrm.getOff();
		double x = Units.toPoints(off.getX());
		double y = Units.toPoints(off.getY());
		CTPositiveSize2D ext = xfrm.getExt();
		double cx = Units.toPoints(ext.getCx());
		double cy = Units.toPoints(ext.getCy());
		return new Rectangle2D.Double(x, y, cx, cy);
	}

	@Override
	public void setAnchor(Rectangle2D anchor) {
		CTTransform2D xfrm = ((CTGraphicalObjectFrame) getXmlObject()).getXfrm();
		CTPoint2D off = xfrm.isSetOff() ? xfrm.getOff() : xfrm.addNewOff();
		long x = Units.toEMU(anchor.getX());
		long y = Units.toEMU(anchor.getY());
		off.setX(x);
		off.setY(y);
		CTPositiveSize2D ext = xfrm.isSetExt() ? xfrm.getExt() : xfrm.addNewExt();
		long cx = Units.toEMU(anchor.getWidth());
		long cy = Units.toEMU(anchor.getHeight());
		ext.setCx(cx);
		ext.setCy(cy);
	}

	static XSLFGraphicFrame create(CTGraphicalObjectFrame shape, XSLFSheet sheet) {
		String uri = shape.getGraphic().getGraphicData().getUri();
		if (XSLFTable.TABLE_URI.equals(uri)) {
			return new XSLFTable(shape, sheet);
		} else {
			return new XSLFGraphicFrame(shape, sheet);
		}
	}

	/**
	 * Rotate this shape.
	 * <p>
	 * Positive angles are clockwise (i.e., towards the positive y axis); negative
	 * angles are counter-clockwise (i.e., towards the negative y axis).
	 * </p>
	 *
	 * @param theta
	 *            the rotation angle in degrees.
	 */
	@Override
	public void setRotation(double theta) {
		throw new IllegalArgumentException("Operation not supported");
	}

	/**
	 * Rotation angle in degrees
	 * <p>
	 * Positive angles are clockwise (i.e., towards the positive y axis); negative
	 * angles are counter-clockwise (i.e., towards the negative y axis).
	 * </p>
	 *
	 * @return rotation angle in degrees
	 */
	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public void setFlipHorizontal(boolean flip) {
		throw new IllegalArgumentException("Operation not supported");
	}

	@Override
	public void setFlipVertical(boolean flip) {
		throw new IllegalArgumentException("Operation not supported");
	}

	/**
	 * Whether the shape is horizontally flipped
	 *
	 * @return whether the shape is horizontally flipped
	 */
	@Override
	public boolean getFlipHorizontal() {
		return false;
	}

	@Override
	public boolean getFlipVertical() {
		return false;
	}

	@Override
	void copy(XSLFShape sh) {
		super.copy(sh);

		CTGraphicalObjectData data = ((CTGraphicalObjectFrame) getXmlObject()).getGraphic().getGraphicData();
		String uri = data.getUri();
		if (uri.equals("http://schemas.openxmlformats.org/drawingml/2006/diagram")) {
			copyDiagram(data, (XSLFGraphicFrame) sh);
		} else {
			// TODO support other types of objects

		}
	}

	// TODO should be moved to a sub-class
	private void copyDiagram(CTGraphicalObjectData objData, XSLFGraphicFrame srcShape) {
		String xpath = "declare namespace dgm='http://schemas.openxmlformats.org/drawingml/2006/diagram' $this//dgm:relIds";
		XmlObject[] obj = objData.selectPath(xpath);
		if (obj != null && obj.length == 1) {
			XmlCursor c = obj[0].newCursor();

			XSLFSheet sheet = srcShape.getSheet();
			try {
				String dm = c.getAttributeText(
						new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "dm"));
				PackageRelationship dmRel = sheet.getPackagePart().getRelationship(dm);
				PackagePart dmPart = sheet.getPackagePart().getRelatedPart(dmRel);
				getSheet().importPart(dmRel, dmPart);

				String lo = c.getAttributeText(
						new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "lo"));
				PackageRelationship loRel = sheet.getPackagePart().getRelationship(lo);
				PackagePart loPart = sheet.getPackagePart().getRelatedPart(loRel);
				getSheet().importPart(loRel, loPart);

				String qs = c.getAttributeText(
						new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "qs"));
				PackageRelationship qsRel = sheet.getPackagePart().getRelationship(qs);
				PackagePart qsPart = sheet.getPackagePart().getRelatedPart(qsRel);
				getSheet().importPart(qsRel, qsPart);

				String cs = c.getAttributeText(
						new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "cs"));
				PackageRelationship csRel = sheet.getPackagePart().getRelationship(cs);
				PackagePart csPart = sheet.getPackagePart().getRelatedPart(csRel);
				getSheet().importPart(csRel, csPart);

			} catch (InvalidFormatException e) {
				throw new POIXMLException(e);
			}
			c.dispose();
		}
	}

	@Override
	public XSLFPictureShape getFallbackPicture() {

		String xquery = "declare namespace p='http://schemas.openxmlformats.org/presentationml/2006/main'; "
				+ "declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' "
				+ ".//mc:Fallback/*/p:pic";

		XmlObject xo = selectProperty(XmlObject.class, xquery);
		if (xo == null) {
			return null;
		}

		CTGroupShape gs;
		try {
			gs = CTGroupShape.Factory.parse(xo.newDomNode());
		} catch (XmlException e) {
			LOG.log(POILogger.WARN, "Can't parse fallback picture stream of graphical frame", e);
			return null;
		}

		if (gs.sizeOfPicArray() == 0) {
			return null;
		}

		return new XSLFPictureShape(gs.getPicArray(0), getSheet());
	}

	public void getChart() {
		String xquery = "declare namespace a='http://schemas.openxmlformats.org/drawingml/2006/main'; "
				+ "declare namespace r='http://schemas.openxmlformats.org/officeDocument/2006/relationships'; "
				+ "declare namespace p='http://schemas.openxmlformats.org/presentationml/2006/main' " + ".//a:graphic";
		XmlObject xo = selectProperty(XmlObject.class, xquery);
		CTGroupShape gs;
		try {
			gs = CTGroupShape.Factory.parse(xo.newDomNode());
		} catch (XmlException e) {
			LOG.log(POILogger.WARN, "Can't parse fallback picture stream of graphical frame", e);
		}
	}
}