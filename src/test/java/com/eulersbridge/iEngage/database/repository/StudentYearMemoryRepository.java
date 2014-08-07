/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;

import com.eulersbridge.iEngage.database.domain.StudentYear;

/**
 * @author Greg Newitt
 *
 */
public class StudentYearMemoryRepository implements StudentYearRepository 
{

	private Map<Long, StudentYear> years;
	Long maxKey=(long) 0;
	  
	public StudentYearMemoryRepository(final Map<Long, StudentYear> years) 
	{
		this.years = Collections.unmodifiableMap(years);
		maxKey=(long) years.size();
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll()
	 */
	@Override
	public Result<StudentYear> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll(org.springframework.data.domain.Sort)
	 */
	@Override
	public Result<StudentYear> findAll(Sort arg0) {
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
	public Result<StudentYear> query(String arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<StudentYear> findAll(Pageable arg0) {
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
	public void delete(Long id) {
	    Map<Long, StudentYear> modifiableYears = new HashMap<Long, StudentYear>(years);
		StudentYear na=modifiableYears.remove(id);
	    this.years = Collections.unmodifiableMap(modifiableYears);
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(StudentYear arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
	 */
	@Override
	public void delete(Iterable<? extends StudentYear> arg0) {
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
	public Iterable<StudentYear> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public StudentYear findOne(Long id) 
	{
		StudentYear one=years.get(id);
		return one;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends StudentYear> S save(S year) {
	    Map<Long, StudentYear> modifiableYears = new HashMap<Long, StudentYear>(years);
	    if (null==year.getNodeId())
	    {
	    	maxKey++;
	    	year.setNodeId(maxKey);
	    }
	    modifiableYears.put(year.getNodeId(), year);
	    this.years = Collections.unmodifiableMap(modifiableYears);

	    return year;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
	 */
	@Override
	public <S extends StudentYear> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<StudentYear> findAllByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<StudentYear> findAllByQuery(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findAllByRange(java.lang.String, java.lang.Number, java.lang.Number)
	 */
	@Override
	public Result<StudentYear> findAllByRange(String arg0, Number arg1,
			Number arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.IndexRepository#findByPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public StudentYear findByPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findAllBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Result<StudentYear> findAllBySchemaPropertyValue(String arg0,
			Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.SchemaIndexRepository#findBySchemaPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public StudentYear findBySchemaPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.TraversalRepository#findAllByTraversal(java.lang.Object, org.neo4j.graphdb.traversal.TraversalDescription)
	 */
	@Override
	public <N> Iterable<StudentYear> findAllByTraversal(N arg0,
			TraversalDescription arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<StudentYear> findStudentYears(Long institutionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
