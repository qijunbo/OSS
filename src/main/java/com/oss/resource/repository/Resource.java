// tag::sample[]
package com.oss.resource.repository;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Resource {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;

	private String name;

	private String path;

	private Date uploadTime;

	private String tags;

	private String mimeType;

	private String md5code;

	private String originName;

	private boolean legal = true;

	protected Resource() {
	}

	public Resource(String name, String tags, String mimeType) {
		this.name = name;
		this.tags = tags;
		this.mimeType = mimeType;
		this.uploadTime = new Date();
		this.legal = true;
	}

	public Resource(String name, String tags, String mimeType, String originName) {
		this(originName, tags, mimeType);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMd5code() {
		return md5code;
	}

	public void setMd5code(String md5code) {
		this.md5code = md5code;
	}

	public boolean isLegal() {
		return legal;
	}

	public void setLegal(boolean legal) {
		this.legal = legal;
	}

 

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", path=" + path + ", uploadTime=" + uploadTime + ", tags="
				+ tags + ", mimeType=" + mimeType + ", md5code=" + md5code + ", originName=" + originName + ", legal="
				+ legal + "]";
	}

}
