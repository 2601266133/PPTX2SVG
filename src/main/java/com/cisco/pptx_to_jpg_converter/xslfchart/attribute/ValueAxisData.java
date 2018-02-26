package com.cisco.pptx_to_jpg_converter.xslfchart.attribute;

import java.util.Map;

public class ValueAxisData {

	// numRef
	String f;

	// numCache
	String formatCode;
	long pCount;

	// pt
	String idx;
	String v;

	Map<Long, String> idxVMap;

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getFormatCode() {
		return formatCode;
	}

	public void setFormatCode(String formatCode) {
		this.formatCode = formatCode;
	}

	public long getpCount() {
		return pCount;
	}

	public void setpCount(long pCount) {
		this.pCount = pCount;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public Map<Long, String> getIdxVMap() {
		return idxVMap;
	}

	public void setIdxVMap(Map<Long, String> idxVMap) {
		this.idxVMap = idxVMap;
	}

}
