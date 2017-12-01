package com.cisco.pptx_to_jpg_converter.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cisco.pptx_to_jpg_converter.mapper.PPTInfoMapper;
import com.cisco.pptx_to_jpg_converter.model.ImageInformation;
import com.cisco.pptx_to_jpg_converter.model.PPTInformation;

@Service
public class CiscoService {

	private static final Logger logger = LoggerFactory.getLogger(CiscoService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PPTInfoMapper pptMapper;
	//
	// public List<PPTInformation> getPPTs() {
	// String sql = "select * from pptinfo";
	// return jdbcTemplate.query(sql, new RowMapper<PPTInformation>() {
	//
	// @Override
	// public PPTInformation mapRow(ResultSet rs, int arg1) throws SQLException {
	// PPTInformation pptInfo = new PPTInformation();
	// pptInfo.setId(rs.getString("ID"));
	// pptInfo.setFilePath(rs.getString("FILE_PATH"));
	// pptInfo.setFileNewName(rs.getString("FILE_NEW_NAME"));
	// pptInfo.setFileOrignName(rs.getString("FILE_ORIGN_NAME"));
	// pptInfo.setImagePath(rs.getString("IMAGE_PATH"));
	// pptInfo.setImageUUID(rs.getString("IMAGE_UUID"));
	// pptInfo.setCreatedDate(rs.getDate("CREATED_DT"));
	// pptInfo.setCreatedBy(rs.getString("CREATED_BY"));
	// return pptInfo;
	// }
	//
	// });
	// }

	public void prepareData(List<String> slqs) {
		for (String sql : slqs) {
			jdbcTemplate.batchUpdate(sql);
		}
	}

	public List<PPTInformation> getPPTsByMapper() {
		logger.info("Get all ppts information.");
		return pptMapper.getPPTs();
	}

	public void addPPTByMapByMapper(Map paramMap) throws SQLException {
		pptMapper.addPPTByMap(paramMap);
	}

	// public void addPPTByMapper(PPTInformation pptInfo) throws SQLException {
	// pptMapper.addPPT(pptInfo);
	// }

	public PPTInformation getPPTByIdByMapper(String id) {
		return pptMapper.getPPTById(id);
	}

	public void deletePPTByIdByMapper(String id) throws SQLException {
		pptMapper.deletePPT(id);
	}

	public void deletePPTsByMapper() throws SQLException {
		pptMapper.deletePPTs();
	}

	public PPTInformation getPPTByNameByMapper(String fileName) {
		return pptMapper.getPPTByName(fileName);
	}

	public List<ImageInformation> getImageInfoByPPTIdByMapper(String pptId) {
		return pptMapper.getImagesByPPTId(pptId);
	}

	public ImageInformation getImageInfoByIdByMapper(String id) {
		return pptMapper.getImageById(id);
	}
}
