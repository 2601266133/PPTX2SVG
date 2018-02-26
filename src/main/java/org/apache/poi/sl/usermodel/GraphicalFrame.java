//   Licensed to the Apache Software Foundation (ASF) under one or more

package org.apache.poi.sl.usermodel;

public interface GraphicalFrame<S extends Shape<S, P>, P extends TextParagraph<S, P, ?>>
		extends Shape<S, P>, PlaceableShape<S, P> {

	/**
	 * @return a fallback representation as picture shape
	 */
	PictureShape<S, P> getFallbackPicture();
}
