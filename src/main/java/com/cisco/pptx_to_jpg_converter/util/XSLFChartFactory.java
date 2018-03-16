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
import org.openxmlformats.schemas.drawingml.x2006.chart.CTCatAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDPt;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLegend;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTManualLayout;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrVal;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTitle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBodyProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;

import com.cisco.pptx_to_jpg_converter.xslfchart.ColumnStackedChart;
import com.cisco.pptx_to_jpg_converter.xslfchart.XSLFChartConstants;
import com.cisco.pptx_to_jpg_converter.xslfchart.FillType.SolidFill;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.BodyProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.CategoryAxisData;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ChartText;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ComplexScriptFont;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.DefaultTextRunProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.EastAsianFont;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.LatinFont;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.LineSpace;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ManualLayout;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.RichText;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.Series;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.SeriesText;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ShapeProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.SpacingPercent;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.SpacingPoints;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextParagraphProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextParagraphs;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextRun;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.TextRunProperties;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.ValueAxisData;
import com.cisco.pptx_to_jpg_converter.xslfchart.attribute.XFrame;
import com.cisco.pptx_to_jpg_converter.xslfchart.color.SchemeColor;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.CategoryAxis;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.DataPoint;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Legend;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.PlotArea;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.Title;
import com.cisco.pptx_to_jpg_converter.xslfchart.style.ValueAxis;

public class XSLFChartFactory {

