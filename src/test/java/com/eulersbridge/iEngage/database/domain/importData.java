package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.rest.controller.LoginController;

public class importData {
    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

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
	  CsvInstitution.readWithCsvBeanReader(isr);
//		  CsvInstitution.readWithCsvBeanReader2(filePath.toString());
//		  CsvInstitution.readWithCsvListReader(filePath.toString());
//		  CsvInstitution.readWithCsvMapReader(filePath.toString());
		  
	}

}
