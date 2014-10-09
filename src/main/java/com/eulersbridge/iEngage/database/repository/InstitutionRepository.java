package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.GeneralInfo;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsFeed;

public interface InstitutionRepository extends GraphRepository<Institution> 
{
	static Logger LOG = LoggerFactory.getLogger(InstitutionRepository.class);

//	@Query("MATCH (n:`StudentYear`)-[:HAS_STUDENT_YEAR]-(q:`Institution`) where id(q)={instId}"+
//" WITH DISTINCT n MATCH q-[:HAS_STUDENT_YEAR]-m WITH q, max(m.year) as max "+
//" MATCH q-[:HAS_STUDENT_YEAR]-m WHERE m.year = max "  // find the max m for each q
//+" WITH q, m MATCH m-[:HAS_STUDENT_YEAR]-x RETURN m")
	@Query("Match (i:`"+DatabaseDomainConstants.INSTITUTION+"`)-["+DatabaseDomainConstants.HAS_NEWS_FEED_LABEL+"]-(n:`"+
			DatabaseDomainConstants.NEWS_FEED+"`) where id(i)={instId} RETURN n")
	NewsFeed findNewsFeed(@Param("instId") Long institutionId);

	@Query("MATCH (i:`Institution`)-[]-(c:`Country`) where id(c)={countryId} return i")
	Result<Institution> findByCountryId(@Param("countryId") Long countryId);

	@Query("match (c:Country)-[r:HAS_INSTITUTIONS]-(i:Institution) "+
	       "return id(c) as countryId,c.countryName as countryName"
			+",COLLECT(i.name) as institutionNames,COLLECT(id(i)) as institutionIds"
	       )
	Result<GeneralInfo> getGeneralInfo();
	

}
