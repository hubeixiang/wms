package com.sven.wms.web.controller.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sven
 * @date 2019/10/29 9:56
 */
@Controller
@RequestMapping("/upload/http")
public class HttpTransmissionFileController {
	private static Logger logger = LoggerFactory.getLogger(HttpTransmissionFileController.class);

	@RequestMapping(value = "uploadfile")
	@ResponseBody
	public List<FileLocation> httpUploadFile(HttpServletRequest request) {
		List<FileLocation> saveFiles = new ArrayList<>();

		saveMultipartHttpServletRequest(request, saveFiles);

		return saveFiles;
	}

	public void saveMultipartHttpServletRequest(HttpServletRequest request, List<FileLocation> saveFiles) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> map = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
			String key = entry.getKey();
			MultipartFile multipartFile = entry.getValue();
			logger.debug("key:" + key + ",filename:" + multipartFile.getOriginalFilename());
			String fileName = multipartFile.getOriginalFilename();
			FileNameDescribe fileNameDescribe = FileExtensions.getInstance().parserFileNameDescribe(fileName);
			if (fileNameDescribe == null) {
				//文件上传文件名错误
			} else {
				String uuid = UUIDGenerator.get();
				FileLocation fileLocation = new FileLocation();
				fileLocation.setFileUuid(uuid);
				fileLocation.setExtension(fileNameDescribe.getExtension());
				File file = new File(new File("").getAbsoluteFile().getParent() + File.separator + FileExtensions.getInstance()
						.getFileName(fileLocation));
				try {
					multipartFile.transferTo(file);
					saveFiles.add(fileLocation);
				} catch (Exception e) {
					logger.error("transferToFile Exception:" + e.getMessage(), e);
				}
			}
		}
	}
}
