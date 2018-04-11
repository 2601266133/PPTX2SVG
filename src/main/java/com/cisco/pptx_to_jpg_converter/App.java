package com.cisco.pptx_to_jpg_converter;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Convert PPT file to SVG image
 *
 */
@SpringBootApplication
@EnableTransactionManagement
public class App extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize("1024MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("1152MB");
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		logger.debug("Now start the application.");
		SpringApplication.run(App.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	}

}
