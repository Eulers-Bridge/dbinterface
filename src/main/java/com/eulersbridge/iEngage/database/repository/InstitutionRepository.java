package com.eulersbridge.iEngage.database.repository;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.StudentYear;

public interface InstitutionRepository extends GraphRepository<Institution> 
{
	static Logger LOG = LoggerFactory.getLogger(InstitutionRepository.class);

	@Query("MATCH (n:`StudentYear`)-[:HAS_STUDENT_YEAR]-(q:`Institution`) where id(q)={instId}"+
" WITH DISTINCT n MATCH q-[:HAS_STUDENT_YEAR]-m WITH q, max(m.year) as max "+
" MATCH q-[:HAS_STUDENT_YEAR]-m WHERE m.year = max "  // find the max m for each q
+" WITH q, m MATCH m-[:HAS_STUDENT_YEAR]-x RETURN m")
	StudentYear findLatestStudentYear(@Param("instId") Long institutionId);

}
