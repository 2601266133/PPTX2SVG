package com.cisco.pptx_to_jpg_converter.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.pptx_to_jpg_converter.mapper.PPTInfoMapper;
import com.cisco.pptx_to_jpg_converter.model.ImageInformation;
import com.cisco.pptx_to_jpg_converter.model.PPTInformation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(true)
public class AddPPTByMapByMapperDBTest {

	@Autowired
	PPTInfoMapper pptMapper;

	@Test
	public void testNormal() throws Exception {
		PPTInformation pptInfo = new PPTInformation();
		pptInfo.setFilePath("D:/junit/ppt/");
		pptInfo.setFileNewName("117junit.pptx");
		pptInfo.setFileOrignName("junit.pptx");
		pptInfo.setImagePath("D:/junit/images/118/");
		pptInfo.setImageUUID("118");
		pptInfo.setSize("1MB");
		pptInfo.setCreatedBy("junit");
		List<ImageInformation> imagesInforList = new ArrayList<ImageInformation>();
		ImageInformation image = new ImageInformation();
		image.setImageName("001.jpg");
		image.setImagePath("D:/junit/images/118/");
		image.setCreatedBy("junit");
		imagesInforList.add(image);
		Map map = new HashMap();
		map.put("pptInfo", pptInfo);
		map.put("imagesInfoList", imagesInforList);
		pptMapper.addPPTByMap(map);
		List<PPTInformation> results = pptMapper.getPPTs();
		for (PPTInformation ppt : results) {
			if ("117junit.pptx".equals(ppt.getFileNewName())) {
				assertEquals("D:/junit/ppt/", ppt.getFilePath());
				assertEquals("117junit.pptx", ppt.getFileNewName());
				assertEquals("junit.pptx", ppt.getFileOrignName());
				assertEquals("D:/junit/images/118/", ppt.getImagePath());
				assertEquals("118", ppt.getImageUUID());
				assertEquals("001.jpg", ppt.getImageInfoList().get(0).getImageName());
			}
		}
	}
}
