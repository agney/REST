package com.webcrawler.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.webcrawler.dao.SessionDAO;
import com.webcrawler.dao.UserDAO;
import com.webcrawler.dao.UserUrlDAO;
import com.webcrawler.dto.UrlDTO;
import com.webcrawler.dto.UserUrlDTO;
import com.webcrawler.model.Url;

@Path("/crawlingService")
public class CrawlingService {

	@Path("/addUrl")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUrl(Url url){
		
		//System.out.println(url);

		try
		{
			UserDAO userDAO = new UserDAO();

			UrlDTO urlDTO = new UrlDTO();
			urlDTO.setUrl(url.getUrl());

			UserUrlDAO dao = new UserUrlDAO();
			int urlId = dao.addUrl(urlDTO);

			SessionDAO sessionDAO = new SessionDAO();
			String username = sessionDAO.getUsernameFromAuthKey(url.getAuthKey());

			UserUrlDTO userUrlDTO = new UserUrlDTO();
			userUrlDTO.setUrlId(urlId);
			userUrlDTO.setUserId(userDAO.getUserIdFromUserName(username));

			dao.addUserUrl(userUrlDTO);

			URLStatus urlStat = new URLStatus();
			int responseCode = urlStat.getURLStatus(url.getUrl());

			return Response.ok(String.valueOf(responseCode)).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(503).build();
		}
	}
	
	@Path("/getUrls")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUrls(String authKey)
	{
		//System.out.println("prashant: -->"+authKey);
		try
		{
			SessionDAO sessionDAO = new SessionDAO();
			String username = sessionDAO.getUsernameFromAuthKey(authKey);

			UserDAO userDAO = new UserDAO();
			int userId = userDAO.getUserIdFromUserName(username);

			UserUrlDAO dao = new UserUrlDAO();
			List<String> list = dao.getUrls(userId);

			JSONObject jso = null;
			JSONArray jsa = new JSONArray();
			URLStatus urlStat = new URLStatus();
			int status = 0;

			for (String i : list) {
				status = urlStat.getURLStatus(i);
				jso = new JSONObject();
				jso.put("url", i);
				jso.put("status", status);
				jsa.put(jso);
			}

			return Response.ok(jsa.toString()).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(503).build();
		}
	}
	
	@Path("/getUrlStatus")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUrlStatus(String url){
		
		//System.out.println(url);
		try
		{
			URLStatus urlStat = new URLStatus();
			int responseCode = urlStat.getURLStatus(url);
			return Response.ok(String.valueOf(responseCode)).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(503).build();
		}
	}
	
	@Path("/clearSession")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response clearSession(String authKey){
		
		//System.out.println(authKey);
		try
		{
			SessionDAO dao = new SessionDAO();
			dao.deleteSessionFromAuthKey(authKey);
			return Response.ok("200").build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(503).build();
		}
	}
}
