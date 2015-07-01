package com.eulersbridge.iEngage.database.domain.Fixture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import com.eulersbridge.iEngage.core.events.notifications.Message;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.domain.notifications.Notification;
import com.eulersbridge.iEngage.database.domain.notifications.NotificationConstants;
import com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest;
import com.eulersbridge.iEngage.database.domain.notifications.NotificationMessage;

import org.springframework.data.neo4j.conversion.Result;

import com.eulersbridge.iEngage.database.repository.ResultImpl;
import com.eulersbridge.iEngage.security.SecurityConstants;

public class DatabaseDataFixture 
{
	public static User populateUserGnewitt()
	{
		Long nodeId=(long)1;
		boolean verified=true;
		Institution inst=populateInstUniMelb();
		String roles=SecurityConstants.USER_ROLE;
		return populateUser("gnewitt@hotmail.com", "Greg", "Newitt", "Male", "Australian", "1971", "password", nodeId, verified, inst, roles, "0447304209");
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
		return populateUser("greg.newitt@unimelb.edu.au", "Greg", "Newitt", "Male", "Australian", "1971", "password", nodeId, verified, inst, roles, "0447304209");
	}
    public static User populateUserYikai()
    {
        Long nodeId=(long)3;
        boolean verified=true;
        Institution inst=populateInstUniMelb();
        String roles=SecurityConstants.USER_ROLE;
        return populateUser("yikaig@student.unimelb.edu.au", "Yikai", "Gong", "Male", "Chinese", "1989", "password", nodeId, verified, inst, roles, "0469749222");
    }
	public static User populateUser(String email, String firstName, String lastName, String gender, String nationality, String yearOfBirth, String password, Long id, boolean verified, Institution inst, String roles, String contactNumber)
	{
		User user=new User(email, firstName, lastName, gender, nationality, yearOfBirth, password,contactNumber);
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
		Long nodeId=1l;
		Institution inst=populateInst(nodeId,"University of Melbourne", "Parkville", "Victoria", country);
		return inst;
	}
	
	public static Institution populateInstMonashUni()
	{
		Country country=populateCountryAust();
		Long nodeId=2l;
		Institution inst=populateInst(nodeId,"Monash University", "Clayton", "Victoria", country);
		return inst;
	}
	
	public static Institution populateInst(Long nodeId, String name,String campus,String state,Country country)
	{
		Institution inst=new Institution(name, campus, state, country);
		inst.setNodeId(nodeId);
		NewsFeed newsFeed=new NewsFeed();
		newsFeed.setInstitution(inst);
		newsFeed.setNodeId(nodeId+100);
		inst.setNewsFeed(newsFeed);
		return inst;
	}
	
	public static Country populateCountryAust()
	{
		ArrayList<Institution> institutions=new ArrayList<Institution>();
		return populateCountry((long)1,"Australia",institutions);
	}
	
