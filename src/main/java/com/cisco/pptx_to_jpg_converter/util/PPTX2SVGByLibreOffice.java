package com.cisco.pptx_to_jpg_converter.util;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.cisco.pptx_to_jpg_converter.model.ImageInformation;

public class PPTX2SVGByLibreOffice extends AbstractConverter {

	private static final Logger logger = LoggerFactory.getLogger(PPTX2SVGByLibreOffice.class);

	private List<Map<String, SVGGraphics2D>> convertedSVGList = new ArrayList<Map<String, SVGGraphics2D>>();

	private InputStream in;

	public PPTX2SVGByLibreOffice(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
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

			uuID = UUID.randomUUID().toString();
			logger.info("parent image direct: " + targetImageFileDir);
			targetImageFileDir = targetImageFileDir + uuID + File.separator;
			logger.info("real image direct: " + targetImageFileDir);

			String OpenOffice_HOME = getLibreOfficeHome();
			String OpenOffice_IP = "127.0.0.1";
			int OpenOffice_Port = 8100;
			// 启动OpenOffice的服务
			String command = OpenOffice_HOME + "\\program\\soffice.exe -headless -accept=\"socket,host=" + OpenOffice_IP
					+ ",port=" + OpenOffice_Port + ";urp;\"";

			Process pro = Runtime.getRuntime().exec(command);

			// 创建连接
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(OpenOffice_IP, OpenOffice_Port);
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
			DocumentFormat txt = formatReg.getFormatByFileExtension("odt");
			DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");
			File tpdf = new File(targetImageFileDir.replace("\\", "/") + "ss.pdf");
			if (!tpdf.getParentFile().exists()) {
				tpdf.getParentFile().mkdirs();
			}
			FileOutputStream outPdfStream = new FileOutputStream(tpdf);
			converter.convert(in, txt, outPdfStream, pdf);
			connection.disconnect();
			outPdfStream.close();
			pro.destroy();

			PDDocument document = PDDocument.load(tpdf);
			PDFRenderer renderer = new PDFRenderer(document);

			for (int i = 0; i < pptPageXSLFSLiseList.size(); i++) {

				// use jfreeSVG
				SVGGraphics2D graphics = new SVGGraphics2D(onePPTPageSize.width, onePPTPageSize.height);

				renderer.renderPageToGraphics(i, graphics);

				// save the result.

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

			}
		} catch (IOException e) {
			e.printStackTrace();
			converReturnResult = false;
			dest.getParentFile().delete();
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			converReturnResult = false;
			dest.getParentFile().delete();
			throw new Exception(e);
		} finally {

			try {
				if (inStream != null) {
					inStream.close();
				}
				if (in != null) {
					in.close();
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
	}

	public String getLibreOfficeHome() {
		String osName = System.getProperty("os.name");

		if (Pattern.matches("Linux.*", osName)) {
			// 获取linux系统下libreoffice主程序的位置
			logger.info("获取Linux系统LibreOffice路径");
			return "/opt/libreoffice 5/program/soffice";
		} else if (Pattern.matches("Windows.*", osName)) {
			// 获取windows系统下libreoffice主程序的位置
			logger.info("获取windows系统LibreOffice路径");
			return "C:\\Software\\Libreoffice";
		}
		return null;
	}

	public void setAnotherINStream(InputStream in) {
		this.in = in;
	}

}
