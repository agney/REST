package com.webcrawler.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Url {

	private String authKey;
	private String url;
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Url [authKey=" + authKey + ", url=" + url + "]";
	}
	
}
