package com.cisco.pptx_to_jpg_converter.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.pptx_to_jpg_converter.mapper.PPTInfoMapper;
import com.cisco.pptx_to_jpg_converter.model.PPTInformation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(true)
public class DeletePPTByIdByMapperDBTest {

	@Autowired
	PPTInfoMapper pptMapper;

	@Autowired
	private CiscoService service;

	@Before
	public void setUp() throws Exception {
		String sql01 = "delete from imageInfo where id in(-1,-2);";
		String sql0 = "delete from pptinfo where id in (-1,-2)";
		String sql1 = "insert into pptinfo(ID,FILE_PATH,FILE_NEW_NAME,FILE_ORIGN_NAME,IMAGE_PATH,IMAGE_UUID,CREATED_DT,CREATED_BY) values(-1,'D:/junit/ppt/','117junit.pptx','junit.pptx','D:/junit/images/118/','118',current_timestamp,'junit');";
		String sql2 = "insert into pptinfo(ID,FILE_PATH,FILE_NEW_NAME,FILE_ORIGN_NAME,IMAGE_PATH,IMAGE_UUID,CREATED_DT,CREATED_BY) values(-2,'D:/junit/ppt/','1171junit.pptx','junit.pptx','D:/junit/images/1181/','1181',current_timestamp,'junit');";
		String sql3 = "insert into imageinfo(ID,PPT_ID,IMAGE_PATH,IMAGE_NAME,CREATED_DT,CREATED_BY) values(-1,-1,'D:/junit/images/118/','001.jpg',current_timestamp,'junit')";
		String sql4 = "insert into imageinfo(ID,PPT_ID,IMAGE_PATH,IMAGE_NAME,CREATED_DT,CREATED_BY) values(-2,-2,'D:/junit/images/1181/','990.jpg',current_timestamp,'junit')";
		List<String> sqls = new ArrayList<String>();
		sqls.add(sql01);
		sqls.add(sql0);
		sqls.add(sql1);
		sqls.add(sql2);
		sqls.add(sql3);
		sqls.add(sql4);
		service.prepareData(sqls);
	}

	@Test
	public void testNormal() throws Exception {
		pptMapper.deletePPT("-1");
		PPTInformation result = pptMapper.getPPTById("-1");
		assertEquals(null, result);
	}

}
