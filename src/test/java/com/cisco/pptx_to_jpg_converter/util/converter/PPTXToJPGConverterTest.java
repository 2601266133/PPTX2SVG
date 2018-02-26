package com.cisco.pptx_to_jpg_converter.util.converter;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cisco.pptx_to_jpg_converter.util.PPTXToJPGConverter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PPTXToJPGConverterTest {

	File pptFile = null;
	FileInputStream fIn = null;

	@Test
	public void testNormal() throws Exception {
		pptFile = new File("src/test/resources/ppt/junit.pptx");
		fIn = new FileInputStream(pptFile);
		PPTXToJPGConverter converter = new PPTXToJPGConverter(fIn, "src/test/resources/images/", "jpg");
		converter.convert();
		converter.writeImages();
	}

	@After
	public void tearDown() throws Exception {
		if (fIn != null) {
			fIn.close();
		}
		File imagesFile = new File("src/test/resources/images/");
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
