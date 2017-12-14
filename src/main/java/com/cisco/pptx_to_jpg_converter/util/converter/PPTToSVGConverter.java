package com.cisco.pptx_to_jpg_converter.util.converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;

public class PPTToSVGConverter extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(PPTToJPGConverter.class);

	private List<Map<String, DOMSource>> convertedSVGList = new ArrayList<Map<String, DOMSource>>();

	public PPTToSVGConverter(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
		super(inStream, targetImageFileDir, imageFormatNameString);
	}

	@Override
	public void convert() {
		converReturnResult = true;// 是否全部转成功
		List<String> imgNamesList = new ArrayList<String>();// PPT转成图片后所有名称集合
		List<File> imagesFileList = new ArrayList<File>();// 所有图片的file
		List<ImageInformation> imagesInfoList = new ArrayList<ImageInformation>();
		HSLFSlideShow oneHSLFSlideShow = null;
		File dest = null;
		String uuID = null;
		try {

			oneHSLFSlideShow = new HSLFSlideShow(inStream);

			// 获取PPT每页的尺寸大小（宽和高度）
			Dimension onePPTPageSize = oneHSLFSlideShow.getPageSize();
			// 获取PPT文件中的所有PPT页面，并转换为一张张播放片
			List<HSLFSlide> pptPageSlideList = oneHSLFSlideShow.getSlides();

			String xmlFontFormat = "<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"
					+ "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> "
					+ "<a:latin typeface=\"+mj-ea\"/> " + "</a:rPr>" + "</xml-fragment>";
			uuID = UUID.randomUUID().toString();
			logger.info("parent image direct: " + targetImageFileDir);
			targetImageFileDir = targetImageFileDir + uuID + File.separator;
			logger.info("real image direct: " + targetImageFileDir);
			for (int i = 0; i < pptPageSlideList.size(); i++) {
				// Create initial SVG DOM
				DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();

				Document doc = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
				// Use Batik SVG Graphics2D driver
				SVGGraphics2D graphics = new SVGGraphics2D(doc);
				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width, onePPTPageSize.height));
				graphics.setSVGCanvasSize(onePPTPageSize);

				String title = pptPageSlideList.get(i).getTitle();
				System.out.println("Rendering slide " + (i + 1) + (title == null ? "" : ": " + title));

				// draw stuff. All the heavy-lifting happens here
				pptPageSlideList.get(i).draw(graphics);

				// save the result.

				DOMSource domSource = new DOMSource(graphics.getRoot());

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
				Map<String, DOMSource> svgMap = new HashMap<String, DOMSource>();
				svgMap.put(targetImageFileDir + imgName, domSource);
				convertedSVGList.add(svgMap);
				// 将转换后的各个图片文件保存带指定的目录中
				// ImageIO.write(oneBufferedImage, imageFormatNameString, dest);

			}
		} catch (IOException e) {
			e.printStackTrace();
			converReturnResult = false;
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

		for (Map<String, DOMSource> svgInfo : convertedSVGList) {
			for (Entry<String, DOMSource> svg : svgInfo.entrySet()) {
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(svg.getKey())), "UTF-8");
				StreamResult streamResult = new StreamResult(out);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer;
				try {
					serializer = tf.newTransformer();
					serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
					serializer.setOutputProperty(OutputKeys.INDENT, "yes");
					try {
						serializer.transform(svg.getValue(), streamResult);
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.flush();
					out.close();
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

}
