package com.webcrawler.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.webcrawler.dto.SessionDTO;

public class SessionDAO {

	public void addUserSession(SessionDTO userSession) {
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(userSession);
		session.getTransaction().commit();
		session.close();
	}
	
	public String getUsernameFromAuthKey(String authKey) {

		String uname = "";
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("select username from USER_SESSION where authKey = :authKey ");
		query.setParameter("authKey", authKey);
		List list = query.list();
		
		uname = (String) list.get(0);
		
		session.getTransaction().commit();
		session.close();
		
		return uname;
	}
	
	public void deleteSessionFromAuthKey(String authKey) {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("delete USER_SESSION where authKey = :authKey ");
		query.setParameter("authKey", authKey);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}
	
}
