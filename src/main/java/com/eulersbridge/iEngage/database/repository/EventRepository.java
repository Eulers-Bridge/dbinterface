package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Like;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface EventRepository extends GraphRepository<Event>
{
    static Logger LOG = LoggerFactory.getLogger(EventRepository.class);

	@Query("Match (n:`"+DatabaseDomainConstants.INSTITUTION+"`)-[r:"+DatabaseDomainConstants.HAS_NEWS_FEED_LABEL+
			"]-(f:`"+DatabaseDomainConstants.NEWS_FEED+"`)-[s:"+DatabaseDomainConstants.HAS_EVENT_LABEL+
			"]-(e:`Event`) where id(n)={instId} return e")
	Page<Event> findByInstitutionId(@Param("instId")Long instId,Pageable p);
	
	@Query("Match (a:`User`),(b) where a.email={email} and id(b)={likedId} CREATE UNIQUE a-[r:LIKES]-b SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Like' return r")
	Like likeEvent(@Param("email")String email,@Param("likedId")Long likedId);
	
	@Query("Match (a:`User`)-[r:LIKES]-(b) where a.email={email} and id(b)={likedId} delete r")
	void unlikeEvent(@Param("email")String email,@Param("likedId")Long likedId);
}
