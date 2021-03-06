package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
  static Logger LOG = LoggerFactory.getLogger(UserRepository.class);

  User findByEmail(String email);

  User findByEmail(String email, @Depth int i);

  @Query("MATCH (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.VERIFIED_BY_LABEL +
    "]-(v:`VerificationToken`) where ID(u)={userId} AND ID(v)={tokenId} set u.accountVerified={isVerified} set v.verified={isVerified} ")
  void verifyUser(@Param("userId") Long userId, @Param("tokenId") Long tokenId, @Param("isVerified") boolean isVerified);

  @Query("MATCH (u:`" + DataConstants.USER + "`) where ID(u)={userId} MATCH (p:`Personality`) where ID(p)={personalityId} create unique (u)-[r:" + DataConstants.HAS_PERSONALITY_LABEL +
    "]->(p) return p;")
  Personality addPersonality(@Param("userId") Long userId, @Param("personalityId") Long personalityId);

  @Query("Match (a:`" + DataConstants.USER + "`),(b:Election) where id(a)={userId} and id(b)={electionId} CREATE UNIQUE (a)-[r:" + DataConstants.VREMINDER_LABEL +
    "]->(b) SET r.timestamp=coalesce(r.timestamp,timestamp()),r.location={location},r.date={date} return (a)-[r]->(b)")
  VoteReminder addVoteReminder(@Param("userId") Long userId, @Param("electionId") Long electionId,
                               @Param("date") Long date, @Param("location") String location);

  @Query("Match (a:`" + DataConstants.USER + "`),(b) where id(a)={userId} and id(b)={electionId} CREATE UNIQUE (a)-[r:" + DataConstants.VRECORD_LABEL +
    "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='VoteRecord',r.location={location} return r")
  VoteRecord addVoteRecord(@Param("userId") Long userId, @Param("electionId") Long electionId, @Param("location") String location);

  @Query("Match (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.VRECORD_LABEL + "]-(e:`Election`) where id(r)={id} return r")
  VoteRecord readVoteRecord(@Param("id") Long id);

  @Query("Match (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.VREMINDER_LABEL + "]-(e:`Election`) where id(r)={id} return r")
  VoteReminder readVoteReminder(@Param("id") Long id);

  @Query("Match (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.VRECORD_LABEL + "]-(e:`Election`) where id(r)={id} delete r return r")
  VoteRecord deleteVoteRecord(@Param("id") Long id);

  @Query("Match (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.VREMINDER_LABEL + "]-(e:`Election`) where id(r)={id} delete r return r")
  VoteReminder deleteVoteReminder(@Param("id") Long id);

  @Query(value = "Match (" + DataConstants.USER + ")-[r:" + DataConstants.LIKES_LABEL + "]-(a:`NewsArticle`) WHERE id(a)={articleId} RETURN u",
  countQuery = "Match (" + DataConstants.USER + ")-[r:" + DataConstants.LIKES_LABEL + "]-(a:`NewsArticle`) WHERE id(a)={articleId} RETURN count(u)")
  Page<User> findByArticleId(@Param("articleId") Long id, Pageable p);

  @Query(value = "Match (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.LIKES_LABEL + "]-(a) WHERE id(a)={objId} RETURN u",
  countQuery = "Match (u:`" + DataConstants.USER + "`)-[r:" + DataConstants.LIKES_LABEL + "]-(a) WHERE id(a)={objId} RETURN count(u)")
  Page<User> findByLikeableObjId(@Param("objId") Long id, Pageable p);


  @Query("Match (a:`" + DataConstants.USER + "`)-[r:`" + DataConstants.LIKES_LABEL + "`]-(b) where a.email={email} and id(b)={likedId} return count(r)")
  Long isLikedBy(@Param("email") String email, @Param("likedId") Long likedId);

  @Query("Match (a:`" + DataConstants.USER + "`),(b) where a.email={email} and id(b)={likedId} CREATE UNIQUE (a)-[r:" + DataConstants.LIKES_LABEL + "]->(b) SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Like' return count(r)")
  Long like(@Param("email") String email, @Param("likedId") Long likedId);

  @Query("Match (a:`" + DataConstants.USER + "`)-[r:LIKES]-(b) where a.email={email} and id(b)={likedId} delete r return count(r)")
  Long unlike(@Param("email") String email, @Param("likedId") Long likedId);

  User findByContactNumber(String contactNumber);

  @Query("Match (a),(b) where id(a)={contactorId} and id(b)={contacteeId} CREATE UNIQUE (a)-[r:" + DataConstants.CONTACT_LABEL +
    "]->(b) SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Contact' return r")
  Contact addContact(@Param("contactorId") Long contactorId, @Param("contacteeId") Long contacteeId);

  @Query(value = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.CONTACT_LABEL + "]-(b:`" + DataConstants.USER +
    "`) where id(a)={userId} return b",
  countQuery = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.CONTACT_LABEL + "]-(b:`" + DataConstants.USER +
    "`) where id(a)={userId} return count(b)")
  Page<User> findContacts(@Param("userId") Long userId, Pageable pageable);

