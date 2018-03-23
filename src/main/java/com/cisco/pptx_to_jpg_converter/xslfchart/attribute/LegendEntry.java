package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

/**
 * 5.7.2.95 legendEntry (Legend Entry)
 *
 * delete (Delete) §5.7.2.40 extLst (Chart Extensibility) §5.7.2.64 idx (Index)
 * §5.7.2.84 txPr (Text Properties) §5.7.2.217
 *
 */

public class LegendEntry {

	boolean delete;
	Long idx;
	TextProperties txPr;
	String extLst;

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public double getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public TextProperties getTxPr() {
		return txPr;
	}

	public void setTxPr(TextProperties txPr) {
		this.txPr = txPr;
	}

	public String getExtLst() {
		return extLst;
	}

	public void setExtLst(String extLst) {
		this.extLst = extLst;
	}

}
