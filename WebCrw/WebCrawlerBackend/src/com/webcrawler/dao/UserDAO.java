package com.webcrawler.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.webcrawler.dto.UserDTO;

public class UserDAO {

	public void addUser(UserDTO user) {
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public String getUserPass(String username) {

		String pass = "";
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("select password from USER where username = :username ");
		query.setParameter("username", username);
		List list = query.list();
		
		pass = (String) list.get(0);
		
		session.getTransaction().commit();
		session.close();
		
		return pass;
	}
	
	public int getUserIdFromUserName(String username) {

		int userId = 0;
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("select userId from USER where username = :username ");
		query.setParameter("username", username);
		List list = query.list();
		
		userId = (Integer) list.get(0);
		
		session.getTransaction().commit();
		session.close();
		
		return userId;
	}
	
	private SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		return  configuration.buildSessionFactory(builder.build());
	}
}
