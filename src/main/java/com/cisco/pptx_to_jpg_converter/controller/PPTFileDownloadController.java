package com.cisco.pptx_to_jpg_converter.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.Zip64Mode;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;
import com.cisco.pptx_to_jpg_converter.model.PPTInformation;
import com.cisco.pptx_to_jpg_converter.service.CiscoService;

@Controller
public class PPTFileDownloadController {

	private static final Logger logger = LoggerFactory.getLogger(PPTFileDownloadController.class);

	@Value("${web.upload-path}")
	private String filePath;

	@Autowired
	private CiscoService service;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String downLoad(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Start file download by name processing");
		String filename = request.getParameter("fileName");
		File file = new File(filePath + filename);
		if (file.exists()) {
			downloadFile(file, filename, response);
		} else {
			return "文件不存在。";
		}
		return null;
	}

	@RequestMapping(value = "/download/ppt/{id}", method = RequestMethod.GET)
	public String downloadById(@PathVariable String id, HttpServletResponse response) {
		logger.info("----------------------------------------------------------------------");
		logger.info("Start download ppt by id: " + id);
		long startTime = System.currentTimeMillis();
		PPTInformation result = service.getPPTByIdByMapper(id);
		String fileNewName = result.getFileNewName();
		String filePath = result.getFilePath();
		String fileOriginName = result.getFileOrignName();
		File file = new File(filePath + fileNewName);
		if (file.exists()) {
			downloadFile(file, fileOriginName, response);
		} else {
			return "文件不存在。";
		}
		logger.info("Finish download ppt, takes: " + (System.currentTimeMillis() - startTime));
		logger.info("----------------------------------------------------------------------");
		return null;
	}

	@RequestMapping(value = "/download/ppt/{pptId}/image/{id}", method = RequestMethod.GET)
	public String downloadImageById(@PathVariable String pptId, @PathVariable String id, HttpServletResponse response) {
		ImageInformation result = service.getImageInfoByIdByMapper(id);
		String imageName = result.getImageName();
		String imagePath = result.getImagePath();
		File file = new File(imagePath + imageName);
		if (file.exists()) {
			downloadFile(file, imageName, response);
		} else {
			return "文件不存在。";
		}
		return null;
	}

	@RequestMapping(value = "/download/ppt/{id}/images", method = RequestMethod.GET)
	public String getImagesByPPTId(@PathVariable String id, HttpServletResponse response) {
		logger.info("----------------------------------------------------------------------");
		logger.info("Start download images by ppt id: " + id);
		long startTime = System.currentTimeMillis();
		String pptId = id;
		PPTInformation result = service.getPPTByIdByMapper(pptId);
		String fileNewName = result.getFileNewName();
		String imagePath = result.getImagePath();
		logger.info("The fileNewName is: " + fileNewName + " and the image path is: " + imagePath);
		File file = new File(imagePath);
		logger.info("file exists: " + file.exists());
		logger.info("file is directory: " + file.isDirectory());
		if (file.exists() && file.isDirectory()) {
			logger.info("find the image saved file");
			File[] imageFiles = file.listFiles();
			if (imageFiles.length > 0) {
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileNewName.substring(0, fileNewName.lastIndexOf(".") - 1) + ".zip");

				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				ZipOutputStream zipOut = null;
				OutputStream os = null;
				try {
					os = response.getOutputStream();
					zipOut = new ZipOutputStream(os);
					zipOut.setUseZip64(Zip64Mode.Always);
					zipOut.setEncoding("GBK");
					logger.info("read the image one by one");
					for (File imageFile : imageFiles) {
						fis = new FileInputStream(imageFile);
						zipOut.putNextEntry(new ZipEntry(imageFile.getName()));
						logger.info("Image name: " + imageFile.getName());
						int len = -1;
						while ((len = fis.read(buffer)) != -1) {
							zipOut.write(buffer, 0, len);
						}
						zipOut.flush();
						zipOut.closeEntry();
						fis.close();
					}
					logger.info("read all: " + imageFiles.length);
					zipOut.close();
					logger.info("Finish download images, takes: " + (System.currentTimeMillis() - startTime));
					logger.info("----------------------------------------------------------------------");
				} catch (Exception e) {
					logger.info(e.getMessage());
					return e.getMessage();
				} finally {
					try {
						if (fis != null) {
							fis.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (os != null) {
							os.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

		return null;

	}

	private void downloadFile(File file, String fileName, HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

		// byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;

		OutputStream os = null;

		try {
			os = response.getOutputStream();
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, os);
			// bis = new BufferedInputStream(fis);
			// int i = bis.read(buffer);
			// while (i != -1) {
			// os.write(buffer);
			// i = bis.read(buffer);
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (fis != null) {
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
