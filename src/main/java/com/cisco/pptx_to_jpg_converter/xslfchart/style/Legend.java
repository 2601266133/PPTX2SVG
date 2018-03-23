package com.cisco.pptx_to_jpg_converter.xslfchart.style;

import java.util.List;
import java.util.Map;

import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.LegendEntry;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ManualLayout;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;

/**
 * 5.7.2.94 legend (Legend)
 * 
 * extLst (Chart Extensibility) §5.7.2.64 layout (Layout) §5.7.2.88 legendEntry
 * (Legend Entry) §5.7.2.95 legendPos (Legend Position) §5.7.2.96 overlay
 * (Overlay) §5.7.2.133 spPr (Shape Properties) §5.7.2.198 txPr (Text
 * Properties) §5.7.2.217
 * 
 *
 */

public class Legend {

	String legendPos;
	ManualLayout manualLayout;
	ShapeProperties spPr;
	TextProperties txPr;

	List<LegendEntry> entryList;
	Map<Long, LegendEntry> idEntryMap;

	public List<LegendEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<LegendEntry> entryList) {
		this.entryList = entryList;
	}

	public Map<Long, LegendEntry> getIdEntryMap() {
		return idEntryMap;
	}

	public void setIdEntryMap(Map<Long, LegendEntry> idEntryMap) {
		this.idEntryMap = idEntryMap;
	}

	public String getLegendPos() {
		return legendPos;
	}

	public void setLegendPos(String legendPos) {
		this.legendPos = legendPos;
	}

	public ShapeProperties getSpPr() {
		return spPr;
	}

	public void setSpPr(ShapeProperties spPr) {
		this.spPr = spPr;
	}

	public TextProperties getTxPr() {
		return txPr;
	}

	public void setTxPr(TextProperties txPr) {
		this.txPr = txPr;
	}

	public ManualLayout getManualLayout() {
		return manualLayout;
	}

	public void setManualLayout(ManualLayout manualLayout) {
		this.manualLayout = manualLayout;
	}

}
