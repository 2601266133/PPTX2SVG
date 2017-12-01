package com.cisco.pptx_to_jpg_converter.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cisco.pptx_to_jpg_converter.model.PPTInformation;
import com.cisco.pptx_to_jpg_converter.model.RequestResult;
import com.cisco.pptx_to_jpg_converter.service.CiscoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @WebMvcTest(PPTInfoController.class)
// @DirtiesContext
public class PPTInfoControllerTest {

	// @Autowired
	// private MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private CiscoService service;

	@Test
	public void testGetPPTById() throws Exception {
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setId("1");
		pptInfo.setFileOrignName("Test.pptx");
		pptInfo.setFileNewName("12345667Test.pptx");
		pptInfo.setFilePath("D:\\ppt");
		pptInfo.setImagePath("D:\\image\\1");
		Mockito.when(this.service.getPPTByIdByMapper(Mockito.anyString())).thenReturn(pptInfo);
		ResponseEntity<PPTInformation> entity = restTemplate.getForEntity("/ppt/1.xml", PPTInformation.class);
		assertEquals(entity.getBody().getId(), pptInfo.getId());
	}

	@Test
	public void testGetPPTs() throws Exception {
		List<PPTInformation> pptInfoList = new ArrayList<PPTInformation>();
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setId("1");
		pptInfo.setFileOrignName("Test.pptx");
		pptInfo.setFileNewName("12345667Test.pptx");
		pptInfo.setFilePath("D:\\ppt");
		pptInfo.setImagePath("D:\\image");
		PPTInformation pptInfo2 = new PPTInformation();
		pptInfo2.setId("2");
		pptInfo2.setFileOrignName("Test2.pptx");
		pptInfo2.setFileNewName("7654321Test2.pptx");
		pptInfo2.setFilePath("D:\\ppt");
		pptInfo2.setImagePath("D:\\image\\2");
		pptInfoList.add(pptInfo);
		pptInfoList.add(pptInfo2);
		Mockito.when(this.service.getPPTsByMapper()).thenReturn(pptInfoList);
		ResponseEntity<List> entity = restTemplate.getForEntity("/ppts.xml", List.class);
		assertEquals(((Map) entity.getBody().get(0)).get("id"), pptInfo.getId());
		assertEquals(((Map) entity.getBody().get(1)).get("id"), pptInfo2.getId());
	}

	@Test
	public void testDeletePPTById() throws Exception {
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setId("1");
		pptInfo.setFileOrignName("Test.pptx");
		pptInfo.setFileNewName("12345667Test.pptx");
		pptInfo.setFilePath("D:\\ppt");
		pptInfo.setImagePath("D:\\image");
		Mockito.when(service.getPPTByIdByMapper(Mockito.anyString())).thenReturn(pptInfo);
		Mockito.doNothing().when(service).deletePPTByIdByMapper(Mockito.anyString());
		ResponseEntity<RequestResult> entity = restTemplate.getForEntity("/delete/ppt/1", RequestResult.class);
		// 当有多个构造方法时，必须要写出默认的构造方法
		assertEquals(entity.getBody().getCode(), "204");
		assertEquals(entity.getBody().getContext(), "成功。");
	}

	@Test
	public void testDeletePPTs() throws Exception {
		List<PPTInformation> pptInfoList = new ArrayList<PPTInformation>();
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setId("1");
		pptInfo.setFileOrignName("Test.pptx");
		pptInfo.setFileNewName("12345667Test.pptx");
		pptInfo.setFilePath("D:\\ppt");
		pptInfo.setImagePath("D:\\image");
		PPTInformation pptInfo2 = new PPTInformation();
		pptInfo2.setId("2");
		pptInfo2.setFileOrignName("Test2.pptx");
		pptInfo2.setFileNewName("7654321Test2.pptx");
		pptInfo2.setFilePath("D:\\ppt");
		pptInfo2.setImagePath("D:\\image\\2");
		pptInfoList.add(pptInfo);
		pptInfoList.add(pptInfo2);
		Mockito.when(service.getPPTsByMapper()).thenReturn(pptInfoList);
		Mockito.doNothing().when(service).deletePPTsByMapper();
		ResponseEntity<RequestResult> entity = restTemplate.getForEntity("/delete/ppts", RequestResult.class);
		assertEquals(entity.getBody().getCode(), "204");
		assertEquals(entity.getBody().getContext(), "成功。");
	}

}
