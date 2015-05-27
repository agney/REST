package com.webcrawler.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.validator.routines.UrlValidator;

import com.webcrawler.model.Url;

@Path("/urlService")
public class URLValidation {

	@Path("/isValidURL")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response isValidURL(Url url)
	{
		System.out.println("Backend Validator"+url.getUrl());
		try
		{
			UrlValidator urlValidator = new UrlValidator();
			boolean isv = urlValidator.isValid(url.getUrl());

			if(isv==true)
			{
				return Response.ok("200").build();
			}
			else
			{
				return Response.status(400).build();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(503).build(); 
		}
	}
	
	@Path("/getTest")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test()
	{
		//System.out.println("Hello Get Validator");
		return "Hello Get Validator";
	}
}
