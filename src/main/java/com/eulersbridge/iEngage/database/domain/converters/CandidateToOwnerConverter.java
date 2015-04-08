/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import org.springframework.core.convert.converter.Converter;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Owner;

/**
 * @author Greg Newitt
 *
 */

public class CandidateToOwnerConverter implements Converter<Candidate, Owner>
{

	@Override
	public Owner convert(Candidate source)
	{
		Owner owner=new Owner();
		owner.setNodeId(source.getNodeId());
			
		return owner;
	}

}
