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

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;

public class PPTXToJPGConverter extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(PPTXToJPGConverter.class);

	public PPTXToJPGConverter(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
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

			oneSlideShow = new XMLSlideShow(inStream);

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
				/**
				 * 设置中文为宋体，解决中文乱码问题
				 */
				// CTSlide oneCTSlide = pptPageXSLFSLiseList.get(i).getXmlObject();
				// CTGroupShape oneCTGroupShape = oneCTSlide.getCSld().getSpTree();
				// List<CTShape> oneCTShapeList = oneCTGroupShape.getSpList();
				// for (CTShape ctShape : oneCTShapeList) {
				// CTTextBody oneCTTextBody = ctShape.getTxBody();
				//
				// if (null == oneCTTextBody) {
				// continue;
				// }
				// CTTextParagraph[] oneCTTextParagraph = oneCTTextBody.getPArray();
				// CTTextFont oneCTTextFont = null;
				// oneCTTextFont = CTTextFont.Factory.parse(xmlFontFormat);
				// for (CTTextParagraph ctTextParagraph : oneCTTextParagraph) {
				// CTRegularTextRun[] onrCTRegularTextRunArray = ctTextParagraph.getRArray();
				// for (CTRegularTextRun ctRegularTextRun : onrCTRegularTextRunArray) {
				// CTTextCharacterProperties oneCTTextCharacterProperties =
				// ctRegularTextRun.getRPr();
				// oneCTTextCharacterProperties.setLatin(oneCTTextFont);
				//
				// }
				//
				// }
				// }

				for (XSLFShape shape : pptPageXSLFSLiseList.get(i).getShapes()) {
					if (shape instanceof XSLFTextShape) {
						XSLFTextShape tsh = (XSLFTextShape) shape;
						for (XSLFTextParagraph p : tsh) {
							for (XSLFTextRun r : p) {
								r.setFontFamily("宋体");
							}

						}
					}
				}

				XSLFSlide tempSlide = null;
				XSLFPictureData picture = null;
				for (POIXMLDocumentPart part : pptPageXSLFSLiseList.get(i).getRelations()) {
					if (part instanceof XSLFChart) {
						XSLFChart chart = (XSLFChart) part;
						// logger.info(chart.getCTChart().getPlotArea().toString());
					}

				}

				// 创建BufferedImage 对象，图像尺寸为原来的PPT的每页尺寸

				BufferedImage oneBufferedImage = new BufferedImage(onePPTPageSize.width, onePPTPageSize.height,
						BufferedImage.TYPE_INT_RGB);
				oneBufferedImage = getScaledImage(oneBufferedImage, onePPTPageSize.width, onePPTPageSize.height);
				Graphics2D oneGraphics2D = oneBufferedImage.createGraphics();
				/**
				 * 设置转换后的图片背景色为白色
				 *
				 */
				oneGraphics2D.setPaint(Color.white);
				oneGraphics2D.setBackground(pptPageXSLFSLiseList.get(i).getBackground().getFillColor());
				oneGraphics2D.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width, onePPTPageSize.height));
				// 将PPT文件中的每个页面中的相关内容画到转换后的图片中
				pptPageXSLFSLiseList.get(i).draw(oneGraphics2D);

				// DrawFactory drawFact = DrawFactory.getInstance(oneGraphics2D);
				// Drawable draw = drawFact.getDrawable(pptPageXSLFSLiseList.get(i));
				// draw.draw(oneGraphics2D);
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
		}
		// catch (XmlException e) {
		// e.printStackTrace();
		// converReturnResult = false;
		// }
		catch (IOException e) {
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
