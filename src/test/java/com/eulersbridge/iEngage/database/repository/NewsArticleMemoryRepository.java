/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;

import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;

/**
 * @author Greg
 *
 */
public class NewsArticleMemoryRepository implements NewsArticleRepository 
{

	private Map<Long, NewsArticle> newsArticles;
	  
	public NewsArticleMemoryRepository(final Map<Long, NewsArticle> newsArticles) 
	{
		this.newsArticles = Collections.unmodifiableMap(newsArticles);
	}


	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll()
	 */
	@Override
	public Result<NewsArticle> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll(org.springframework.data.domain.Sort)
	 */
	@Override
	public Result<NewsArticle> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#getStoredJavaType(java.lang.Object)
	 */
	@Override
	public Class getStoredJavaType(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#query(java.lang.String, java.util.Map)
	 */
	@Override
	public Result<NewsArticle> query(String arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<NewsArticle> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#count()
	 */
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(NewsArticle arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(Iterable<? extends NewsArticle> arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#deleteAll()
	 */
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)
	 */
	@Override
	public Iterable<NewsArticle> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public NewsArticle findOne(Long arg0) 
	{
		NewsArticle one=newsArticles.get(arg0);
		return one;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends NewsArticle> S save(S newsArticle) 
	{
	    Map<Long, NewsArticle> modifiableNews = new HashMap<Long, NewsArticle>(newsArticles);
	    modifiableNews.put(newsArticle.getNodeId(), newsArticle);
	    this.newsArticles = Collections.unmodifiableMap(modifiableNews);

	    return newsArticle;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
	 */
	@Override
	public <S extends NewsArticle> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<NewsArticle> findAllByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<NewsArticle> findAllByQuery(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByRange(java.lang.String, java.lang.Number, java.lang.Number)
	 */
	@Override
	public Result<NewsArticle> findAllByRange(String arg0, Number arg1,
			Number arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public NewsArticle findByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findAllBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<NewsArticle> findAllBySchemaPropertyValue(String arg0,
			Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public NewsArticle findBySchemaPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.TraversalRepository#findAllByTraversal(java.lang.Object, org.neo4j.graphdb.traversal.TraversalDescription)
	 */
	@Override
	public <N> Iterable<NewsArticle> findAllByTraversal(N arg0,
			TraversalDescription arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
