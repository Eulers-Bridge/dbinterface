/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.StudentYear;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

/**
 * @author Greg Newitt
 *
 */
public class InstitutionMemoryRepository implements InstitutionRepository 
{
	private Map<Long, Institution> institutions;
	Long maxKey=(long) 0;
	  
	public InstitutionMemoryRepository(final Map<Long, Institution> institutions) 
	{
		this.institutions = Collections.unmodifiableMap(institutions);
		maxKey=(long) institutions.size();
	}


	/* (non-Javadoc)
	 * @see org.springframework.data.neo4j.repository.CRUDRepository#findAll()
	 */
	@Override
	public Result<Institution> findAll() 
	{
		Iterator<Institution> iter=this.institutions.values().iterator();
		ResultImpl<Institution> result=new ResultImpl<Institution>();
		result.setIterator(iter);
		return result;
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
	public void delete(Long id) 
	{
	    Map<Long, Institution> modifiableInsts = new HashMap<Long, Institution>(institutions);
		Institution na=modifiableInsts.remove(id);
	    this.institutions = Collections.unmodifiableMap(modifiableInsts);
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
	public Institution findOne(Long id) 
	{
		Institution one=institutions.get(id);
		return one;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends Institution> S save(S institution) 
	{
	    Map<Long, Institution> modifiableInsts = new HashMap<Long, Institution>(institutions);
	    if (null==institution.getNodeId())
	    {
	    	maxKey++;
	    	institution.setNodeId(maxKey);
	    }
	    modifiableInsts.put(institution.getNodeId(), institution);
	    this.institutions = Collections.unmodifiableMap(modifiableInsts);

	    return institution;
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

	@Override
	public StudentYear findLatestStudentYear(Long institutionId) 
	{
		return DatabaseDataFixture.populateStudentYear2014();
	}


	@Override
	public Result<Institution> findByCountryId(Long countryId) 
	{
		Iterator<Institution> iter=this.institutions.values().iterator();
		ArrayList<Institution> insts=new ArrayList<Institution>();
		while(iter.hasNext())
		{
			Institution inst=iter.next();
			if (countryId==inst.getCountry().getNodeId())
				insts.add(inst);
		}
		ResultImpl<Institution> result=new ResultImpl<Institution>();
		result.setIterator(insts.iterator());
		return result;
	}

}
