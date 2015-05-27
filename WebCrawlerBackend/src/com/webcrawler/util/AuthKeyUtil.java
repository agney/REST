package com.webcrawler.util;

import java.util.UUID;

public class AuthKeyUtil {

	public String generateAuthKey() {
		return UUID.randomUUID().toString();
	}

}