	public static Country populateCountry(Long nodeId,String countryName, Iterable<Institution> institutions)
	{
		Country country=new Country();
		country.setCountryName(countryName);
		country.setNodeId(nodeId);
		country.setInstitutions(institutions);
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
		ArrayList<Photo> pictures=new ArrayList<Photo>();
		pictures.add(populatePhoto1());
		pictures.add(populatePhoto2());
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
	public static NewsArticle populateNewsArticle(String title, String content, Iterable<Photo> picture, User creator, Calendar date, Long id, NewsFeed studentYear)
	{
		NewsArticle article=new NewsArticle(title, content, date, creator);
		Long nodeId=id;
		article.setNodeId(nodeId);
		article.setNewsFeed(studentYear);
		article.setLikes(new HashSet<Like>());
		article.setPhotos(picture);
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
		Photo photo=populatePhoto("http://localhost:8080/photos/photo1.jpg","http://localhost:8080/photos/thumb1.jpg","Test Photo", "Contents of the Test Photo", Calendar.getInstance().getTimeInMillis(), nodeId,ownerId,3,false);
		photo.setNumOfLikes(3425l);
		return photo;
	}
	public static Photo populatePhoto2()
	{
		Long nodeId=2l;
		Long ownerId=1l;
		Photo photo=populatePhoto("http://localhost:8080/photos/photo2.jpg","http://localhost:8080/photos/thumb2.jpg","Test Photo2", "Contents of the Test Photo2", Calendar.getInstance().getTimeInMillis(), nodeId,ownerId,1,false);
		photo.setNumOfLikes(33425l);
		return photo;
	}
	public static Photo populatePhoto(String url, String thumbNailUrl, String title, String content, Long date,Long id, Long ownerId, Integer sequence, boolean inappropriateContent)
	{
		Photo photo=new Photo(url, thumbNailUrl, title, content, date, sequence,inappropriateContent);
		Long nodeId=id;
		photo.setNodeId(nodeId);
		Owner owner=new Owner(ownerId);
		photo.setOwner(owner);
		return photo;
	}
	
	public static HashMap<Long, Photo> populatePhotos()
	{
		HashMap<Long, Photo> photos=new HashMap<Long, Photo>();
		Photo initialInst=populatePhoto1();
		photos.put(initialInst.getNodeId(), initialInst);
		initialInst=populatePhoto2();
		photos.put(initialInst.getNodeId(), initialInst);
		return photos;
	}
	public static PhotoAlbum populatePhotoAlbum1()
	{
		Long nodeId=1l;
		Long ownerId=2l;
		Long creatorId=4l;
		PhotoAlbum photoAlbum=populatePhotoAlbum(nodeId,"Graduation Photo Album","Photos of Hot Graduates", "Union lawn","http://localhost:8080/thumb1.jpg",creatorId,Calendar.getInstance().getTimeInMillis(), null,ownerId);
		return photoAlbum;
	}
	public static PhotoAlbum populatePhotoAlbum2()
	{
		Long nodeId=2l;
		Long ownerId=1l;
		Long creatorId=3l;
		PhotoAlbum photoAlbum=populatePhotoAlbum(nodeId,"Fresher Photo Album","Photos of Hot Freshers", "Union Building","http://localhost:8080/thumb2.jpg",creatorId,Calendar.getInstance().getTimeInMillis(), null,ownerId);
		return photoAlbum;
	}
	public static PhotoAlbum populatePhotoAlbum(Long nodeId,String name, String description, String location, String thumbNailUrl, Long creatorId,Long created,Long modified, Long ownerId)
	{
		Owner owner=new Owner(ownerId);
		Owner creator=new Owner(creatorId);
		PhotoAlbum photoAlbum=new PhotoAlbum(name,description,location,thumbNailUrl,creator,created,owner,modified);
		photoAlbum.setNodeId(nodeId);
		photoAlbum.setOwner(owner);
		return photoAlbum;
	}
	
	public static HashMap<Long, PhotoAlbum> populatePhotoAlbums()
	{
		HashMap<Long, PhotoAlbum> photoAlbums=new HashMap<Long, PhotoAlbum>();
		PhotoAlbum initialInst=populatePhotoAlbum1();
		photoAlbums.put(initialInst.getNodeId(), initialInst);
		initialInst=populatePhotoAlbum2();
		photoAlbums.put(initialInst.getNodeId(), initialInst);
		return photoAlbums;
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
		return populateElection(nodeId, "This election", 123456l, 123756l, 123656l, 123706l, inst,"introduction 1","process 1");
	}
	public static Election populateElection2()
	{
		Long nodeId=(long)2;
		Institution inst=populateInstUniMelb();
		return populateElection(nodeId, "That election", 123555l, 123777l, 123555l, 123666l, inst,"introduction 2","process 2");
	}
	public static Election populateElection(Long id, String title, Long starts, Long ends, Long voteStarts, Long voteEnds, Institution inst, String introduction, String process)
	{
		Election election=new Election();
		election.setNodeId(id);
		election.setTitle(title);
		election.setInstitution(inst);
		election.setStart(starts);
		election.setEnd(ends);
		election.setVotingStart(voteStarts);
		election.setVotingEnd(voteEnds);
		election.setIntroduction(introduction);
		election.setProcess(process);
		return election;
	}
	public static HashMap<Long,Election> populateElections()
	{
		HashMap<Long, Election> elections=new HashMap<Long, Election>();
		elections.put(new Long(1), populateElection1());
		elections.put(2l, populateElection2());
		return elections;
	}

	public static Event populateEvent1()
	{
		Long nodeId=(long)2;
		ArrayList<Photo> pictures=new ArrayList<Photo>();
		pictures.add(populatePhoto1());
		pictures.add(populatePhoto2());

		return populateEvent(nodeId, "That event", "That event where that thing happened.", "Union Building", "Greg Newitt", "gnewitt@hotmail.com", pictures, 123666l, 123666l, 124666l, 124766l, populateNewsFeed2());
	}
	public static Event populateEvent2()
	{
		Long nodeId=(long)3;
		ArrayList<Photo> pictures=new ArrayList<Photo>();
		pictures.add(populatePhoto2());
		pictures.add(populatePhoto1());

		return populateEvent(nodeId, "That other event", "That other event where that other thing happened.", "Clyde Hotel", "Greg Newitt", "gnewitt@hotmail.com", pictures, 123766l, 123766l, 124766l, 124866l, populateNewsFeed2());
	}
	public static Event populateEvent(Long id, String name, String description, String location,String organizer, String organizerEmail, Iterable<Photo> photos,Long created, Long modified, Long starts, Long ends, NewsFeed newsFeed)
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
		event.setPhotos(photos);
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
		return populateVoteRecord(nodeId, 123458l, elec, "Union House", voter,"qrCode1");
	}
	public static VoteRecord populateVoteRecord2()
	{
		Long nodeId=(long)2;
		Election elec=populateElection2();
		User voter=populateUserGnewitt2();
		return populateVoteRecord(nodeId, 123559l, elec, "Union House", voter,"qrCode2");
	}
	public static VoteRecord populateVoteRecord(Long id, Long date, Election election, String location, User voter, String qrCode)
	{
		VoteRecord voteRecord=new VoteRecord();
		voteRecord.setNodeId(id);
		voteRecord.setDate(date);
		voteRecord.setElection(election);
		voteRecord.setLocation(location);
		voteRecord.setQrCode(qrCode);
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
	public static Poll populatePoll1()
	{
		Long nodeId=1l;
		String answers="John Curtin, Robert Menzies, Gough Whitlam, Bob Hawke, John Howard";
		return populatePoll(nodeId,"Who is your favourite PM?",answers,123214l,12312321l,42l,7449l);
	}
	public static Poll populatePoll2()
	{
		Long nodeId=2l;
		String answers="Richmond, Collingwood, Sydney, North Melbourne, Saint Kilda";
		return populatePoll(nodeId,"Who is your favourite footy team?",answers,123214l,12312321l,42l,7449l);
	}
	public static Poll populatePoll(Long nodeId,String question, String answers, Long duration, Long start, Long creatorId, Long ownerId)
	{
		Poll poll = new Poll(question,answers, start, duration);
		Owner creator=new Owner(creatorId);
		Owner owner= new Owner(ownerId);
		poll.setCreator(creator);
		poll.setOwner(owner);
		poll.setNodeId(nodeId);
		return poll;
	}
	public static HashMap<Long, Poll> populatePolls()
	{
		HashMap<Long, Poll> polls=new HashMap<Long, Poll>();
		polls.put(new Long(1), populatePoll1());
		polls.put(2l, populatePoll2());
		return polls;
	}
	
	public static ForumQuestion populateForumQuestion1()
	{
		Long nodeId=1l;
		String question="Forum question 1";
		return populateForumQuestion(nodeId,question);
	}
	public static ForumQuestion populateForumQuestion2()
	{
		Long nodeId=2l;
		String question="Forum question 2";
		return populateForumQuestion(nodeId,question);
	}
	private static ForumQuestion populateForumQuestion(Long nodeId, String question)
	{
		ForumQuestion fq=new ForumQuestion();
		fq.setForumQuestionId(nodeId);
		fq.setQuestion(question);
		return fq;
	}
	public static HashMap<Long, ForumQuestion> populateForumQuestions()
	{
		HashMap<Long, ForumQuestion> forumQuestions=new HashMap<Long, ForumQuestion>();
		forumQuestions.put(new Long(1), populateForumQuestion1());
		forumQuestions.put(2l, populateForumQuestion2());
		return forumQuestions;
	}

	public static PollAnswer populatePollAnswer1()
	{
		Long nodeId=9l;
		Integer answer=1;
		User answerer=populateUserGnewitt();
		Poll poll=populatePoll1();
		Long timestamp=Calendar.getInstance().getTimeInMillis();
		return populatePollAnswer(nodeId,poll,answerer,answer,timestamp);
	}
	public static PollAnswer populatePollAnswer2()
	{
		Long nodeId=9l;
		Integer answer=1;
		User answerer=populateUserGnewitt();
		Poll poll=populatePoll2();
		Long timestamp=Calendar.getInstance().getTimeInMillis();
		return populatePollAnswer(nodeId,poll,answerer,answer,timestamp);
	}
	public static PollAnswer populatePollAnswer(Long id, Poll poll, User answerer, Integer answer, Long timeStamp)
	{
		PollAnswer fq=new PollAnswer(new Owner(answerer.getNodeId()),poll,answer);
		fq.setNodeId(id);
		return fq;
	}
	
	public static Result<PollResultTemplate> populatePollResultDetails1()
	{
		ArrayList<PollResultTemplate> prts=new ArrayList<PollResultTemplate>();
		prts.add(0,new PollResultImpl(0,165));
		prts.add(1,new PollResultImpl(1,95));
		prts.add(2,new PollResultImpl(2,145));
		prts.add(3,new PollResultImpl(3,115));
		prts.add(4,new PollResultImpl(4,15));
		ResultImpl<PollResultTemplate> results=new ResultImpl<PollResultTemplate>(prts);
		return populatePollResultDetails(results);
	}
	public static Result<PollResultTemplate> populatePollResultDetails2()
	{	
		LinkedList<PollResultTemplate> prts=new LinkedList<PollResultTemplate>();
		prts.add(new PollResultImpl(0,165));
		prts.add(new PollResultImpl(2,145));
		prts.add(new PollResultImpl(3,115));
		ResultImpl<PollResultTemplate> results=new ResultImpl<PollResultTemplate>(prts);
		return populatePollResultDetails(results);
	}
	public static Result<PollResultTemplate> populatePollResultDetails(Result<PollResultTemplate> results)
	{
		return results;
	}

	public static Position populatePosition1()
	{
		Long nodeId=9l;
		String position="Shit kicker";
		Election election=populateElection1();
		String description="This person does the shitkicking.";
		return populatePosition(nodeId,position,description,election);
	}
	public static Position populatePosition2()
	{
		Long nodeId=19l;
		String position="Piss taker";
		Election election=populateElection1();
		String description="This person does the pisstaking.";
		return populatePosition(nodeId,position,description,election);
	}
    public static Position populatePosition(Long id, String name, String description, Election election)
    {
    	Position position = new Position();
        position.setNodeId(id);
        position.setName(name);
        position.setDescription(description);
        position.setElection(election);
        return position;
    }
	public static HashMap<Long, Position> populatePositions()
	{
		HashMap<Long, Position> positions=new HashMap<Long, Position>();
		Position initialPosition=populatePosition1();
		positions.put(initialPosition.getNodeId(), initialPosition);
		initialPosition=populatePosition2();
		positions.put(initialPosition.getNodeId(), initialPosition);
		return positions;
	}

    public static Badge populateBadge(Long id, String name, String description, Long xpValue)
    {
        Badge badge = new Badge();
        badge.setNodeId(id);
        badge.setName(name);
        badge.setDescription(description);
        badge.setXpValue(xpValue);
        return badge;
    }

    public static Badge populateBadge1()
    {
        Long id = 100l;
        String name = "Badge name 1";
        String description = "Badge description 1";
        Long xpValue = 150l;
        return populateBadge(id, name, description, xpValue);
    }
    public static Badge populateBadge2()
    {
        Long id = 101l;
        String name = "Badge name 2";
        String description = "Badge description 2";
        Long xpValue = 250l;
        return populateBadge(id, name, description, xpValue);
    }
	public static HashMap<Long, Badge> populateBadges()
	{
		HashMap<Long, Badge> badges=new HashMap<Long, Badge>();
		Badge initialBadge=populateBadge1();
		badges.put(initialBadge.getNodeId(), initialBadge);
		initialBadge=populateBadge2();
		badges.put(initialBadge.getNodeId(), initialBadge);
		return badges;
	}
    
	public static Candidate populateCandidate1()
	{
		Long candidateId=43234l;
		String information="I am a 3rd year Law student looking to pad out my CV.";
		String policyStatement="I will do anything that benefits me.";
		Ticket ticket=populateTicket(34l, "New Ticket1", "logo1", "information1", null, populateElection1(), "NT1",12l);
		Candidate candidate=populateCandidate(candidateId,information,policyStatement,populateUserGnewitt(),populatePosition1(), ticket);
		return candidate;
	}

	public static Candidate populateCandidate2()
	{
		Long candidateId=43235l;
		String information="I am a 2nd year Law student looking to pad out my CV.";
		String policyStatement="I will do anything that benefits my family.";
		Ticket ticket=populateTicket(35l, "New Ticket2", "logo2", "information2", null, populateElection2(), "NT2",12l);
		Candidate candidate=populateCandidate(candidateId,information,policyStatement,populateUserGnewitt2(),populatePosition2(), ticket);
		return candidate;
	}

	public static Candidate populateCandidate(Long candidateId, String information, String policyStatement, User user, Position position, Ticket ticket)
	{
		Candidate candidate=new Candidate(candidateId, information, policyStatement, user, position, ticket);
		return candidate;
	}
	public static HashMap<Long, Candidate> populateCandidates()
	{
		HashMap<Long, Candidate> candidates=new HashMap<Long, Candidate>();
		Candidate initialCandidate=populateCandidate1();
		candidates.put(initialCandidate.getNodeId(), initialCandidate);
		initialCandidate=populateCandidate2();
		candidates.put(initialCandidate.getNodeId(), initialCandidate);
		return candidates;
	}
	
	public static Task populateTask1()
	{
		return populateTask(453l,"Read an article.","This task involves reading an article from the newsfeed.",500);
	}

	public static Task populateTask2()
	{
		return populateTask(45l,"Respond to a poll.","This task involves responding to a poll.",1500);
	}

	public static Task populateTask(Long taskId, String action, String description, Integer xpValue)
	{
		Task task=new Task(taskId,action,description,xpValue);
		return task;
	}
	public static HashMap<Long, Task> populateTasks()
	{
		HashMap<Long, Task> tasks=new HashMap<Long, Task>();
		Task initialTask=populateTask1();
		tasks.put(initialTask.getNodeId(), initialTask);
		initialTask=populateTask2();
		tasks.put(initialTask.getNodeId(), initialTask);
		return tasks;
	}

	public static Ticket populateTicket1()
	{
		LinkedList<Candidate> candidates=new LinkedList<Candidate>();
		candidates.add(populateCandidate1());
		Long numberOfSupporters=18l;
		return populateTicket(234l, "Old Crew", "OC", "We are the Old Crew", candidates, populateElection1(), "GRN", numberOfSupporters);
	}

	public static Ticket populateTicket2()
	{
		LinkedList<Candidate> candidates=new LinkedList<Candidate>();
		candidates.add(populateCandidate1());
		candidates.add(populateCandidate2());
		Long numberOfSupporters=15l;
		return populateTicket(235l, "New Start", "NS", "We are the New Start", candidates, populateElection2(), "GRN", numberOfSupporters);
	}

	public static Ticket populateTicket(Long ticketId, String name, String logo, String information, Iterable<Candidate> candidates, Election election, String characterCode, Long numberOfSupporters)
	{
		Ticket ticket=new Ticket(ticketId, name, logo, information, candidates, election, characterCode);
		ticket.setNumberOfSupporters(numberOfSupporters);
		return ticket;
	}
	public static HashMap<Long, Ticket> populateTickets()
	{
		HashMap<Long, Ticket> tickets=new HashMap<Long, Ticket>();
		Ticket initialTicket=populateTicket1();
		tickets.put(initialTicket.getNodeId(), initialTicket);
		initialTicket=populateTicket2();
		tickets.put(initialTicket.getNodeId(), initialTicket);
		return tickets;
	}
	public static VotingLocation populateVotingLocation1()
	{
		Owner owner = new Owner(populateInstUniMelb().getNodeId());
		return populateVotingLocation(237l, "Union Building", "Ground Floor of the Union Building", owner);
	}
	public static VotingLocation populateVotingLocation2()
	{
		Owner owner = new Owner(populateInstUniMelb().getNodeId());
		return populateVotingLocation(238l, "Baillieu Library", "Ground Floor of the Baillieu Library", owner);
	}
	public static VotingLocation populateVotingLocation3()
	{
		Owner owner = new Owner(populateInstUniMelb().getNodeId());
		return populateVotingLocation(239l, "ERC Library", "Ground Floor of the ERC Library", owner);
	}
	public static VotingLocation populateVotingLocation(Long locationId, String name, String description, Owner owner)
	{
		VotingLocation votingLocation=new VotingLocation(locationId, name, description, owner);
		return votingLocation;
	}
	public static HashMap<Long, VotingLocation> populateVotingLocations()
	{
		HashMap<Long, VotingLocation> votingLocations=new HashMap<Long, VotingLocation>();
		VotingLocation initialVotingLocation=populateVotingLocation1();
		votingLocations.put(initialVotingLocation.getNodeId(), initialVotingLocation);
		initialVotingLocation=populateVotingLocation2();
		votingLocations.put(initialVotingLocation.getNodeId(), initialVotingLocation);
		initialVotingLocation=populateVotingLocation3();
		votingLocations.put(initialVotingLocation.getNodeId(), initialVotingLocation);
		return votingLocations;
	}
	
	public static Personality populatePersonality1()
	{
		Long nodeId=45l;
		float emotionalStability=3.4F;
		float agreeableness=4.3F;
		float openess=4.1F;
		float conscientiousness=3.8F;
		float extroversion=2.5F;
		return populatePersonality(nodeId,extroversion, agreeableness, conscientiousness, emotionalStability, openess);
	}

	public static Personality populatePersonality2()
	{
		Long nodeId=45l;
		float emotionalStability=2.4F;
		float agreeableness=3.3F;
		float openess=1.1F;
		float conscientiousness=2.8F;
		float extroversion=4.5F;
		return populatePersonality(nodeId,extroversion, agreeableness, conscientiousness, emotionalStability, openess);
	}

	public static Personality populatePersonality(Long nodeId,Float extroversion, Float agreeableness, Float conscientiousness, Float emotionalStability, Float openess)
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		personality.setNodeId(nodeId);
		return personality;
	}
	public static TaskComplete populateTaskComplete1()
	{		
		Long nodeId=1467l;
		User user=populateUserGnewitt();
		Task task=populateTask1();
		Long date=1425609701874l;
		return populateTaskComplete(nodeId, user, task, date);
	}
	public static TaskComplete populateTaskComplete2()
	{
		Long nodeId=1468l;
		User user=populateUserGnewitt2();
		Task task=populateTask2();
		Long date=1425609701878l;
		return populateTaskComplete(nodeId, user, task, date);
	}
	public static TaskComplete populateTaskComplete(Long nodeId, User user, Task task, Long date)
	{
		TaskComplete tc=new TaskComplete();
		tc.setNodeId(nodeId);
		tc.setUser(user);
		tc.setTask(task);
		tc.setDate(date);
		return tc;
	}
	public static HashMap<Long, TaskComplete> populateCompleteTasks()
	{
		HashMap<Long, TaskComplete> tasks=new HashMap<Long, TaskComplete>();
		TaskComplete initialTask=populateTaskComplete1();
		tasks.put(initialTask.getNodeId(), initialTask);
		return tasks;
	}
	
