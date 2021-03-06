package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.GeneralInfoData;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends Neo4jRepository<Institution, Long> {
  static Logger LOG = LoggerFactory.getLogger(InstitutionRepository.class);

  //	@Query("MATCH (n:`StudentYear`)-[:HAS_STUDENT_YEAR]-(q:`Institution`) where id(q)={instId}"+
//" WITH DISTINCT n MATCH q-[:HAS_STUDENT_YEAR]-m WITH q, max(m.year) as max "+
//" MATCH q-[:HAS_STUDENT_YEAR]-m WHERE m.year = max "  // find the max m for each q
//+" WITH q, m MATCH m-[:HAS_STUDENT_YEAR]-x RETURN m")
  @Query("Match (i:`" + DataConstants.INSTITUTION + "`)-[" + DataConstants.HAS_NEWS_FEED_LABEL + "]-(n:`" +
    DataConstants.NEWS_FEED + "`) where id(i)={instId} RETURN n Limit 1")
  NewsFeed findNewsFeedByInstitutionId(@Param("instId") Long institutionId);

  @Query("MATCH (i:`Institution`)-[]-(c:`Country`) where id(c)={countryId} return i")
  List<Institution> findByCountryId(@Param("countryId") Long countryId);

  //TODO Allow configuration of default institution
  @Query("MATCH (i:`Institution`) return i limit 1")
  Institution findDefaultInstitution();

  @Query("match (c:Country)-[r:HAS_INSTITUTIONS]-(i:Institution) " +
    "return id(c) as countryId,c.countryName as countryName"
    + ",COLLECT(i.name) as institutionNames,COLLECT(id(i)) as institutionIds"
  )
  List<GeneralInfoData> getGeneralInfo();

  Institution findByName(String name, @Depth int i);

  @Query("MATCH (i:" + DataConstants.INSTITUTION + ")<-[r:" + DataConstants.USERS_LABEL + "]-(u:" + DataConstants.USER + ") WHERE u.email={email} RETURN i LIMIT 1")
  Institution findInstitutionByUserEMail(@Param("email") String email);

}
