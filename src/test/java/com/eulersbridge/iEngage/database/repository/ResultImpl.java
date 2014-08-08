/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import java.util.Iterator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.conversion.Handler;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.conversion.ResultConverter;
import org.springframework.data.neo4j.mapping.MappingPolicy;

/**
 * @author Greg
 *
 */
public class ResultImpl<T> implements Result<T> 
{
	Iterator <T> iterator;

	@Override
	public Iterator<T> iterator() 
	{
		return iterator;
	}
	
	public void setIterator(Iterator<T> iter)
	{
		this.iterator=iter;
	}

	@Override
	public <C extends Iterable<T>> C as(Class<C> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle(Handler<T> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T single() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T singleOrNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slice<T> slice(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slice<T> slice(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R> Result<R> to(Class<R> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R> Result<R> to(Class<R> arg0, ResultConverter<T, R> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<T> with(MappingPolicy arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
