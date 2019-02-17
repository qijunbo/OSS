package com.oss.resource;

public class ResourceLink {
	
	private String uuid;
	
	private String link;

	public ResourceLink(String uuid, String link) {
		super();
		this.uuid = uuid;
		this.link = link;
	}

	public ResourceLink() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	

}
