package com.eulersbridge.iEngage.database.repository;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.StudentYear;

public interface StudentYearRepository extends GraphRepository<StudentYear> 
{
	   static Logger LOG = LoggerFactory.getLogger(StudentYearRepository.class);

		@Query("MATCH (i:`Institution`)-[r:HAS_STUDENT_YEAR]-(s:`StudentYear`) where ID(i)={instId} return s")
		Set <StudentYear> findStudentYears(@Param("instId") Long institutionId);
}
