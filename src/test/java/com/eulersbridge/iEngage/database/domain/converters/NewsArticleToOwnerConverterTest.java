/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class NewsArticleToOwnerConverterTest
{
	NewsArticleToOwnerConverter converter;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		converter=new NewsArticleToOwnerConverter();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.converters.NewsArticleToOwnerConverter#convert(com.eulersbridge.iEngage.database.domain.NewsArticle)}.
	 */
	@Test
	public final void testConvert()
	{
		NewsArticle source=DatabaseDataFixture.populateNewsArticle1();		
		Owner owner=converter.convert(source);
		assertEquals(owner.getNodeId(),source.getNodeId());
	}

}
