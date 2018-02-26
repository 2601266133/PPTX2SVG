package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

public class BodyProperties {
	boolean anchorCtr;
	String anchor;
	String wrap;
	String vert;
	String vertOverflow;
	boolean spcFirstLastPara;
	int rot;

	public boolean getAnchorCtr() {
		return anchorCtr;
	}

	public void setAnchorCtr(boolean anchorCtr) {
		this.anchorCtr = anchorCtr;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getWrap() {
		return wrap;
	}

	public void setWrap(String wrap) {
		this.wrap = wrap;
	}

	public String getVert() {
		return vert;
	}

	public void setVert(String vert) {
		this.vert = vert;
	}

	public String getVertOverflow() {
		return vertOverflow;
	}

	public void setVertOverflow(String vertOverflow) {
		this.vertOverflow = vertOverflow;
	}

	public boolean getSpcFirstLastPara() {
		return spcFirstLastPara;
	}

	public void setSpcFirstLastPara(boolean spcFirstLastPara) {
		this.spcFirstLastPara = spcFirstLastPara;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

}
