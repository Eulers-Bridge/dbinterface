package com.eulersbridge.iEngage.database.domain.Fixture;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Like;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
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
		return populateUser("gnewitt@hotmail.com", "Greg", "Newitt", "Male", "Australian", "1971", "password", nodeId, verified, inst, roles);
	}
	public static User populateUserGnewitt2()
	{
		Long nodeId=(long)2;
		boolean verified=false;
		Institution inst=populateInstUniMelb();
		String roles=SecurityConstants.USER_ROLE+','
					+SecurityConstants.ADMIN_ROLE+','
					+SecurityConstants.CONTENT_MANAGER_ROLE+','
					+SecurityConstants.RETURNING_OFFICER_ROLE;
		return populateUser("greg.newitt@unimelb.edu.au", "Greg", "Newitt", "Male", "Australian", "1971", "password", nodeId, verified, inst, roles);
	}
	public static User populateUser(String email, String firstName, String lastName, String gender, String nationality, String yearOfBirth, String password, Long id, boolean verified, Institution inst, String roles)
	{
		User user=new User(email, firstName, lastName, gender, nationality, yearOfBirth, password);
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
	
	public static Institution populateInstMonashUni()
	{
		Country country=populateCountryAust();
		Institution inst=populateInst("Monash University", "Clayton", "Victoria", country);
		inst.setNodeId((long)2);
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
	public static NewsFeed populateNewsFeed1()
	{
		return populateStudentYear((long)2);
	}
	
	public static NewsFeed populateNewsFeed2()
	{
		return populateStudentYear((long)1);
	}
	
	public static NewsFeed populateStudentYear(Long nodeId)
	{
		NewsFeed year=new NewsFeed();
		Institution inst=populateInstUniMelb();
		year.setInstitution(inst);
		year.setNodeId(nodeId);
		return year;
	}
	
	public static NewsArticle populateNewsArticle1()
	{
		Long nodeId=(long)1;
		NewsArticle initialArticle=populateNewsArticle("Test Article", "Contents of the Test Article", null, populateUserGnewitt(), Calendar.getInstance(), nodeId, populateNewsFeed2());
		initialArticle.setNewsFeed(DatabaseDataFixture.populateNewsFeed2());
		return initialArticle;
	}
	public static NewsArticle populateNewsArticle2()
	{
		Long nodeId=(long)2;
		NewsArticle initialArticle=populateNewsArticle("Test Article", "Contents of the Test Article", null, populateUserGnewitt(), Calendar.getInstance(), nodeId, populateNewsFeed2());
		initialArticle.setNewsFeed(DatabaseDataFixture.populateNewsFeed2());
		return initialArticle;
	}
	public static NewsArticle populateNewsArticle(String title, String content, Iterable<String> picture, User creator, Calendar date, Long id, NewsFeed studentYear)
	{
		NewsArticle article=new NewsArticle(title, content, picture, date, creator);
		Long nodeId=id;
		article.setNodeId(nodeId);
		article.setNewsFeed(studentYear);
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
		institutions.put(initialInst.getNodeId(), initialInst);
		initialInst=populateInstMonashUni();
		institutions.put(initialInst.getNodeId(), initialInst);
		return institutions;
	}
	
	public static HashMap<Long,NewsFeed> populateStudentYears()
	{
		HashMap<Long, NewsFeed> years=new HashMap<Long, NewsFeed>();
		years.put(new Long(1), populateNewsFeed2());
		years.put(new Long(2), populateNewsFeed1());
		return years;
	}

	public static Election populateElection1()
	{
		Long nodeId=(long)2;
		Institution inst=populateInstUniMelb();
		return populateElection(nodeId, "This election", 123456l, 123756l, 123656l, 123706l, inst);
	}
	public static Election populateElection(Long id, String title, Long starts, Long ends, Long voteStarts, Long voteEnds, Institution inst)
	{
		Election election=new Election();
		election.setNodeId(id);
		election.setTitle(title);
		election.setInstitution(inst);
		election.setStart(starts);
		election.setEnd(ends);
		election.setVotingStart(voteStarts);
		election.setVotingEnd(voteEnds);
		return election;
	}
	public static HashMap<Long,Election> populateElections()
	{
		HashMap<Long, Election> elections=new HashMap<Long, Election>();
		Election initialElection=populateElection1();
		elections.put(new Long(1), initialElection);
		return elections;
	}

}
