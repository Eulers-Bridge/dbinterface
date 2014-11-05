package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Personality;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VoteRecord;
import com.eulersbridge.iEngage.database.domain.VoteReminder;

public interface UserRepository extends GraphRepository<User> 
{
    static Logger LOG = LoggerFactory.getLogger(UserRepository.class);
	
 	User findByEmail(String email);
 	@Query("MATCH (u:`User`)-[r:"+DatabaseDomainConstants.VERIFIED_BY_LABEL+
 			"]-(v:`VerificationToken`) where ID(u)={userId} AND ID(v)={tokenId} set u.accountVerified={isVerified} set v.verified={isVerified} ")
	void verifyUser(@Param("userId") Long userId, @Param("tokenId") Long tokenId, @Param("isVerified") boolean isVerified);

	@Query("MATCH (u:`User`) where ID(u)={userId} MATCH (p:`Personality`) where ID(p)={personalityId} create (u)-[r:hasPersonality]->(p) return p;")
	Personality addPersonality(@Param("userId") Long userId,@Param("personalityId") Long personalityId);
	
	@Query("Match (a:`User`),(b) where id(a)={userId} and id(b)={electionId} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.VREMINDER_LABEL+
			"]-b SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='VoteReminder',r.location={location},r.date={date} return r")
	VoteReminder addVoteReminder(@Param("userId")Long userId,@Param("electionId")Long electionId,
								 @Param("date")Long date, @Param("location")String location);

	@Query("Match (a:`User`),(b) where id(a)={userId} and id(b)={electionId} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.VRECORD_LABEL+
			"]-b SET r.date=coalesce(r.date,timestamp()),r.__type__='VoteRecord',r.location={location} return r")
	VoteRecord addVoteRecord(@Param("userId")Long userId,@Param("electionId")Long electionId, @Param("location")String location);
	
	@Query("Match (u:`User`)-[r:"+DatabaseDomainConstants.VRECORD_LABEL+"]-(e:`Election`) where id(r)={id} return r")
	VoteRecord readVoteRecord(@Param("id")Long id);

	@Query("Match (u:`User`)-[r:"+DatabaseDomainConstants.VREMINDER_LABEL+"]-(e:`Election`) where id(r)={id} return r")
	VoteReminder readVoteReminder(@Param("id")Long id);

	@Query("Match (u:`User`)-[r:"+DatabaseDomainConstants.VRECORD_LABEL+"]-(e:`Election`) where id(r)={id} delete r return r")
	VoteRecord deleteVoteRecord(@Param("id")Long id);

	@Query("Match (u:`User`)-[r:"+DatabaseDomainConstants.VREMINDER_LABEL+"]-(e:`Election`) where id(r)={id} delete r return r")
	VoteReminder deleteVoteReminder(@Param("id")Long id);

    @Query("Match (u:'User')-[r:" + DatabaseDomainConstants.LIKES_LABEL + "]-(a:'NewsArticle') WHERE id(a)={articleId} RETURN u")
    Page<User> findByArticleId (@Param("articleId")Long id, Pageable p);

    @Query("Match (u:'User')-[r:" + DatabaseDomainConstants.LIKES_LABEL + "]-(a) WHERE id(a)={objId} RETURN u")
    Page<User> findByLikeableObjId (@Param("objId")Long id, Pageable p);
}