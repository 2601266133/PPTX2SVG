package com.cisco.pptx_to_jpg_converter.configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * this configuration not worked, currently I don'y find why? but configure in
 * application.properties, it works.
 * 
 * @author jacsong2
 *
 */

public class MyWebAppConfig extends WebMvcConfigurerAdapter {

	// 访问图片方法
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("../temp/converter/upload/images/")
				.setCachePeriod(0);

	}

}
