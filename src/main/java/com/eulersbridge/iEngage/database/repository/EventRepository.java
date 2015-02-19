package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Event;

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
}
