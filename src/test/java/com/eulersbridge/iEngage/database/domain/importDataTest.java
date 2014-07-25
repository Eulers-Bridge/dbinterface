package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class importDataTest {
    private static Logger LOG = LoggerFactory.getLogger(importDataTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void shouldProduceCypherImportStringsForInstitutionTestData() throws Exception 
	{
		String fileName="/Institution.csv";
		if (LOG.isDebugEnabled()) LOG.debug("Filename = "+fileName);
		assertNotNull("Test file missing",getClass().getResource(fileName));

	  InputStream is=getClass().getResourceAsStream(fileName);
	  InputStreamReader isr=new InputStreamReader(is);
//	  CsvInstitution.readWithCsvBeanReader(isr);
//		  CsvInstitution.readWithCsvBeanReader2(filePath.toString());
//		  CsvInstitution.readWithCsvListReader(filePath.toString());
//		  CsvInstitution.readWithCsvMapReader(filePath.toString());
		  
	}

}
