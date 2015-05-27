package com.webcrawler.service;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.webcrawler.dao.SessionDAO;
import com.webcrawler.dao.UserDAO;
import com.webcrawler.dto.SessionDTO;
import com.webcrawler.dto.UserDTO;
import com.webcrawler.model.User;
import com.webcrawler.util.AuthKeyUtil;

@Path("/userService")
public class UserService {

	@Path("/doLogin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doLogin(User user) throws SQLException{

		//System.out.println(user);

		UserDAO dao = new UserDAO();
		String pass = dao.getUserPass(user.getUsername());


		if(pass.equals(user.getPassword()))
		{
			try{
				JSONObject jso = new JSONObject();

				AuthKeyUtil aku = new AuthKeyUtil();
				String authKey = aku.generateAuthKey();

				SessionDTO sessionDTO = new SessionDTO();

				sessionDTO.setUsername(user.getUsername());
				sessionDTO.setAuthKey(authKey);

				SessionDAO sessionDAO = new SessionDAO();
				sessionDAO.addUserSession(sessionDTO);

				jso.put("StatusCode", 200);
				jso.put("AuthKey", authKey);

				return Response.ok(jso.toString()).build();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return Response.status(503).build();
			}

		}
		else
		{
			return Response.status(403).build();
		}
	}

	@Path("/doRegister")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doRegister(User user){

		//System.out.println(user);

		try{
			UserDTO dto = new UserDTO();
			dto.setEmail(user.getEmail());
			dto.setPassword(user.getPassword());
			dto.setUsername(user.getUsername());

			UserDAO dao = new UserDAO();
			dao.addUser(dto);

			return Response.ok("200").build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(503).build();  // check
		}
	}

	/*@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String doSomething()
	{
		//System.out.println("Web Crawler");
		return "Hello";
	}*/
}
