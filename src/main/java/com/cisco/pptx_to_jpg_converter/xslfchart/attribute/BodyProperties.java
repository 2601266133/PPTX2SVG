package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 
 * 5.1.5.1.1 bodyPr (Body Properties)
 * 
 * child elements: extLst (Extension List) §5.1.2.1.15 flatTx (No text in 3D
 * scene) §5.1.7.8 noAutofit (No AutoFit) §5.1.5.1.2 normAutofit (Normal
 * AutoFit) §5.1.5.1.3 prstTxWarp (Preset Text Warp) §5.1.11.19 scene3d (3D
 * Scene Properties) §5.1.4.1.26 sp3d (Apply 3D shape properties) §5.1.7.12
 * spAutoFit (Shape AutoFit) §5.1.5.1.4
 * 
 * attributes:
 * 
 *
 */

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
