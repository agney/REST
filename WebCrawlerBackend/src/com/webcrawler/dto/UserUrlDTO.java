package com.webcrawler.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "USER_URL")
public class UserUrlDTO {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "URL_ID")
	private int urlId;

	public int getUserId() {
		return userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

	@Override
	public String toString() {
		return "UserUrlDTO [id=" + id + ", userId=" + userId + ", urlId="
				+ urlId + "]";
	}

}
