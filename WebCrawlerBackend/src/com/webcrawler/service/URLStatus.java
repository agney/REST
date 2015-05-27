package com.webcrawler.service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class URLStatus {

	URL url;
	HttpURLConnection huc;
	
	public int getURLStatus(String urlTemp) {
		
		int responseCode = -1;
		try {
			url = new URL(urlTemp);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
			//System.out.println(huc.toString());
			responseCode = huc.getResponseCode();
			//System.out.println(urlTemp+"-->"+responseCode);
		}
		catch(ConnectException c)
		{
			responseCode = 408;
		}
		catch (UnknownHostException e) {
			responseCode = -1;
		}
		catch (IOException e) {
			e.printStackTrace();
			if(null != huc)
				huc.disconnect();
		}
		
		return responseCode;
	}
	
	/*
	 * TEST Method
	 */
	/*public static void main(String[] args) {
		URLStatus us = new URLStatus();
		System.out.println(us.getURLStatus("http://www.kkr.com"));
	}*/
}
