/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Owner;
import org.springframework.core.convert.converter.Converter;

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
