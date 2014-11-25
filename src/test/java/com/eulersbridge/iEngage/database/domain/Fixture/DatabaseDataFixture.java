package com.eulersbridge.iEngage.database.domain.Fixture;

import java.util.ArrayList;
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
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VoteRecord;
import com.eulersbridge.iEngage.database.domain.VoteReminder;
import com.eulersbridge.iEngage.database.domain.Event;
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
		return populateNewsFeed((long)2);
	}
	
	public static NewsFeed populateNewsFeed2()
	{
		return populateNewsFeed((long)1);
	}
	
	public static NewsFeed populateNewsFeed(Long nodeId)
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
		ArrayList<String> pictures=new ArrayList<String>();
		pictures.add("http://localhost:8080/testPictures/picture2.jpg");
		pictures.add("http://localhost:8080/testPictures/picture.jpg");
		NewsArticle initialArticle=populateNewsArticle("Test Article", "Contents of the Test Article", pictures, populateUserGnewitt(), Calendar.getInstance(), nodeId, populateNewsFeed2());
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

	public static Photo populatePhoto1()
	{
		Long nodeId=1l;
		Long ownerId=2l;
		Photo photo=populatePhoto("http://localhost:8080/photos/photo1.jpg","Test Photo", "Contents of the Test Photo", Calendar.getInstance().getTimeInMillis(), nodeId,ownerId);
		return photo;
	}
	public static Photo populatePhoto2()
	{
		Long nodeId=2l;
		Long ownerId=1l;
		Photo photo=populatePhoto("http://localhost:8080/photos/photo2.jpg","Test Photo2", "Contents of the Test Photo2", Calendar.getInstance().getTimeInMillis(), nodeId,ownerId);
		return photo;
	}
	public static Photo populatePhoto(String url, String title, String content, Long date,Long id, Long ownerId)
	{
		Photo photo=new Photo(url, title, content, date);
		Long nodeId=id;
		photo.setNodeId(nodeId);
		Owner owner=new Owner(ownerId);
		photo.setOwner(owner);
		return photo;
	}
	
	public static PhotoAlbum populatePhotoAlbum1()
	{
		Long nodeId=1l;
		Long ownerId=2l;
		Long creatorId=4l;
		PhotoAlbum photoAlbum=populatePhotoAlbum(nodeId,"Graduation Photo Album","Photos of Hot Graduates", "Union lawn",creatorId,Calendar.getInstance().getTimeInMillis(), null,ownerId);
		return photoAlbum;
	}
	public static PhotoAlbum populatePhotoAlbum2()
	{
		Long nodeId=2l;
		Long ownerId=1l;
		Long creatorId=3l;
		PhotoAlbum photoAlbum=populatePhotoAlbum(nodeId,"Fresher Photo Album","Photos of Hot Freshers", "Union Building",creatorId,Calendar.getInstance().getTimeInMillis(), null,ownerId);
		return photoAlbum;
	}
	public static PhotoAlbum populatePhotoAlbum(Long nodeId,String name, String description, String location, Long creatorId,Long created,Long modified, Long ownerId)
	{
		Owner owner=new Owner(ownerId);
		Owner creator=new Owner(creatorId);
		PhotoAlbum photoAlbum=new PhotoAlbum(name,description,location,creator,created,owner,modified);
		photoAlbum.setNodeId(nodeId);
		photoAlbum.setOwner(owner);
		return photoAlbum;
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
		Long nodeId=(long)1;
		Institution inst=populateInstUniMelb();
		return populateElection(nodeId, "This election", 123456l, 123756l, 123656l, 123706l, inst);
	}
	public static Election populateElection2()
	{
		Long nodeId=(long)2;
		Institution inst=populateInstUniMelb();
		return populateElection(nodeId, "That election", 123555l, 123777l, 123555l, 123666l, inst);
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
		elections.put(new Long(1), populateElection1());
		elections.put(2l, populateElection2());
		return elections;
	}

	public static Event populateEvent1() {
		Long nodeId=(long)2;
		return populateEvent(nodeId, "That event", "That event where that thing happened.", "Union Building", "Greg Newitt", "gnewitt@hotmail.com", 123666l, 123666l, 124666l, 124766l, populateNewsFeed2());
	}
	public static Event populateEvent2()
	{
		Long nodeId=(long)2;
		return populateEvent(nodeId, "That other event", "That other event where that other thing happened.", "Clyde Hotel", "Greg Newitt", "gnewitt@hotmail.com", 123766l, 123766l, 124766l, 124866l, populateNewsFeed2());
	}
	public static Event populateEvent(Long id, String name, String description, String location,String organizer, String organizerEmail, Long created, Long modified, Long starts, Long ends, NewsFeed newsFeed)
	{
		Event event=new Event();
		event.setEventId(id);
		event.setName(name);
		event.setStarts(starts);
		event.setEnds(ends);
		event.setDescription(description);
		event.setLocation(location);
		event.setOrganizer(organizer);
		event.setOrganizerEmail(organizerEmail);
		event.setCreated(created);
		event.setModified(modified);
		event.setNewsFeed(newsFeed);
		return event;
	}
	public static HashMap<Long,Event> populateEvents()
	{
		HashMap<Long, Event> events=new HashMap<Long, Event>();
		events.put(new Long(1), populateEvent1());
		events.put(2l, populateEvent2());
		return events;
	}

	public static VoteReminder populateVoteReminder1()
	{
		Long nodeId=(long)1;
		Election elec=populateElection1();
		User voter=populateUserGnewitt();
		return populateVoteReminder(nodeId, 123456l, elec, "Union House", 123706l, voter);
	}
	public static VoteReminder populateVoteReminder2()
	{
		Long nodeId=(long)2;
		Election elec=populateElection2();
		User voter=populateUserGnewitt2();
		return populateVoteReminder(nodeId, 123556l, elec, "Union House", 121706l, voter);
	}
	public static VoteReminder populateVoteReminder(Long id, Long date, Election election, String location, Long timestamp, User voter)
	{
		VoteReminder voteReminder=new VoteReminder();
		voteReminder.setNodeId(id);
		voteReminder.setDate(date);
		voteReminder.setElection(election);
		voteReminder.setLocation(location);
		voteReminder.setTimestamp(timestamp);
		voteReminder.setVoter(voter);
		return voteReminder;
	}
	public static HashMap<Long,VoteReminder> populateVoteReminders()
	{
		HashMap<Long, VoteReminder> voteReminders=new HashMap<Long, VoteReminder>();
		voteReminders.put(new Long(1), populateVoteReminder1());
		voteReminders.put(2l, populateVoteReminder2());
		return voteReminders;
	}

	public static VoteRecord populateVoteRecord1()
	{
		Long nodeId=(long)1;
		Election elec=populateElection1();
		User voter=populateUserGnewitt();
		return populateVoteRecord(nodeId, 123458l, elec, "Union House", voter);
	}
	public static VoteRecord populateVoteRecord2()
	{
		Long nodeId=(long)2;
		Election elec=populateElection2();
		User voter=populateUserGnewitt2();
		return populateVoteRecord(nodeId, 123559l, elec, "Union House", voter);
	}
	public static VoteRecord populateVoteRecord(Long id, Long date, Election election, String location, User voter)
	{
		VoteRecord voteRecord=new VoteRecord();
		voteRecord.setNodeId(id);
		voteRecord.setDate(date);
		voteRecord.setElection(election);
		voteRecord.setLocation(location);
		voteRecord.setVoter(voter);
		return voteRecord;
	}
	public static HashMap<Long,VoteRecord> populateVoteRecords()
	{
		HashMap<Long, VoteRecord> voteRecords=new HashMap<Long, VoteRecord>();
		voteRecords.put(new Long(1), populateVoteRecord1());
		voteRecords.put(2l, populateVoteRecord2());
		return voteRecords;
	}
}
