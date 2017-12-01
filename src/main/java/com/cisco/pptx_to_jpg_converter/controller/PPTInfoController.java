package com.cisco.pptx_to_jpg_converter.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.pptx_to_jpg_converter.model.PPTInformation;
import com.cisco.pptx_to_jpg_converter.model.RequestResult;
import com.cisco.pptx_to_jpg_converter.service.CiscoService;

@RestController
public class PPTInfoController {

	private static final Logger logger = LoggerFactory.getLogger(PPTInfoController.class);

	@Autowired
	private CiscoService service;

	@RequestMapping(value = "/ppt/{id}", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public PPTInformation getPPTById(@PathVariable String id) {
		// String id = request.getParameter("id");
		return service.getPPTByIdByMapper(id);
	}

	@RequestMapping(value = "/ppt/name/{fileName}", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public PPTInformation getPPTByName(@PathVariable String fileName) {
		return service.getPPTByNameByMapper(fileName);
	}

	@RequestMapping(value = "/ppts", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public List<PPTInformation> getPPTs() {
		return service.getPPTsByMapper();
	}

	@RequestMapping(value = "/delete/ppt/{id}", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public RequestResult deletePPTByid(@PathVariable String id) {

		try {
			PPTInformation result = service.getPPTByIdByMapper(id);
			if (result == null) {
				return new RequestResult("Result", "文件不存在。", "400");
			}
			deletePPTAndImages(result);
			service.deletePPTByIdByMapper(id);
		} catch (SQLException se) {
			return new RequestResult("Failed", "失败： " + se.getMessage(), "500");
		}
		return new RequestResult("Result", "成功。", "204");
	}

	@RequestMapping(value = "/delete/ppts", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public RequestResult deleteAllFiles() {
		try {
			List<PPTInformation> results = service.getPPTsByMapper();
			if (results == null || results.isEmpty()) {
				return new RequestResult("Failed", "文件不存在。", "400");
			}
			for (PPTInformation result : results) {
				deletePPTAndImages(result);
			}
			service.deletePPTsByMapper();
		} catch (SQLException se) {
			return new RequestResult("Failed", "失败： " + se.getMessage(), "500");
		}
		return new RequestResult("Success", "成功。", "204");
	}

	private void deletePPTAndImages(PPTInformation result) {
		String filePath = result.getFilePath();
		String fileNewName = result.getFileNewName();
		String imagePath = result.getImagePath();

		File pptFile = new File(filePath + fileNewName);
		if (pptFile.exists()) {
			pptFile.delete();
		}
		File imageFile = new File(imagePath);
		if (imageFile.exists() && imageFile.isDirectory()) {
			File[] images = imageFile.listFiles();
			if (images.length > 0) {
				for (File image : images) {
					image.delete();
				}
				imageFile.delete();
			} else {
				imageFile.delete();
			}
		}
	}

}
