package com.cisco.pptx_to_jpg_converter.util;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.openxmlformats.schemas.presentationml.x2006.main.CTBackground;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;

public class PPTXToSVGConverter extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(PPTXToSVGConverter.class);

	private List<Map<String, SVGGraphics2D>> convertedSVGList = new ArrayList<Map<String, SVGGraphics2D>>();

	public PPTXToSVGConverter(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
		super(inStream, targetImageFileDir, imageFormatNameString);
	}

	@Override
	public void convert() throws Exception {
		converReturnResult = true;// 是否全部转成功
		List<String> imgNamesList = new ArrayList<String>();// PPT转成图片后所有名称集合
		List<File> imagesFileList = new ArrayList<File>();// 所有图片的file
		List<ImageInformation> imagesInfoList = new ArrayList<ImageInformation>();
		XMLSlideShow oneSlideShow = null;
		File dest = null;
		String uuID = null;
		try {

			oneSlideShow = new XMLSlideShow(OPCPackage.open(inStream));

			// 获取PPT每页的尺寸大小（宽和高度）
			Dimension onePPTPageSize = oneSlideShow.getPageSize();
			// 获取PPT文件中的所有PPT页面，并转换为一张张播放片
			List<XSLFSlide> pptPageXSLFSLiseList = oneSlideShow.getSlides();

			String xmlFontFormat = "<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"
					+ "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> "
					+ "<a:latin typeface=\"+mj-ea\"/> " + "</a:rPr>" + "</xml-fragment>";
			uuID = UUID.randomUUID().toString();
			logger.info("parent image direct: " + targetImageFileDir);
			targetImageFileDir = targetImageFileDir + uuID + File.separator;
			logger.info("real image direct: " + targetImageFileDir);
			for (int i = 0; i < pptPageXSLFSLiseList.size(); i++) {

				// Create initial SVG DOM
				// DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
				// Document doc = domImpl.createDocument("http://www.w3.org/2000/svg", "svg",
				// null);
				// Use Batik SVG Graphics2D driver
				// SVGGraphics2D graphics = new SVGGraphics2D(doc);

				// use jfreeSVG
				SVGGraphics2D graphics = new SVGGraphics2D(onePPTPageSize.width, onePPTPageSize.height);

				// graphics.setSVGCanvasSize(onePPTPageSize);

				String title = pptPageXSLFSLiseList.get(i).getTitle();
				System.out.println("Rendering slide " + (i + 1) + (title == null ? "" : ": " + title));
				// draw stuff. All the heavy-lifting happens here
				pptPageXSLFSLiseList.get(i).toString();
				CTBackground xmlBg = (CTBackground) pptPageXSLFSLiseList.get(i).getBackground().getXmlObject();
				xmlBg.toString();
				// logger.info("Background color is: "
				// + pptPageXSLFSLiseList.get(i).getBackground().getFillColor().toString());
				// graphics.setBackground(new Color(R, G, B));
				graphics.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width, onePPTPageSize.height));
				pptPageXSLFSLiseList.get(i).draw(graphics);

				// 当ppt中有chart，手动画入
				for (POIXMLDocumentPart part : pptPageXSLFSLiseList.get(i).getRelations()) {
					if (part instanceof XSLFChart) {
						XSLFChart chart = (XSLFChart) part;
						ChartUtils.setChart(chart, graphics, pptPageXSLFSLiseList.get(i));
						// DrawChart chartDraw = new DrawChart(
						// new XSLFChartShape(chart.getCTChart(), pptPageXSLFSLiseList.get(i)));
						// chartDraw.draw(graphics);
					}
				}

				// save the result.
				// DOMSource domSource = new DOMSource(graphics.getRoot());

				String imgName = (i + 1) + "_" + UUID.randomUUID().toString() + "." + imageFormatNameString;
				imgNamesList.add(imgName);// 将图片名称添加的集合中

				ImageInformation imageInfo = new ImageInformation();
				imageInfo.setImageName(imgName);
				imageInfo.setImagePath(targetImageFileDir.replace("\\", "/"));
				imageInfo.setCreatedBy("jacSong2");
				imagesInfoList.add(imageInfo);

				dest = new File(targetImageFileDir + imgName);
				// 检测是否存在目录
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				imagesFileList.add(dest);
				Map<String, SVGGraphics2D> svgMap = new HashMap<String, SVGGraphics2D>();
				svgMap.put(targetImageFileDir + imgName, graphics);
				convertedSVGList.add(svgMap);

				// 将转换后的各个图片文件保存带指定的目录中
				// ImageIO.write(oneBufferedImage, imageFormatNameString, dest);

			}
		}
		// catch (XmlException e) {
		// e.printStackTrace();
		// converReturnResult = false;
		// }
		catch (IOException e) {
			e.printStackTrace();
			converReturnResult = false;
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			converReturnResult = false;
			if (dest.getParentFile().exists()) {
				dest.getParentFile().delete();
			}
			throw new Exception(e);
		} finally {

			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			resultsMap.put("converReturnResult", converReturnResult);
			resultsMap.put("imgNames", imgNamesList);
			resultsMap.put("imageFiles", imagesFileList);
			resultsMap.put("imagesInfo", imagesInfoList);
			resultsMap.put("path", targetImageFileDir.replace("\\", "/"));
			resultsMap.put("uuID", uuID);
		}
	}

	@Override
	public void writeImages() throws IOException {

		// use jfreeSVG
		for (Map<String, SVGGraphics2D> svgInfo : convertedSVGList) {
			for (Entry<String, SVGGraphics2D> svg : svgInfo.entrySet()) {
				File f = new File(svg.getKey());
				SVGUtils.writeToSVG(f, svg.getValue().getSVGElement());
			}
		}

		// use batik
		// for (Map<String, DOMSource> svgInfo : convertedSVGList) {
		// for (Entry<String, DOMSource> svg : svgInfo.entrySet()) {
		// OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new
		// File(svg.getKey())), "UTF-8");
		// StreamResult streamResult = new StreamResult(out);
		// TransformerFactory tf = TransformerFactory.newInstance();
		// Transformer serializer;
		// try {
		// serializer = tf.newTransformer();
		// serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		// serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		// try {
		// serializer.transform(svg.getValue(), streamResult);
		// } catch (TransformerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// out.flush();
		// out.close();
		// } catch (TransformerConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// }

	}

}
