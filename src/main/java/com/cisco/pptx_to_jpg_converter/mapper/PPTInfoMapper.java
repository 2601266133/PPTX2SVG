package com.cisco.pptx_to_jpg_converter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.pptx_to_jpg_converter.model.ImageInformation;
import com.cisco.pptx_to_jpg_converter.model.PPTInformation;

@Mapper
public interface PPTInfoMapper {

	public List<PPTInformation> getPPTs();

	public PPTInformation getPPTById(String id);

	public PPTInformation getPPTByName(String fileName);

	// @Transactional
	// public void addPPT(PPTInformation pptInfo);

	@Transactional
	public void addPPTByMap(Map<String, Object> paramMap);

	@Transactional
	public void deletePPT(String id);

	@Transactional
	public void deletePPTs();

	// public void addPPTImages(List<ImageInformation> imagesList);

	// public List<ImageInformation> getAllImages();

	public List<ImageInformation> getImagesByPPTId(String pptId);

	public ImageInformation getImageById(String id);

}
