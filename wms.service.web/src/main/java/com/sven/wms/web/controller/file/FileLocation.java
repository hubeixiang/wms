package com.sven.wms.web.controller.file;

import java.io.Serializable;

/**
 * @author sven
 * @date 2019/10/29 9:57
 */
public class FileLocation implements IExtensionOper, Serializable {
	private final static long serialVersionUID = 128L;
	//文件后缀
	private String extension;
	//文件唯一定位标志
	private String fileUuid;
	//服务标志,默认为空.标志文件在服务上存储的目录区分,可能不同的服务或者接口会存储上传的文件到不同的目录下
	private String service;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFileUuid() {
		return fileUuid;
	}

	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("FileLocation[extension=").append(extension).append(",fileUuid=").append(fileUuid).append(",service=")
				.append(service).append("]").toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((fileUuid == null) ? 0 : fileUuid.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileLocation other = (FileLocation) obj;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (fileUuid == null) {
			if (other.fileUuid != null)
				return false;
		} else if (!fileUuid.equals(other.fileUuid))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}
}
