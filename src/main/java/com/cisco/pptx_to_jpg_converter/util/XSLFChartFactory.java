package com.cisco.pptx_to_jpg_converter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFGraphicFrame;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDPt;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTManualLayout;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTitle;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBodyProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;

import com.cisco.pptx_to_jpg_converter.xslfchart.ColumnClusteredChart;
import com.cisco.pptx_to_jpg_converter.xslfchart.FillType.SolidFill;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.BodyProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.CategoryAxisData;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ManualLayout;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.Series;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextParagraphs;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ValueAxisData;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.color.SchemeColor;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.DataPoint;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;

public class XSLFChartFactory {

	public static ColumnClusteredChart createColumnClusteredChart(XSLFChart chart, XSLFSlide slide) {
		ColumnClusteredChart columnChart = new ColumnClusteredChart();

		XFrame xfrm = new XFrame();
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				xfrm.setOffX(shape.getAnchor().getX());
				xfrm.setOffY(shape.getAnchor().getWidth());
				xfrm.setHeight(shape.getAnchor().getHeight());
				xfrm.setWidth(shape.getAnchor().getWidth());
			}
		}
		columnChart.setXfrm(xfrm);

		Title title = new Title();
		CTTitle ctTitle = chart.getCTChart().getTitle();
		if (chart.getCTChart().getTitle().getLayout() != null) {
			ManualLayout manualLayout = new ManualLayout();
			CTManualLayout ctLayout = ctTitle.getLayout().getManualLayout();
			manualLayout.setManualLayout(ctLayout.isSetLayoutTarget());
			manualLayout.setxMode(ctLayout.getXMode().getVal().toString());
			manualLayout.setyMode(ctLayout.getYMode().getVal().toString());
			manualLayout.setX(ctLayout.getX().getVal());
			manualLayout.setY(ctLayout.getY().getVal());
			manualLayout.setH(ctLayout.getH().getVal());
			manualLayout.setW(ctLayout.getW().getVal());
			title.setManualLayout(manualLayout);
		}

		title.setOverlay(ctTitle.getOverlay().getVal());

		title.setSpPr(getShapeProperties(ctTitle.getSpPr()));

		TextProperties txPr = new TextProperties();
		CTTextBody ctTextBody = ctTitle.getTxPr();

		BodyProperties bodyPr = new BodyProperties();
		CTTextBodyProperties ctTextBodyPr = ctTextBody.getBodyPr();
		bodyPr.setAnchor(ctTextBodyPr.getAnchor().toString());
		bodyPr.setAnchorCtr(ctTextBodyPr.getAnchorCtr());
		bodyPr.setRot(ctTextBodyPr.getRot());
		bodyPr.setSpcFirstLastPara(ctTextBodyPr.getSpcFirstLastPara());
		bodyPr.setVert(ctTextBodyPr.getVert().toString());
		bodyPr.setVertOverflow(ctTextBodyPr.getVertOverflow().toString());
		bodyPr.setWrap(ctTextBodyPr.getWrap().toString());
		TextParagraphs p = new TextParagraphs();
		CTTextCharacterProperties ctTextCharacterPr = ctTextBody.getPList().get(0).getPPr().getDefRPr();
		p.setB(ctTextCharacterPr.getB());
		p.setBaseline(ctTextCharacterPr.getBaseline());

		txPr.setBodyPr(bodyPr);
		txPr.setP(p);

		title.setTxPr(txPr);

		PlotArea plotArea = new PlotArea();
		CTPlotArea ctPlotArea = chart.getCTChart().getPlotArea();
		List<CTBarChart> barList = ctPlotArea.getBarChartList();
		if (barList.size() > 0) {
			for (CTBarChart bar : barList) {
				if (bar.getBarDir() != null) {
					plotArea.setBarDir(bar.getBarDir().getVal().toString());
				}
				plotArea.setGrouping(bar.getGrouping().getVal().toString());
				plotArea.setVaryColors(bar.getVaryColors().getVal());
				plotArea.setGapWidth(bar.getGapWidth().getVal());
				plotArea.setOverlap(bar.getOverlap().toString());

				List<Series> serList = new ArrayList<Series>();
				for (CTBarSer ser : bar.getSerList()) {
					Series series = new Series();
					series.setId(ser.getIdx().getVal());
					series.setOrder(ser.getOrder().getVal());
					series.setSpPr(getShapeProperties(ser.getSpPr()));
					series.setInvertIfNegative(ser.getInvertIfNegative().getVal());
					for (CTDPt ctdPt : ser.getDPtList()) {
						DataPoint dPt = new DataPoint();
						dPt.setId(ctdPt.getIdx().getVal());
						dPt.setInvertIfNegative(ctdPt.getInvertIfNegative().getVal());
						dPt.setBubble3D(ctdPt.getBubble3D().getVal());
						dPt.setSpPr(getShapeProperties(ctdPt.getSpPr()));
						series.setdPt(dPt);
					}
					List<CTStrVal> serTxList = new ArrayList<CTStrVal>();
					List<CTStrVal> strCatList = new ArrayList<CTStrVal>();
					List<CTNumVal> numValList = new ArrayList<CTNumVal>();
					CategoryAxisData cat = new CategoryAxisData();
					cat.setF(ser.getCat().getStrRef().getF());
					cat.setpCount(ser.getCat().getStrRef().getStrCache().getPtCount().getVal());
					Map<Long, String> catidxVMap = new HashMap<Long, String>();
					ValueAxisData val = new ValueAxisData();
					val.setF(ser.getVal().getNumRef().getF());
					val.setFormatCode(ser.getVal().getNumRef().getNumCache().getFormatCode());
					val.setpCount(ser.getVal().getNumRef().getNumCache().getPtCount().getVal());
					Map<Long, String> validxVMap = new HashMap<Long, String>();
					if (ser.getTx() != null) {
						serTxList = ser.getTx().getStrRef().getStrCache().getPtList();
						strCatList = ser.getCat().getStrRef().getStrCache().getPtList();
						numValList = ser.getVal().getNumRef().getNumCache().getPtList();
						for (CTStrVal ctstrVal : strCatList) {
							catidxVMap.put(ctstrVal.getIdx(), ctstrVal.getV());
						}
						cat.setIdxVMap(catidxVMap);
						for (CTNumVal ctnumVal : numValList) {
							validxVMap.put(ctnumVal.getIdx(), ctnumVal.getV());
						}
						val.setIdxVMap(validxVMap);
					}

					series.setCat(cat);
					series.setVal(val);
					serList.add(series);
				}
			}

			ctPlotArea.getCatAxList();
		}
		return columnChart;
	}

	private static ShapeProperties getShapeProperties(CTShapeProperties shapePr) {
		ShapeProperties spPr = new ShapeProperties();
		CTShapeProperties ctShapePr = shapePr;
		if (ctShapePr.getNoFill() != null) {
			String fillType = "NoFill";
		} else if (ctShapePr.getSolidFill() != null) {
			String fillType = "SolidFill";
			SolidFill solidFill = new SolidFill();
			SchemeColor schemeClr = new SchemeColor();
			schemeClr.setVal(ctShapePr.getSolidFill().getSchemeClr().getVal().toString());
			schemeClr.setLumMod(ctShapePr.getSolidFill().getSchemeClr().getLumModList().get(0).getVal());
			schemeClr.setLumOff(ctShapePr.getSolidFill().getSchemeClr().getLumOffList().get(0).getVal());
			solidFill.setSchemeClr(schemeClr);
			spPr.setSolidFill(solidFill);
		} else if (ctShapePr.getGradFill() != null) {
			String fillType = "GradinetFill";
		} else if (ctShapePr.getPattFill() != null) {
			String fillType = "PatternFill";
		}

		return spPr;
	}

}
