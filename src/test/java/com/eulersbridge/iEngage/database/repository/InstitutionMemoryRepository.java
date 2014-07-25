/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import java.util.Map;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

/**
 * @author Greg Newitt
 *
 */
public class InstitutionMemoryRepository implements InstitutionRepository {

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll()
	 */
	@Override
	public Result<Institution> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll(org.springframework.data.domain.Sort)
	 */
	@Override
	public Result<Institution> findAll(Sort arg0) {
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
	public Result<Institution> query(String arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Institution> findAll(Pageable arg0) {
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
	public void delete(Institution arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(Iterable<? extends Institution> arg0) {
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
	public Iterable<Institution> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public Institution findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends Institution> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
	 */
	@Override
	public <S extends Institution> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<Institution> findAllByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<Institution> findAllByQuery(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByRange(java.lang.String, java.lang.Number, java.lang.Number)
	 */
	@Override
	public Result<Institution> findAllByRange(String arg0, Number arg1,
			Number arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Institution findByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findAllBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<Institution> findAllBySchemaPropertyValue(String arg0,
			Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Institution findBySchemaPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.TraversalRepository#findAllByTraversal(java.lang.Object, org.neo4j.graphdb.traversal.TraversalDescription)
	 */
	@Override
	public <N> Iterable<Institution> findAllByTraversal(N arg0,
			TraversalDescription arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
