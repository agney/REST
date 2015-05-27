package com.webcrawler.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "URL")
public class UrlDTO {
	
	@Id
	@GeneratedValue
	@Column(name = "URL_ID")
	private int urlId;
	
	@Column(name = "URL")
	private String url;

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "UrlDTO [urlId=" + urlId + ", url=" + url + "]";
	}

}
