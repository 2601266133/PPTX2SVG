package com.cisco.pptx_to_jpg_converter.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cisco.pptx_to_jpg_converter.model.PPTInformation;
import com.cisco.pptx_to_jpg_converter.service.CiscoService;

@RunWith(SpringRunner.class)
@WebMvcTest(PPTFileDownloadController.class)
public class PPTFileDownloadControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CiscoService service;

	@Test
	public void testDownloadPPTFileById() throws Exception {
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setId("1");
		pptInfo.setFilePath("src/test/resources/download/ppt/");
		pptInfo.setFileNewName("1510019153970junit.pptx");
		pptInfo.setFileOrignName("junit.pptx");
		Mockito.when(service.getPPTByIdByMapper(Mockito.anyString())).thenReturn(pptInfo);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/download/ppt/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		result.getResponse().getStatus();
		File ff = new File("src/test/resources/download/ppt/new.pptx");
		if (!ff.exists()) {
			ff.createNewFile();
		}
		FileOutputStream os = new FileOutputStream(ff);
		result.getResponse().getContentAsByteArray();
		os.write(result.getResponse().getContentAsByteArray());
		os.close();

	}

	@Test
	public void testGetImagesByPPTId() throws Exception {
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setId("1");
		pptInfo.setFilePath("src/test/resources/download/ppt/");
		pptInfo.setFileNewName("1510019153970junit.pptx");
		pptInfo.setFileOrignName("junit.pptx");
		pptInfo.setImagePath("src/test/resources/download/images");
		Mockito.when(service.getPPTByIdByMapper(Mockito.anyString())).thenReturn(pptInfo);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/download/ppt/1/images"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		result.getResponse().getStatus();
		File ff = new File("src/test/resources/download/ppt/newppt.zip");
		if (!ff.exists()) {
			ff.createNewFile();
		}
		FileOutputStream os = new FileOutputStream(ff);
		os.write(result.getResponse().getContentAsByteArray());
		os.close();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		File ff = new File("src/test/resources/download/ppt/new.pptx");
		File ffZip = new File("src/test/resources/download/ppt/newppt.zip");
		if (ff.exists()) {
			ff.delete();
		}
		if (ffZip.exists()) {
			ffZip.delete();
		}
	}
}