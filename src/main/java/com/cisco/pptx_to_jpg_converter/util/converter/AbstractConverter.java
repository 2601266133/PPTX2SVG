package com.cisco.pptx_to_jpg_converter.util.converter;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConverter implements Converter {

	private static final Logger logger = LoggerFactory.getLogger(AbstractConverter.class);

	private final String LOADING_FORMAT = "\nLoading stream\n\n";
	private final String PROCESSING_FORMAT = "Load completed in %1$dms, now converting...\n\n";
	private final String SAVING_FORMAT = "Conversion took %1$dms.\n\nTotal: %2$dms\n";

	private long startTime;
	private long startOfProcessTime;

	protected InputStream inStream;
	protected Map<String, Object> resultsMap = new HashMap<String, Object>();
	protected List<Map<String, BufferedImage>> convertedImagesInfoList = new ArrayList<Map<String, BufferedImage>>();
	protected String targetImageFileDir;
	protected String imageFormatNameString;
	protected boolean converReturnResult;

	public AbstractConverter(InputStream inStream, String targetImageFileDir, String imageFormatNameString) {
		this.inStream = inStream;
		this.targetImageFileDir = targetImageFileDir;
		this.imageFormatNameString = imageFormatNameString;
	}

	public abstract void convert() throws Exception;

	public abstract void writeImages() throws IOException;

	private void startTime() {
		startTime = System.currentTimeMillis();
		startOfProcessTime = startTime;
	}

	protected void loading() {
		sendToOutputOrNot(String.format(LOADING_FORMAT));
		startTime();
	}

	protected void processing() {
		long currentTime = System.currentTimeMillis();
		long prevProcessTook = currentTime - startOfProcessTime;
		sendToOutputOrNot(String.format(PROCESSING_FORMAT, prevProcessTook));
		startOfProcessTime = System.currentTimeMillis();

	}

	protected void finished() {
		long currentTime = System.currentTimeMillis();
		long timeTaken = currentTime - startTime;
		long prevProcessTook = currentTime - startOfProcessTime;
		startOfProcessTime = System.currentTimeMillis();
		sendToOutputOrNot(String.format(SAVING_FORMAT, prevProcessTook, timeTaken));
	}

	private void sendToOutputOrNot(String toBePrinted) {
		actuallySendToOutput(toBePrinted);
	}

	protected void actuallySendToOutput(String toBePrinted) {
		System.out.println(toBePrinted);
	}

	public Map<String, Object> getResultsMap() {
		return resultsMap;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.000");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}

	@SuppressWarnings("resource")
	public File convertPPTXToTempPPTFile(String pptxToPPTPath, String fileName) {
		File tempPPTFile = null;
		try {
			XMLSlideShow oneSlideShow = new XMLSlideShow(inStream);
			String fileTempName = "Temp" + System.currentTimeMillis() + fileName;
			tempPPTFile = new File(pptxToPPTPath + fileTempName.substring(0, fileTempName.lastIndexOf(".")) + ".ppt");
			if (!tempPPTFile.getParentFile().exists()) {
				tempPPTFile.getParentFile().mkdirs();
			}
			oneSlideShow.write(new FileOutputStream(tempPPTFile));
		} catch (Exception e) {
			logger.info("Error in paser : " + e.getMessage());
		}
		return tempPPTFile;
	}

	public void clearTempPPT(File file) {
		file.delete();
	}
}
