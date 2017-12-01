package com.cisco.pptx_to_jpg_converter.util.converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;

public class PPTToJPGConverter extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(PPTToJPGConverter.class);

	public PPTToJPGConverter(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
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
				// 这几个循环只要是设置字体为宋体，防止中文乱码，
				List<List<HSLFTextParagraph>> oneTextParagraphs = pptPageSlideList.get(i).getTextParagraphs();
				for (List<HSLFTextParagraph> list : oneTextParagraphs) {
					for (HSLFTextParagraph hslfTextParagraph : list) {
						List<HSLFTextRun> HSLFTextRunList = hslfTextParagraph.getTextRuns();
						for (int j = 0; j < HSLFTextRunList.size(); j++) {

							/*
							 * 如果PPT在WPS中保存过，则 HSLFTextRunList.get(j).getFontSize();的值为0或者26040，
							 * 因此首先识别当前文本框内的字体尺寸是否为0或者大于26040，则设置默认的字体尺寸。
							 * 
							 */
							// 设置字体大小
							Double size = HSLFTextRunList.get(j).getFontSize();
							if ((size <= 0) || (size >= 26040)) {
								HSLFTextRunList.get(j).setFontSize(20.0);
							}
							// 设置字体样式为宋体
							// String family=HSLFTextRunList.get(j).getFontFamily();
							HSLFTextRunList.get(j).setFontFamily("宋体");

						}
					}
				}

				// 创建BufferedImage 对象，图像尺寸为原来的PPT的每页尺寸

				BufferedImage oneBufferedImage = new BufferedImage(onePPTPageSize.width, onePPTPageSize.height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D oneGraphics2D = oneBufferedImage.createGraphics();
				/**
				 * 设置转换后的图片背景色为白色
				 * 
				 */
				oneGraphics2D.setPaint(Color.white);
				oneGraphics2D.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width, onePPTPageSize.height));

				// 将PPT文件中的每个页面中的相关内容画到转换后的图片中
				pptPageSlideList.get(i).draw(oneGraphics2D);
				/**
				 * 设置图片的存放路径和图片格式，注意生成的文件路径为绝对路径，最终获得各个图像文件所对应的输出流的对象
				 */
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
				Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
				imageMap.put(targetImageFileDir + imgName, oneBufferedImage);
				convertedImagesInfoList.add(imageMap);
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
		for (Map<String, BufferedImage> imageInfo : convertedImagesInfoList) {
			for (Entry<String, BufferedImage> image : imageInfo.entrySet()) {
				// 将转换后的各个图片文件保存带指定的目录中
				ImageIO.write(image.getValue(), imageFormatNameString, new File(image.getKey()));
			}
		}

	}
}
