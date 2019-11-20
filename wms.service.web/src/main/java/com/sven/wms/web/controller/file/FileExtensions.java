package com.sven.wms.web.controller.file;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sven
 * @date 2019/10/29 10:47
 */
public class FileExtensions {
	private static Map<String, FileExtension> fileExtensionMap = new HashMap<>();
	private static FileExtensions instance = null;

	private FileExtensions() {
		add(createFileExtension(".txt", "TEXT"));
		add(createFileExtension(".log", "LOG"));
		add(createFileExtension(".xml", "XML"));
		add(createFileExtension(".csv", "CSV"));
		add(createFileExtension(".xls", "Excel03"));
		add(createFileExtension(".xlsx", "Excel07"));
		add(createFileExtension(".doc", "Word03"));
		add(createFileExtension(".docx", "Word07"));
	}

	private FileExtension createFileExtension(String extension, String extensionDisplay) {
		FileExtension fileExtension = new FileExtension();
		fileExtension.setExtension(extension);
		fileExtension.setExtensionDisplay(extensionDisplay);
		return fileExtension;
	}

	private void add(FileExtension fileExtension) {
		fileExtensionMap.put(fileExtension.getExtension(), fileExtension);
	}

	public static FileExtensions getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	private static synchronized void createInstance() {
		if (instance != null) {
			return;
		}
		instance = new FileExtensions();
	}

	/**
	 * 验证后缀必须有值,并且以"."开头
	 *
	 * @param extensionOper
	 * @return
	 */
	public boolean validIExtensionOper(IExtensionOper extensionOper) {
		if (extensionOper != null && extensionOper.getExtension() != null) {
			if (extensionOper.getExtension().indexOf(".") != 0) {
				extensionOper.setExtension(String.format(".%s", extensionOper.getExtension()));
			}
			return true;
		}
		return false;
	}

	/**
	 * 依据传入的文件名称,鉴别其文件扩展信息
	 *
	 * @param fileName 传入的文件名
	 * @return 返回该文件名是否在
	 */
	public FileNameDescribe parserFileNameDescribe(String fileName) {
		if (fileName == null || fileName.length() == 0) {
			return null;
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		String extension = fileName.substring(index);
		String name = fileName.substring(0, index);
		FileNameDescribe fileNameDescribe = new FileNameDescribe();
		fileNameDescribe.setExtension(extension);
		fileNameDescribe.setName(name);
		return fileNameDescribe;
	}

	public String getFileName(FileNameDescribe fileNameDescribe) {
		if (fileNameDescribe != null) {
			if (fileNameDescribe.getExtension() != null && fileNameDescribe.getExtension().length() != 0) {
				if (fileNameDescribe.getName() != null && fileNameDescribe.getName().length() != 0) {
					return String.format("%s%s", fileNameDescribe.getName(), fileNameDescribe.getExtension());
				}
			}
		}
		return null;
	}

	public String getFileName(FileLocation fileLocation) {
		if (fileLocation != null) {
			if (fileLocation.getExtension() != null && fileLocation.getExtension().length() != 0) {
				if (fileLocation.getFileUuid() != null && fileLocation.getFileUuid().length() != 0) {
					return String.format("%s%s", fileLocation.getFileUuid(), fileLocation.getExtension());
				}
			}
		}
		return null;
	}
}
