/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;

import com.eulersbridge.iEngage.database.domain.VerificationToken;

/**
 * @author Greg Newitt
 *
 */
public class VerificationTokenMemoryRepository implements
		VerificationTokenRepository 
{
	  private Map<Long, VerificationToken> tokens;
		Long maxKey=(long) 0;

	  public VerificationTokenMemoryRepository(final Map<Long, VerificationToken> tokens) 
	  {
	    this.tokens = Collections.unmodifiableMap(tokens);
	  }


	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll()
	 */
	@Override
	public Result<VerificationToken> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll(org.springframework.data.domain.Sort)
	 */
	@Override
	public Result<VerificationToken> findAll(Sort arg0) {
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
	public Result<VerificationToken> query(String arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<VerificationToken> findAll(Pageable arg0) {
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
	public void delete(Long key) 
	{
	    if (tokens.containsKey(key)) {
	        Map<Long, VerificationToken> modifiableTokens = new HashMap<Long, VerificationToken>(tokens);
	        modifiableTokens.remove(key);
	        this.tokens = Collections.unmodifiableMap(modifiableTokens);
	      }
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(VerificationToken arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(Iterable<? extends VerificationToken> arg0) {
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
	public Iterable<VerificationToken> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public VerificationToken findOne(Long tokenId) 
	{
		return tokens.get(tokenId);
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends VerificationToken> S save(S token) {
	    Map<Long, VerificationToken> modifiableTokens = new HashMap<Long, VerificationToken>(tokens);
	    if (null==token.getNodeId())
	    {
	    	maxKey++;
	    	token.setNodeId(maxKey);
	    }

	    modifiableTokens.put(token.getNodeId(), token);
	    this.tokens = Collections.unmodifiableMap(modifiableTokens);

	    return token;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
	 */
	@Override
	public <S extends VerificationToken> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<VerificationToken> findAllByPropertyValue(String arg0,
			Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<VerificationToken> findAllByQuery(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByRange(java.lang.String, java.lang.Number, java.lang.Number)
	 */
	@Override
	public Result<VerificationToken> findAllByRange(String arg0, Number arg1,
			Number arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public VerificationToken findByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findAllBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<VerificationToken> findAllBySchemaPropertyValue(String arg0,
			Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public VerificationToken findBySchemaPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.TraversalRepository#findAllByTraversal(java.lang.Object, org.neo4j.graphdb.traversal.TraversalDescription)
	 */
	@Override
	public <N> Iterable<VerificationToken> findAllByTraversal(N arg0,
			TraversalDescription arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public VerificationToken findToken()
	{
		VerificationToken token=null;
		if (!(tokens.values().isEmpty()))
		{
			Iterator<VerificationToken> iter=tokens.values().iterator();
			token=iter.next();
		}
		return token;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.database.repository.VerificationTokenRepository#findByToken(java.lang.String)
	 */
	@Override
	public VerificationToken findByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
