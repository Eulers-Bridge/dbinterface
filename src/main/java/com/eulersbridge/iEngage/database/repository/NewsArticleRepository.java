package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.StudentYear;
import com.eulersbridge.iEngage.database.domain.User;

public interface NewsArticleRepository extends GraphRepository<NewsArticle> 
{
	static Logger LOG = LoggerFactory.getLogger(NewsArticleRepository.class);
	   
	Page<NewsArticle> findByStudentYear(Pageable p,StudentYear sy);
	Iterable<NewsArticle> findByCreator(User creator);
	@Query("Match (n:`StudentYear`)-[r]-(a:NewsArticle) where id(n)={syId} return a")
	Page<NewsArticle> findByStudentYearId(@Param("syId")Long syId,Pageable p);

}