	public static BadgeComplete populateBadgeComplete1()
	{
		Long nodeId=1467l;
		User user=populateUserGnewitt();
		Badge badge=populateBadge1();
		Long date=1425609701874l;
		return populateBadgeComplete(nodeId, user, badge, date);
	}
	
	public static BadgeComplete populateBadgeComplete2()
	{
		Long nodeId=1468l;
		User user=populateUserGnewitt2();
		Badge badge=populateBadge2();
		Long date=1425609701878l;
		return populateBadgeComplete(nodeId, user, badge, date);
	}
	public static BadgeComplete populateBadgeComplete(Long nodeId, User user, Badge badge, Long date)
	{
		BadgeComplete tc=new BadgeComplete();
		tc.setNodeId(nodeId);
		tc.setUser(user);
		tc.setBadge(badge);
		tc.setDate(date);
		return tc;
	}
	public static HashMap<Long, BadgeComplete> populateCompleteBadges()
	{
		HashMap<Long, BadgeComplete> badges=new HashMap<Long, BadgeComplete>();
		BadgeComplete initialTask=populateBadgeComplete1();
		badges.put(initialTask.getNodeId(), initialTask);
		return badges;
	}

	public static ContactRequest populateContactRequest1()
	{
		Long nodeId=123l;
		String contactDetails=populateUserGnewitt().getContactNumber();
		User user=populateUserGnewitt2();
		Long requestDate=Calendar.getInstance().getTimeInMillis();
		Boolean accepted=false,rejected=false;
		Long responseDate=requestDate+32324;
		return populateContactRequest(nodeId,contactDetails,user,accepted,rejected,requestDate,responseDate);
	}
	public static ContactRequest populateContactRequest2()
	{
		Long nodeId=128l;
		String contactDetails=populateUserGnewitt2().getEmail();
		User user=populateUserGnewitt();
		Long requestDate=Calendar.getInstance().getTimeInMillis();
		Boolean accepted=false,rejected=false;
		Long responseDate=requestDate+323324;
		return populateContactRequest(nodeId,contactDetails,user,accepted,rejected,requestDate,responseDate);
	}
	public static ContactRequest populateContactRequest(Long nodeId, String contactDetails, User user, Boolean accepted, Boolean rejected, Long requestDate, Long responseDate)
	{
		ContactRequest contactRequest=new ContactRequest();
		contactRequest.setContactDetails(contactDetails);
		contactRequest.setNodeId(nodeId);
		contactRequest.setRejected(rejected);
		contactRequest.setAccepted(accepted);
		contactRequest.setRequestDate(requestDate);
		contactRequest.setResponseDate(responseDate);
		contactRequest.setUser(user);
		return contactRequest;
	}
	public static HashMap<Long, ContactRequest> populateContactRequests()
	{
		HashMap<Long, ContactRequest> tasks=new HashMap<Long, ContactRequest>();
		ContactRequest initialTask=populateContactRequest1();
		tasks.put(initialTask.getNodeId(), initialTask);
		initialTask=populateContactRequest2();
		tasks.put(initialTask.getNodeId(), initialTask);
		return tasks;
	}

