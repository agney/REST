Project Details

Project : Crawler
Author : Agney prashant

Description:
This is HTML5+js+bootstrap(FrontEnd)/J2EE(BackEnd)+MySQL Application

Features:
- User can Register and Login , and specify maximum 10 Urls for Crawling and health check.
- User will not be able to add invalid URL as part of security feature.
- This Application is having very easy UI so User can watch and Learn as they use.
--------------------------------------------------------------------------------------------
Prerequisites To Run/Test this project.

0. JDK/JRE 1.7
1. MySQL Server
2. MySQL credentials
	user-name: root
	password:   (For Personal use i haven't kept password)
	(Note: If your MySQL Installation is having some other password, 
	 then please change hibernate.cfg.xml inside WebCrawlerBackend/src accordingly)
	 
3. Apache tomcat server 7.0.59

4.Must Have
 - Tomcat server runs on port 8080
 - MySQL server runs on port 3306
 
5. There is only One SQL Query Needed before using the Application. All the tables of Application will be made automatically (Through Hibernate)

CREATE DATABASE crawler;
--------------------------------------------------------------------

Installation For Running/Testing Project:

 0. Get Above prerequisites ready.
 1. Import following Projects in Your Eclipse IDE
    (i) WebCrawler
	(ii) WebCrawlerBackend
 
 2. Add (i) and (ii) to Tomcat Server. 
 3. Restart tomcat server.
 
 4. System Ready!!!

 URLs : 
 
 Register To System :  http://localhost:8080/WebCrawler/register.html 
 Login To system    :  http://localhost:8080/WebCrawler/index.html	
