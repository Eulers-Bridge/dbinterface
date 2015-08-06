/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import org.springframework.core.convert.converter.Converter;

import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.Owner;

/**
 * @author Greg Newitt
 *
 */

public class NewsArticleToOwnerConverter implements Converter<NewsArticle, Owner>
{

    @Override
	public Owner convert(NewsArticle source)
	{
		Owner owner=new Owner();
		owner.setNodeId(source.getNodeId());
			
		return owner;
	}

}