//  @Query("MATCH (u:User) where u.email={userEmail} with u match l=(u)-[*0..2]-() return l")
//  @Query("MATCH (n:`User`) WHERE n.`email` = {userEmail} with n match l=(n)-[*0..1]-() return n, l")
//  User findContacts(@Param("userEmail") String userEmail);

  @Query("MATCH (u:User)-[r:HAS_CONTACT]-(t:User) where u.email={userEmail} and id(t)<>id(u) Return distinct t,r order by r.timestamp DESC")
  List<User> findContactsZeroDepth(@Param("userEmail") String userEmail);

  @Query(value = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.SUPPORT_LABEL + "]-(b:`" + DataConstants.TICKET + "`) where id(a)={userId} with b return (b)-[*0..1]-()",
  countQuery = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.SUPPORT_LABEL + "]-(b:`" + DataConstants.TICKET + "`) where id(a)={userId} return count(b)")
  Page<Ticket> findSupports(@Param("userId") Long userId, Pageable pageable);

  @Query(value = "Match l=(a:`" + DataConstants.USER + "`)-[r:" + DataConstants.VREMINDER_LABEL + "]-(b:`" + DataConstants.ELECTION + "`) where id(a)={userId} return l",
  countQuery = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.VREMINDER_LABEL + "]-(b:`" + DataConstants.ELECTION + "`) where id(a)={userId} return count(r)")
  Page<VoteReminder> findVoteReminders(@Param("userId") Long userId, Pageable pageable);

  @Query(value = "Match l=(a:`" + DataConstants.USER + "`)-[r:" + DataConstants.VRECORD_LABEL + "]-(b:`" + DataConstants.ELECTION + "`) where id(a)={userId} return l",
  countQuery = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.VRECORD_LABEL + "]-(b:`" + DataConstants.ELECTION + "`) where id(a)={userId} return count(r)")
  Page<VoteRecord> findVoteRecords(@Param("userId") Long userId, Pageable pageable);

  @Query("Match (a:`User`) WHERE a.email={userEmail} RETURN id(a)")
  Long getUserId(@Param("userEmail") String userEmail);

  @Query("Match (u:User) WHERE lower(u.givenName) STARTS WITH {pattern1} AND lower(u.familyName) STARTS WITH {pattern2} return u limit 20")
  List<User> searchUserByName(@Param("pattern1") String pattern1, @Param("pattern2") String pattern2);

  @Query("Match (u:User) WHERE lower(u.givenName) STARTS WITH {pattern1} OR lower(u.familyName) STARTS WITH {pattern2} return u limit 20")
  List<User> searchUserByName2(@Param("pattern1") String pattern1, @Param("pattern2") String pattern2);

  @Query("MATCH (u:`" + DataConstants.USER + "`) WHERE u.email={userEmail} SET u.experience=u.experience+{expValue}")
  void addExpPoint(@Param("userEmail") String userEmail, @Param("expValue") Long expValue);

  @Query("Match (u1:User), (u2:User) where u1.email={userEmail1} and u2.email={userEmail2} return Exists ( (u1)-[:HAS_CONTACT]-(u2) )")
  boolean isFriend(@Param("userEmail1") String userEmail1, @Param("userEmail2") String userEmail2);

  @Query("Match (u:User)-[:HAS_CONTACT]-(x:User) return u, count(x) as numOfContacts order by numOfContacts DESC limit 20")
  List<User> getTopRankingUsersByNumOfContacts();
}