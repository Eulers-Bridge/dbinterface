package com.eulersbridge.iEngage.database.domain.Fixture;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Like;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.StudentYear;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.security.SecurityConstants;

public class DatabaseDataFixture 
{
	public static User populateUserGnewitt()
	{
		Long nodeId=(long)1;
		boolean verified=true;
		Institution inst=populateInstUniMelb();
		String roles=SecurityConstants.USER_ROLE;
		return populateUser("gnewitt@hotmail.com", "Greg", "Newitt", "Male", "Australian", "1971", "None", "password", nodeId, verified, inst, roles);
	}
	public static User populateUserGnewitt2()
	{
		Long nodeId=(long)2;
		boolean verified=false;
		Institution inst=populateInstUniMelb();
		String roles=SecurityConstants.USER_ROLE+','+SecurityConstants.ADMIN_ROLE;
		return populateUser("greg.newitt@unimelb.edu.au", "Greg", "Newitt", "Male", "Australian", "1971", "None", "password", nodeId, verified, inst, roles);
	}
	public static User populateUser(String email, String firstName, String lastName, String gender, String nationality, String yearOfBirth, String personality, String password, Long id, boolean verified, Institution inst, String roles)
	{
		User user=new User(email, firstName, lastName, gender, nationality, yearOfBirth, personality, password);
		Long nodeId=id;
		user.setNodeId(nodeId);
		user.setAccountVerified(verified);
		user.setInstitution(inst);
		user.setLikes(new HashSet<Like>());
		user.setRoles(roles);
		return user;
	}
	public static HashMap<Long,User> populateUsers()
	{
		HashMap<Long, User> users=new HashMap<Long, User>();
		User initialUser=populateUserGnewitt();
		users.put(new Long(1), initialUser);
		return users;
	}
	

	public static Institution populateInstUniMelb()
	{
		Country country=populateCountryAust();
		Institution inst=populateInst("University of Melbourne", "Parkville", "Victoria", country);
		inst.setNodeId((long)1);
		return inst;
	}
	
	public static Institution populateInst(String name,String campus,String state,Country country)
	{
		Institution inst=new Institution(name, campus, state, country);
		return inst;
	}
	
	public static Country populateCountryAust()
	{
		return populateCountry((long)1,"Australia");
	}
	
	public static Country populateCountry(Long nodeId,String countryName)
	{
		Country country=new Country();
		country.setCountryName(countryName);
		country.setNodeId(nodeId);
		return country;
	}
	public static StudentYear populateStudentYear2013()
	{
		return populateStudentYear((long)2, "2013");
	}
	
	public static StudentYear populateStudentYear2014()
	{
		return populateStudentYear((long)1,"2014");
	}
	
	public static StudentYear populateStudentYear(Long nodeId,String theYear)
	{
		StudentYear year=new StudentYear();
		year.setYear(theYear);
		Calendar now=Calendar.getInstance();
		year.setStart(now.getTimeInMillis());
		year.setEnd(now.getTimeInMillis()+200000);
		Institution inst=populateInstUniMelb();
//		inst.setNodeId((long)1);
		year.setInstitution(inst);
		year.setNodeId(nodeId);
		return year;
	}
	
	public static NewsArticle populateNewsArticle1()
	{
		Long nodeId=(long)1;
		NewsArticle initialArticle=populateNewsArticle("Test Article", "Contents of the Test Article", null, populateUserGnewitt(), Calendar.getInstance(), nodeId, populateStudentYear2014());
		initialArticle.setStudentYear(DatabaseDataFixture.populateStudentYear2014());
		return initialArticle;
	}
	public static NewsArticle populateNewsArticle2()
	{
		Long nodeId=(long)2;
		NewsArticle initialArticle=populateNewsArticle("Test Article", "Contents of the Test Article", null, populateUserGnewitt(), Calendar.getInstance(), nodeId, populateStudentYear2014());
		initialArticle.setStudentYear(DatabaseDataFixture.populateStudentYear2014());
		return initialArticle;
	}
	public static NewsArticle populateNewsArticle(String title, String content, Iterable<String> picture, User creator, Calendar date, Long id, StudentYear studentYear)
	{
		NewsArticle article=new NewsArticle(title, content, picture, date, creator);
		Long nodeId=id;
		article.setNodeId(nodeId);
		article.setStudentYear(studentYear);
		article.setLikes(new HashSet<Like>());
		return article;
	}
	public static Map<Long,NewsArticle> populateNewsArticles()
	{
		HashMap<Long, NewsArticle> newsArticles=new HashMap<Long, NewsArticle>();
		NewsArticle initialNews=populateNewsArticle1();
		newsArticles.put(initialNews.getNodeId(), initialNews);
		initialNews=populateNewsArticle2();
		newsArticles.put(initialNews.getNodeId(), initialNews);
		return newsArticles;
	}

	public static HashMap<Long,Country> populateCountries()
	{
		HashMap<Long, Country> countrys=new HashMap<Long, Country>();
		Country initialCountry=populateCountryAust();
		countrys.put(new Long(1), initialCountry);
		return countrys;
	}
	
	public static HashMap<Long,Institution> populateInstitutions()
	{
		HashMap<Long, Institution> institutions=new HashMap<Long, Institution>();
		Institution initialInst=populateInstUniMelb();
		Long nodeId=new Long(1);
		initialInst.setNodeId(nodeId);
		institutions.put(nodeId, initialInst);
		return institutions;
	}
	
	public static HashMap<Long,StudentYear> populateStudentYears()
	{
		HashMap<Long, StudentYear> years=new HashMap<Long, StudentYear>();
		years.put(new Long(1), populateStudentYear2014());
		years.put(new Long(2), populateStudentYear2013());
		return years;
	}

}
