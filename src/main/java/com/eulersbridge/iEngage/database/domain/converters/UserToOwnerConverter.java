/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import org.springframework.core.convert.converter.Converter;

import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.User;

/**
 * @author Greg Newitt
 *
 */

public class UserToOwnerConverter implements Converter<User, Owner>
{

	@Override
	public Owner convert(User source)
	{
		Owner owner=new Owner();
		owner.setNodeId(source.getNodeId());
			
		return owner;
	}

}