	public static ColumnStackedChart createColumnStackedChart(XSLFChart chart, XSLFSlide slide) {
		ColumnStackedChart columnChart = new ColumnStackedChart();

		XFrame xfrm = new XFrame();
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFGraphicFrame) {
				xfrm.setOffX(shape.getAnchor().getX());
				xfrm.setOffY(shape.getAnchor().getY());
				xfrm.setHeight(shape.getAnchor().getHeight());
				xfrm.setWidth(shape.getAnchor().getWidth());
			}
		}
		columnChart.setXfrm(xfrm);

		CTTitle ctTitle = chart.getCTChart().getTitle();
		if (ctTitle != null) {
			columnChart.setTitle(getTitle(ctTitle));
		}

		PlotArea plotArea = new PlotArea();
		CTPlotArea ctPlotArea = chart.getCTChart().getPlotArea();
		if (ctPlotArea.getLayout().getManualLayout() != null) {
			plotArea.setManualLayout(getManualLayout(ctPlotArea.getLayout().getManualLayout()));
		} else {
			ManualLayout manualLayout = new ManualLayout();
			manualLayout.setX(XSLFChartConstants.COLUMN_CLUSTERED_PLOT_DEFAULT_LAYOUT_X);
			manualLayout.setY(XSLFChartConstants.COLUMN_CLUSTERED_PLOT_DEFAULT_LAYOUT_Y);
			manualLayout.setH(XSLFChartConstants.COLUMN_CLUSTERED_PLOT_DEFAULT_LAYOUT_H);
			manualLayout.setW(XSLFChartConstants.COLUMN_CLUSTERED_PLOT_DEFAULT_LAYOUT_W);
			plotArea.setManualLayout(manualLayout);
		}

		plotArea.setCatAxList(getPlotCategoryAxis(ctPlotArea));
		plotArea.setValAxList(getPlotValueAxis(ctPlotArea));

		List<CTBarChart> barList = ctPlotArea.getBarChartList();
		if (barList.size() > 0) {
			for (CTBarChart bar : barList) {
				if (bar.getBarDir() != null) {
					plotArea.setBarDir(bar.getBarDir().getVal().toString());
				}
				plotArea.setGrouping(bar.getGrouping().getVal().toString());
				plotArea.setVaryColors(bar.getVaryColors().getVal());
				plotArea.setGapWidth(bar.getGapWidth().getVal());
				if (bar.getOverlap() != null) {
					plotArea.setOverlap(bar.getOverlap().xgetVal().getIntValue());
				}

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
					SeriesText tx = new SeriesText();
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
						for (CTStrVal ctstr : serTxList) {
							tx.setId(ctstr.xgetIdx().getStringValue());
							tx.setV(ctstr.getV());
						}
						series.setTx(tx);
					}

					series.setCat(cat);
					series.setVal(val);
					serList.add(series);
				}
				plotArea.setSerList(serList);
			}
		}
		columnChart.setPlotArea(plotArea);

		Legend legend = new Legend();
		CTLegend ctLegend = chart.getCTChart().getLegend();
		if (ctLegend != null && ctLegend.getLayout() != null && ctLegend.getLayout().getManualLayout() != null) {
			legend.setManualLayout(getManualLayout(ctLegend.getLayout().getManualLayout()));
			legend.setSpPr(getShapeProperties(ctLegend.getSpPr()));
			legend.setTxPr(getTextProperties(ctLegend.getTxPr()));
		}
		columnChart.setLegend(legend);

		return columnChart;
	}

	private static ManualLayout getManualLayout(CTManualLayout ctLayout) {
		ManualLayout manualLayout = new ManualLayout();
		manualLayout.setLayoutTarget(
				ctLayout.getLayoutTarget() == null ? null : ctLayout.getLayoutTarget().getVal().toString());
		manualLayout.setManualLayout(ctLayout.isSetLayoutTarget());
		manualLayout.setxMode(ctLayout.getXMode().getVal().toString());
		manualLayout.setyMode(ctLayout.getYMode().getVal().toString());
		manualLayout.setX(ctLayout.getX() == null ? 0.0 : ctLayout.getX().getVal());
		manualLayout.setY(ctLayout.getY() == null ? 0.0 : ctLayout.getY().getVal());
		manualLayout.setH(ctLayout.getH() == null ? 0.0 : ctLayout.getH().getVal());
		manualLayout.setW(ctLayout.getW() == null ? 0.0 : ctLayout.getW().getVal());
		return manualLayout;
	}

	private static ShapeProperties getShapeProperties(CTShapeProperties shapePr) {
		ShapeProperties spPr = new ShapeProperties();
		CTShapeProperties ctShapePr = shapePr;
		if (ctShapePr != null) {
			if (ctShapePr.getNoFill() != null) {
				String fillType = "NoFill";
			} else if (ctShapePr.getSolidFill() != null) {
				String fillType = "SolidFill";
				SolidFill solidFill = new SolidFill();
				SchemeColor schemeClr = new SchemeColor();
				schemeClr.setVal(ctShapePr.getSolidFill().getSchemeClr().getVal().toString());
				if (!ctShapePr.getSolidFill().getSchemeClr().getLumModList().isEmpty()) {
					schemeClr.setLumMod(ctShapePr.getSolidFill().getSchemeClr().getLumModList().get(0).getVal());
					schemeClr.setLumOff(ctShapePr.getSolidFill().getSchemeClr().getLumOffList().get(0).getVal());
				}
				solidFill.setSchemeClr(schemeClr);
				spPr.setSolidFill(solidFill);
			} else if (ctShapePr.getGradFill() != null) {
				String fillType = "GradinetFill";
			} else if (ctShapePr.getPattFill() != null) {
				String fillType = "PatternFill";
			}
		}

		return spPr;
	}

	// c:txPr
	private static TextProperties getTextProperties(CTTextBody ctTextBody) {
		TextProperties txPr = new TextProperties();
		BodyProperties bodyPr = new BodyProperties();
		if (ctTextBody != null) {
			CTTextBodyProperties ctTextBodyPr = ctTextBody.getBodyPr();
			bodyPr.setAnchor(ctTextBodyPr.getAnchor().toString());
			bodyPr.setAnchorCtr(ctTextBodyPr.getAnchorCtr());
			bodyPr.setRot(ctTextBodyPr.getRot());
			bodyPr.setSpcFirstLastPara(ctTextBodyPr.getSpcFirstLastPara());
			bodyPr.setVert(ctTextBodyPr.getVert() == null ? null : ctTextBodyPr.getVert().toString());
			bodyPr.setVertOverflow(ctTextBodyPr.getVertOverflow().toString());
			bodyPr.setWrap(ctTextBodyPr.getWrap().toString());
			txPr.setBodyPr(bodyPr);
			TextParagraphs p = new TextParagraphs();
			TextParagraphProperties pPr = new TextParagraphProperties();
			DefaultTextRunProperties defRPr = new DefaultTextRunProperties();
			LineSpace lnSpc = new LineSpace();
			CTTextCharacterProperties ctTextCharacterPr = ctTextBody.getPList().get(0).getPPr().getDefRPr();
			defRPr.setSz(ctTextCharacterPr.getSz());

			ComplexScriptFont cs = new ComplexScriptFont();
			cs.setTypeface(ctTextCharacterPr.getCs() == null ? null : ctTextCharacterPr.getCs().getTypeface());
			EastAsianFont ea = new EastAsianFont();
			ea.setTypeface(ctTextCharacterPr.getEa() == null ? null : ctTextCharacterPr.getEa().getTypeface());
			LatinFont latin = new LatinFont();
			latin.setTypeface(ctTextCharacterPr.getLatin() == null ? null : ctTextCharacterPr.getLatin().getTypeface());
			defRPr.setEa(ea);
			defRPr.setCs(cs);
			defRPr.setLatin(latin);
			if (ctTextBody.getPList().get(0).getPPr().getLnSpc() != null) {
				SpacingPercent spcPct = new SpacingPercent();
				spcPct.setVal(ctTextBody.getPList().get(0).getPPr().getLnSpc().getSpcPct() == null ? 0
						: ctTextBody.getPList().get(0).getPPr().getLnSpc().getSpcPct().getVal());
				lnSpc.setSpcPct(spcPct);
				SpacingPoints spcPts = new SpacingPoints();
				spcPts.setVal(ctTextBody.getPList().get(0).getPPr().getLnSpc().getSpcPts() == null ? 0
						: ctTextBody.getPList().get(0).getPPr().getLnSpc().getSpcPts().getVal());
				lnSpc.setSpcPts(spcPts);
				pPr.setLnSpc(lnSpc);
			} else {
				SpacingPercent spcPct = new SpacingPercent();
				spcPct.setVal(100000);
				lnSpc.setSpcPct(spcPct);
				SpacingPoints spcPts = new SpacingPoints();
				spcPts.setVal(0);
				lnSpc.setSpcPts(spcPts);
				pPr.setLnSpc(lnSpc);
			}
			pPr.setDefRPr(defRPr);
			p.setpPr(pPr);
			txPr.setP(p);
		}
		return txPr;
	}

	// c:tx
	private static ChartText getChartText(CTTx ctTx) {
		ChartText tx = new ChartText();

		RichText rich = new RichText();
		TextParagraphs p = new TextParagraphs();
		List<TextRun> rList = new ArrayList<TextRun>();
		for (CTRegularTextRun tRun : ctTx.getRich().getPList().get(0).getRList()) {
			TextRun r = new TextRun();
			TextRunProperties rPr = new TextRunProperties();
			rPr.setSz(tRun.getRPr().getSz());
			ComplexScriptFont cs = new ComplexScriptFont();
			cs.setTypeface(tRun.getRPr().getCs() == null ? null : tRun.getRPr().getCs().getTypeface());
			EastAsianFont ea = new EastAsianFont();
			ea.setTypeface(tRun.getRPr().getEa() == null ? null : tRun.getRPr().getEa().getTypeface());
			LatinFont latin = new LatinFont();
			latin.setTypeface(tRun.getRPr().getLatin() == null ? null : tRun.getRPr().getLatin().getTypeface());
			rPr.setEa(ea);
			rPr.setCs(cs);
			rPr.setLatin(latin);
			r.setrPr(rPr);
			r.setT(tRun.getT());
			rList.add(r);
		}
		p.setR(rList);
		TextParagraphProperties pPr = new TextParagraphProperties();
		DefaultTextRunProperties defRPr = new DefaultTextRunProperties();
		defRPr.setSz(ctTx.getRich().getPList().get(0).getPPr().getDefRPr().getSz());
		pPr.setDefRPr(defRPr);
		p.setpPr(pPr);
		rich.setP(p);
		tx.setRich(rich);
		return tx;
	}

	// c:title
	private static Title getTitle(CTTitle ctTitle) {
		Title title = new Title();
		if (ctTitle.getLayout() != null && ctTitle.getLayout().getManualLayout() != null) {
			title.setManualLayout(getManualLayout(ctTitle.getLayout().getManualLayout()));
		}

		title.setOverlay(ctTitle.getOverlay().getVal());

		title.setSpPr(getShapeProperties(ctTitle.getSpPr()));

		CTTextBody ctTextBody = ctTitle.getTxPr();
		if (ctTextBody != null) {
			title.setTxPr(getTextProperties(ctTextBody));
		}

		CTTx ctTx = ctTitle.getTx();
		if (ctTx != null) {
			title.setTx(getChartText(ctTx));
		}
		return title;
	}

	private static List<CategoryAxis> getPlotCategoryAxis(CTPlotArea ctPlotArea) {
		List<CategoryAxis> catAxList = new ArrayList<CategoryAxis>();
		for (CTCatAx ctcatAx : ctPlotArea.getCatAxList()) {
			CategoryAxis catAx = new CategoryAxis();
			catAx.setAxId(ctcatAx.getAxId().xgetVal().getStringValue());
			catAx.setSourceLinked(ctcatAx.getNumFmt().getSourceLinked());
			catAx.setFormatCode(ctcatAx.getNumFmt().getFormatCode());
			catAx.setAuto(ctcatAx.getAuto().getVal());
			catAx.setLblAlgn(ctcatAx.getLblAlgn().xgetVal().getStringValue());
			catAx.setLblOffset(ctcatAx.getLblOffset().getVal());
			catAx.setNoMultiLvlLbl(ctcatAx.getNoMultiLvlLbl().getVal());
			catAx.setOrientation(ctcatAx.getScaling().getOrientation().xgetVal().getStringValue());
			catAx.setDelete(ctcatAx.getDelete().getVal());
			catAx.setAxPos(ctcatAx.getAxPos().xgetVal().getStringValue());
			catAx.setMajorTickMark(ctcatAx.getMajorTickMark().xgetVal().getStringValue());
			catAx.setMinorTickMark(ctcatAx.getMinorTickMark().xgetVal().getStringValue());
			catAx.setCrossAxis(ctcatAx.getCrossAx().xgetVal().getStringValue());
			catAx.setCrosssers(ctcatAx.getCrosses().xgetVal().getStringValue());
			catAx.setSpPr(getShapeProperties(ctcatAx.getSpPr()));
			catAx.setTxPr(getTextProperties(ctcatAx.getTxPr()));
			CTTitle cttitle = ctcatAx.getTitle();
			if (cttitle != null) {
				catAx.setTitle(getTitle(cttitle));
			}
			catAxList.add(catAx);
		}
		return catAxList;
	}

	private static List<ValueAxis> getPlotValueAxis(CTPlotArea ctPlotArea) {
		List<ValueAxis> valAxList = new ArrayList<ValueAxis>();
		List<CTValAx> ctvalAxList = ctPlotArea.getValAxList();
		for (CTValAx ctvalAx : ctvalAxList) {
			ValueAxis valAx = new ValueAxis();
			valAx.setAxId(ctvalAx.getAxId().xgetVal().getStringValue());
			valAx.setSourceLinked(ctvalAx.getNumFmt().getSourceLinked());
			valAx.setFormatCode(ctvalAx.getNumFmt().getFormatCode());
			valAx.setOrientation(ctvalAx.getScaling().getOrientation().xgetVal().getStringValue());
			valAx.setDelete(ctvalAx.getDelete().getVal());
			valAx.setAxPos(ctvalAx.getAxPos().xgetVal().getStringValue());
			valAx.setMajorTickMark(ctvalAx.getMajorTickMark().xgetVal().getStringValue());
			valAx.setMinorTickMark(ctvalAx.getMinorTickMark().xgetVal().getStringValue());
			valAx.setCrossAxis(ctvalAx.getCrossAx().xgetVal().getStringValue());
			valAx.setCrosssers(ctvalAx.getCrosses().xgetVal().getStringValue());
			valAx.setSpPr(getShapeProperties(ctvalAx.getSpPr()));
			valAx.setTxPr(getTextProperties(ctvalAx.getTxPr()));
			CTTitle cttitle = ctvalAx.getTitle();
			if (cttitle != null) {
				valAx.setTitle(getTitle(cttitle));
			}
			valAxList.add(valAx);
		}
		return valAxList;
	}

}
