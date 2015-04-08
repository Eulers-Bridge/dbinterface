/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class CandidateToOwnerConverterTest
{
	CandidateToOwnerConverter converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		converter=new CandidateToOwnerConverter();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.converters.CandidateToOwnerConverter#convert(com.eulersbridge.iEngage.database.domain.Candidate)}.
	 */
	@Test
	public final void testConvert()
	{
		Candidate source=DatabaseDataFixture.populateCandidate1();		
		Owner owner=converter.convert(source);
		assertEquals(owner.getNodeId(),source.getNodeId());
	}

}
