package com.cisco.pptx_to_jpg_converter.util;

import java.awt.Color;
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

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;

public class PPTToSVGConverter extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(PPTToJPGConverter.class);

	private List<Map<String, SVGGraphics2D>> convertedSVGList = new ArrayList<Map<String, SVGGraphics2D>>();

	public PPTToSVGConverter(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
		super(inStream, targetImageFileDir, imageFormatNameString);
	}

	@Override
	public void convert() throws Exception {
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

				SVGGraphics2D graphics = new SVGGraphics2D(onePPTPageSize.width, onePPTPageSize.height);
				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width, onePPTPageSize.height));

				String title = pptPageSlideList.get(i).getTitle();
				System.out.println("Rendering slide " + (i + 1) + (title == null ? "" : ": " + title));

				// draw stuff. All the heavy-lifting happens here
				pptPageSlideList.get(i).draw(graphics);

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
		} catch (Exception e) {
			e.printStackTrace();
			converReturnResult = false;
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new Exception(e1.getMessage());
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

		for (Map<String, SVGGraphics2D> svgInfo : convertedSVGList) {
			for (Entry<String, SVGGraphics2D> svg : svgInfo.entrySet()) {
				File f = new File(svg.getKey());
				SVGUtils.writeToSVG(f, svg.getValue().getSVGElement());
			}
		}

	}

}
