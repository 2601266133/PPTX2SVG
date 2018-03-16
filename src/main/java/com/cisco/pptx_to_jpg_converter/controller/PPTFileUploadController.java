package com.cisco.pptx_to_jpg_converter.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.pptx_to_jpg_converter.model.PPTInformation;
import com.cisco.pptx_to_jpg_converter.service.CiscoService;
import com.cisco.pptx_to_jpg_converter.util.AbstractConverter;
import com.cisco.pptx_to_jpg_converter.util.PPTToSVGConverter;
import com.cisco.pptx_to_jpg_converter.util.PPTXToSVGConverter;

@RestController
public class PPTFileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(PPTFileUploadController.class);

	@Value("${web.upload-path}")
	private String filePath;

	@Value("${pptx.image-path}")
	private String pptxImagePath;

	@Value("${temp.convert-ppt-path}")
	private String pptxToPPTPath;

	@Autowired
	private CiscoService service;

	// 访问路径为：http://127.0.0.1:8080/file
	@RequestMapping("/file")
	public ModelAndView file() {
		return new ModelAndView("/file");
	}

	@RequestMapping("/mutifile")
	public ModelAndView mutifile() {
		return new ModelAndView("/mutifile");
	}

	@RequestMapping("sfile")
	public ModelAndView sfile() {
		return new ModelAndView("/sfile");
	}

	@RequestMapping("images")
	public String images() {
		return "/images";
	}

	@RequestMapping(value = "/upload", produces = { "application/json;charset=UTF-8", "application/xml;charset=UTF-8" })
	@ResponseBody
	public PPTInformation multipartFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		long startTime = System.currentTimeMillis();
		logger.info("Start file upload processing ");
		if (!file.isEmpty()) {

			String fileName = null;
			String name = null;
			String suffixName = null;
			Map<String, Object> map = new HashMap<String, Object>();
			AbstractConverter converter = null;
			PPTInformation pptInfo = null;
			File tempPPTFile = null;
			try {
				// 获取文件名
				fileName = file.getOriginalFilename();
				if (fileName.contains("\\")) {
					name = fileName.substring(fileName.lastIndexOf("\\") + 1);
				} else {
					name = fileName;
				}

				// 获取文件的后缀名
				suffixName = fileName.substring(fileName.lastIndexOf("."));

				// 文件上传后的路径
				String newName = System.currentTimeMillis() + name;
				File dest = new File(filePath + newName);

				if (".pptx".equals(suffixName) || ".potx".equals(suffixName) || ".ppsx".equals(suffixName)) {
					// 1.by LibreOffice
					// converter = new PPTX2SVGByLibreOffice(file.getInputStream(), pptxImagePath,
					// "svg");
					// ((PPTX2SVGByLibreOffice)
					// converter).setAnotherINStream(file.getInputStream());

					// 2. by self
					converter = new PPTXToSVGConverter(file.getInputStream(), pptxImagePath, "svg");
				} else if (".ppt".equals(suffixName) || ".pps".equals(suffixName)) {
					converter = new PPTToSVGConverter(file.getInputStream(), pptxImagePath, "svg");
				}
				if (converter != null) {
					converter.convert();
					map = converter.getResultsMap();
					pptInfo = new PPTInformation();
					pptInfo.setFilePath(filePath);
					pptInfo.setFileNewName(newName);
					pptInfo.setFileOrignName(name);
					pptInfo.setSize(AbstractConverter.FormetFileSize(file.getSize()));
					pptInfo.setImagePath(map.get("path").toString());
					pptInfo.setImageUUID(map.get("uuID").toString());
					pptInfo.setCreatedBy("jacsong2");

					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pptInfo", pptInfo);
					paramMap.put("imagesInfoList", map.get("imagesInfo"));
					// service.addPPTByMapper(pptInfo);
					service.addPPTByMapByMapper(paramMap);
					logger.info("The PPT ID is: " + pptInfo.getId());

					converter.writeImages();
				}

				// 检测是否存在目录
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}

				if (dest.isFile() && dest.exists()) {
					// return "文件已经存在，请更改文件名。";
					dest.delete();
				}
				dest.setLastModified(System.currentTimeMillis());
				FileOutputStream out = new FileOutputStream(dest);
				InputStream in = file.getInputStream();
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				out.flush();
				out.close();
				in.close();

			} catch (SQLException se) {
				se.printStackTrace();
				logger.error(se.getMessage());
				// return "上传失败," + e.getMessage();
				return pptInfo;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				PPTInfoController controller = new PPTInfoController();
				controller.deletePPTAndImages(pptInfo);
				service.deletePPTByIdByMapper(pptInfo.getId());
				return pptInfo;
			} catch (Exception ee) {
				ee.printStackTrace();
				logger.error(ee.getMessage());
				return pptInfo;
			}

			// return "/images";
			// return "上传成功 ： 在目录 " + filePath + ": PPT 转换的结果是: " +
			// map.get("converReturnResult");
			ModelAndView mv = new ModelAndView("/images");
			mv.addObject("map", map);
			mv.addObject("PPTid", pptInfo.getId());
			logger.info("Finish upload processing, take time: " + (System.currentTimeMillis() - startTime));
			return pptInfo;

		} else {

			// return "上传失败，因为文件是空的.";
			return null;

		}
	}

	/**
	 * 
	 * 多文件具体上传时间，主要是使用了MultipartHttpServletRequest和MultipartFile
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 */

	@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(HttpServletRequest request) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		MultipartFile file = null;

		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					multipartFileUpload(file);
				} catch (Exception e) {
					e.printStackTrace();
					return "You failed to upload " + i + " =>" + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " becausethe file was empty.";
			}
		}
		return "upload successful";

	}

	// 当客户端不是WebApp时，用httpClient发起请求
	// 如果是真正的cloud环境，需要切换到自己的cloudClient发起请求

	@RequestMapping("/sUpload")
	@ResponseBody
	public String simulatemultipartFileUpload(HttpServletRequest request) throws IOException {
		String url = URLDecoder.decode(request.getParameter("targetUrl"), "UTF-8");
		String filePath = URLDecoder.decode(request.getParameter("filePath"), "UTF-8");
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(file);
			StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("comment", comment)
					.build();

			httppost.setEntity(reqEntity);

			System.out.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response2 = httpclient.execute(httppost);
			String status = null;
			try {
				System.out.println("----------------------------------------");
				System.out.println(response2.getStatusLine());
				HttpEntity resEntity = response2.getEntity();
				if (resEntity != null) {
					status = EntityUtils.toString(resEntity);
					System.out.println("Response content length: " + resEntity.getContentLength());
				}
				EntityUtils.consume(resEntity);
			} finally {
				response2.close();
			}
			return status;
		} finally {
			httpclient.close();
		}

	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setPptxImagePath(String pptxImagePath) {
		this.pptxImagePath = pptxImagePath;
	}

}
