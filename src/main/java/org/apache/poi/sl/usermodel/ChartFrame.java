package org.apache.poi.sl.usermodel;

import org.apache.poi.xslf.usermodel.XSLFChartShape;

public interface ChartFrame<S extends Shape<S, P>, P extends TextParagraph<S, P, ?>>
		extends Shape<S, P>, PlaceableShape<S, P> {

	XSLFChartShape getChartShape();
}