	public static Contact populateContact1()
	{
		Long nodeId=7123l;
		User contactor=populateUserGnewitt();
		User contactee=populateUserGnewitt2();
		return populateContact(nodeId,contactor,contactee);
	}
	public static Contact populateContact2()
	{
		Long nodeId=7128l;
		User contactor=populateUserGnewitt2();
		User contactee=populateUserGnewitt();
		return populateContact(nodeId,contactor,contactee);
	}
	public static Contact populateContact(Long nodeId, User contactor, User contactee)
	{
		Contact contact=new Contact();
		contact.setNodeId(nodeId);
		contact.setContactor(contactor);
		contact.setContactee(contactee);
		contact.setTimestamp(Calendar.getInstance().getTimeInMillis());
		return contact;
	}

    public static Support populateSupport(User user, Ticket ticket){
        Support support = new Support();
        support.setId(0l);
        support.setSupporter(user);
        support.setTicket(ticket);
        support.setTimeStamp(0l);
        return support;
    }
    
	public static Notification populateNotification1()
	{
		Boolean read=false;
		String type=NotificationConstants.MESSAGE;
		User user=populateUserGnewitt2();
		Long nodeId=23342l;
		Long timeStamp=Calendar.getInstance().getTimeInMillis();
		return populateNotificationMessage(nodeId, read, timeStamp, type, user,"test message 1");
	}

