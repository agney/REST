package com.webcrawler.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.webcrawler.dto.UrlDTO;
import com.webcrawler.dto.UserUrlDTO;

public class UserUrlDAO {

	public int addUrl(UrlDTO url) {
		
		int urlId = 0;
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		Session session = factory.openSession();
		session.beginTransaction();
		urlId = (Integer) session.save(url);
		session.getTransaction().commit();
		session.close();
		
		return urlId;
	}
	
	public void addUserUrl(UserUrlDTO dto) {
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(dto);
		session.getTransaction().commit();
		session.close();

	}

	public List<String> getUrls(int userId) {
		
		List<String> urlList = new ArrayList<String>();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		Session session = factory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("select urlId from USER_URL where userId = :userId ");
		query.setParameter("userId", userId);
		List<Integer> list = query.list();
		
		for(int urlId : list) {
			query = session.createQuery("select url from URL where urlId = :urlId ");
			query.setParameter("urlId", urlId);
			urlList.add((String)query.list().get(0));
		}
		session.getTransaction().commit();
		session.close();
		
		return urlList;
	}
	
}
