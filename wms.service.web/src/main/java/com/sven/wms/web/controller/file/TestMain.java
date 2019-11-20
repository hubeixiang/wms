package com.sven.wms.web.controller.file;

import com.sven.wms.utils.JsonUtils;

/**
 * @author sven
 * @date 2019/10/29 10:20
 */
public class TestMain {
	public static void main(String[] args) {
		FileLocation fileLocation = new FileLocation();
		fileLocation.setExtension("xls");
		fileLocation.setService("ser");
		fileLocation.setFileUuid("uuid");

		String json = JsonUtils.toJson(fileLocation);
		System.out.println(json);

		FileLocation fileLocation1 = JsonUtils.fromJson(json, FileLocation.class);
		System.out.println(fileLocation1);

		String fileName = "E20.1.8.xls";
		FileNameDescribe fileNameDescribe = FileExtensions.getInstance().parserFileNameDescribe(fileName);
		System.out.println(fileNameDescribe.getExtension().indexOf("."));
		System.out.println(fileNameDescribe.getName().indexOf("."));
		System.out.println(fileNameDescribe);
	}
}