	public static Notification populateNotification2()
	{
		Boolean read=true;
		String type=NotificationConstants.MESSAGE;
		User user=populateUserGnewitt();
		Long nodeId=2342l;
		Long timeStamp=Calendar.getInstance().getTimeInMillis();
		return populateNotificationMessage(nodeId, read, timeStamp, type, user,"test message 2");
	}

	public static Notification populateNotification(Long nodeId, Boolean read, Long timestamp, String type, User user)
	{
		Notification notification=new Notification();
		notification.setNodeId(nodeId);
		notification.setRead(read);
		notification.setTimestamp(timestamp);
		notification.setType(type);
		notification.setUser(user);
		return notification;
	}
	public static NotificationMessage populateNotificationMessage(Long nodeId, Boolean read, Long timestamp, String type, User user,String message)
	{
		NotificationMessage notification=new NotificationMessage();
		notification.setNodeId(nodeId);
		notification.setRead(read);
		notification.setTimestamp(timestamp);
		notification.setType(type);
		notification.setUser(user);
		notification.setMessage(message);
		return notification;
	}
	public static HashMap<Long, Notification> populateNotifications()
	{
		HashMap<Long, Notification> notifications=new HashMap<Long, Notification>();
		Notification initialInst=populateNotification1();
		notifications.put(initialInst.getNodeId(), initialInst);
		initialInst=populateNotification2();
		notifications.put(initialInst.getNodeId(), initialInst);
		return notifications;
	}

