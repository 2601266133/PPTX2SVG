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
	public RequestResult getPPTById(@PathVariable String id) {
		// String id = request.getParameter("id");
		return new RequestResult(RequestResult.SUCCESS, "SUCCESS", RequestResult.CODE_SUCCESS,
				service.getPPTByIdByMapper(id));
	}

	@RequestMapping(value = "/ppt/name/{fileName}", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public RequestResult getPPTByName(@PathVariable String fileName) {
		return new RequestResult(RequestResult.SUCCESS, "SUCCESS", RequestResult.CODE_SUCCESS,
				service.getPPTByNameByMapper(fileName));
	}

	@RequestMapping(value = "/ppts", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = RequestMethod.GET)
	public RequestResult getPPTs() {
		return new RequestResult(RequestResult.SUCCESS, "SUCCESS", RequestResult.CODE_SUCCESS,
				service.getPPTsByMapper());
	}

	@RequestMapping(value = "/delete/ppt/{id}", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = { RequestMethod.GET, RequestMethod.DELETE })
	public RequestResult deletePPTByid(@PathVariable String id) {

		try {
			PPTInformation result = service.getPPTByIdByMapper(id);
			if (result == null) {
				return new RequestResult(RequestResult.FAILED, "File not found.", RequestResult.CODE_NOT_FOUND);
			}
			deletePPTAndImages(result);
			service.deletePPTByIdByMapper(id);
		} catch (SQLException se) {
			return new RequestResult(RequestResult.FAILED, "Failed: " + se.getMessage(), RequestResult.CODE_ERROR);
		}
		return new RequestResult(RequestResult.SUCCESS, "SUCCESS", RequestResult.CODE_SUCCESS);
	}

	@RequestMapping(value = "/delete/ppts", produces = { "application/json;charset=UTF-8",
			"application/xml;charset=UTF-8" }, method = { RequestMethod.GET, RequestMethod.DELETE })
	public RequestResult deleteAllFiles() {
		try {
			List<PPTInformation> results = service.getPPTsByMapper();
			if (results == null || results.isEmpty()) {
				return new RequestResult(RequestResult.FAILED, "File not found.", RequestResult.CODE_NOT_FOUND);
			}
			for (PPTInformation result : results) {
				deletePPTAndImages(result);
			}
			service.deletePPTsByMapper();
		} catch (SQLException se) {
			return new RequestResult(RequestResult.FAILED, "Failed: " + se.getMessage(), RequestResult.CODE_ERROR);
		}
		return new RequestResult(RequestResult.SUCCESS, "SUCCESS", RequestResult.CODE_SUCCESS);
	}

	public void deletePPTAndImages(PPTInformation result) {
		String filePath = result.getFilePath();
		String fileNewName = result.getFileNewName();
		String imagePath = result.getImagePath();
		logger.info("FilePath: " + filePath + " FileNewName: " + fileNewName + " ImagePath: " + imagePath);

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
