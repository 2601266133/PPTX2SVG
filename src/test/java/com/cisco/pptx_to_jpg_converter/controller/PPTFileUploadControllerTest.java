package com.cisco.pptx_to_jpg_converter.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cisco.pptx_to_jpg_converter.service.CiscoService;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(PPTFileUploadController.class)
public class PPTFileUploadControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private PPTFileUploadController controller;

	@MockBean
	private CiscoService service;

	File pptFile = null;
	FileInputStream fIn = null;

	@Before
	public void setUp() throws Exception {
		controller.setFilePath("src/test/resources/upload/");
		controller.setPptxImagePath("src/test/resources/images/");
		tearDown();
	}

	@Ignore
	@Test
	public void testUploadPPTX() throws Exception {
		pptFile = new File("src/test/resources/ppt/junit.pptx");
		fIn = new FileInputStream(pptFile);
		MockMultipartFile mockFile = new MockMultipartFile("file", "junit.pptx",
				MediaType.APPLICATION_OCTET_STREAM_VALUE, fIn);
		mvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(mockFile)).andDo(MockMvcResultHandlers.print());
		File uploadFile = new File("src/test/resources/upload/");
		File imagesFile = new File("src/test/resources/images/");
		assertEquals(5, imagesFile.listFiles()[0].listFiles().length);
		assertEquals(1, uploadFile.listFiles().length);
	}

	@Test
	public void testUploadPPT() throws Exception {
		pptFile = new File("src/test/resources/ppt/junit2.ppt");
		fIn = new FileInputStream(pptFile);
		MockMultipartFile mockFile = new MockMultipartFile("file", "junit2.ppt",
				MediaType.APPLICATION_OCTET_STREAM_VALUE, fIn);
		mvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(mockFile)).andDo(MockMvcResultHandlers.print());
		File uploadFile = new File("src/test/resources/upload/");
		File imagesFile = new File("src/test/resources/images/");
		assertEquals(27, imagesFile.listFiles()[0].listFiles().length);
		assertEquals(1, uploadFile.listFiles().length);
	}

	@After
	public void tearDown() throws Exception {
		if (fIn != null) {
			fIn.close();
		}
		File uploadFile = new File("src/test/resources/upload/");
		File imagesFile = new File("src/test/resources/images/");
		deleteFiles(uploadFile);
		deleteFiles(imagesFile);
	}

	private void deleteFiles(File file) {
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files.length > 0) {
				for (File f : files) {
					if (f.isFile()) {
						f.delete();
					} else {
						deleteFiles(f);
						f.delete();
					}
				}
			}
		}
		file.delete();
	}
}