	public static NotificationContactRequest populateNotificationContactRequest1()
	{
		Boolean read=true;
		String type="contactRequest";
		User user=populateUserGnewitt();
		Long nodeId=2342l;
		Long timeStamp=Calendar.getInstance().getTimeInMillis();
		ContactRequest contactRequest=populateContactRequest1();
		return populateNotificationContactRequest(nodeId, read, timeStamp, type, user,contactRequest);
	}

	public static NotificationContactRequest populateNotificationContactRequest(Long nodeId, Boolean read, Long timestamp, String type, User user, ContactRequest contactRequest)
	{
		NotificationContactRequest ncr=new NotificationContactRequest();
		ncr.setContactRequest(contactRequest);
		ncr.setNodeId(nodeId);
		ncr.setRead(read);
		ncr.setTimestamp(timestamp);
		ncr.setType(type);
		ncr.setUser(user);

		return ncr;
	}

    public static Owner populateOwner1()
    {
        Owner object = new Owner(44l);
        return object;
    }

    public static Owner populateOwner2()
    {
        Owner object = new Owner(4443l);
        return object;
    }

    public static Comment populateComment1()
    {
        Owner object=populateOwner1();
		User user=populateUserGnewitt();
		Comment comment = populateComment(243l, user, object, "A comment", 453l);
        return comment;
    }

    public static Comment populateComment2()
    {
        Owner object=populateOwner2();
		User user=populateUserYikai();
		Comment comment = populateComment(251l, user, object, "Another comment",4655l);
        return comment;
    }

    public static Comment populateComment(Long nodeId, User user, Owner object, String contents, Long timestamp)
    {
        Comment comment = new Comment();
        comment.setNodeId(nodeId);
        comment.setUser(user);
        comment.setTarget(object);
        comment.setContent(contents);
        comment.setTimestamp(timestamp);
        return comment;
    }
	public static HashMap<Long, Comment> populateComments()
	{
		HashMap<Long, Comment> comments=new HashMap<Long, Comment>();
		Comment initialInst=populateComment1();
		comments.put(initialInst.getNodeId(), initialInst);
		initialInst=populateComment2();
		comments.put(initialInst.getNodeId(), initialInst);
		return comments;
	}

}
