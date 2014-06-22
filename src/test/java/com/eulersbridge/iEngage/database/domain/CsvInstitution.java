package com.eulersbridge.iEngage.database.domain;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.supercsv.cellprocessor.Optional;
//import org.supercsv.cellprocessor.ParseBool;
//import org.supercsv.cellprocessor.ParseDate;
//import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
//import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class CsvInstitution extends Institution {
    private static Logger LOG = LoggerFactory.getLogger(CsvInstitution.class);

	public CsvInstitution() {
	}
	
	
	/**
	 * Sets up the processors used for the examples. There are 10 CSV columns, so 10 processors are defined. Empty
	 * columns are read as null (hence the NotNull() for mandatory columns).
	 * 
	 * @return the cell processors
	 */

	public static CellProcessor[] getProcessors() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("getProcessors() entered");
	        
	        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
	        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
	        
	        final CellProcessor[] processors = new CellProcessor[] 
	        {
	        		new NotNull(),
	        		new NotNull(),
	        		new NotNull(),
	        		new NotNull()
/*	                new UniqueHashCode(), // customerNo (must be unique)
	                new NotNull(), // firstName
	                new NotNull(), // lastName
	                new ParseDate("dd/MM/yyyy"), // birthDate
	                new NotNull(), // mailingAddress
	                new Optional(new ParseBool()), // married
	                new Optional(new ParseInt()), // numberOfKids
	                new NotNull(), // favouriteQuote
	                new StrRegEx(emailRegex) // email
	                */
	        };
	        
	        return processors;
	}
	
	public static void readWithCsvBeanReader(Reader reader) throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("readWithCsvBeanReader() entered");
    
        ICsvBeanReader beanReader = null;
        try 
        {
            beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
            
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();
            
            Institution inst;
            while( (inst = beanReader.read(Institution.class, header, processors)) != null ) 
            {
            	if (LOG.isDebugEnabled())
            		LOG.debug(String.format("lineNo=%s, rowNo=%s, institution=%s", beanReader.getLineNumber(),
                            beanReader.getRowNumber(), inst));
                System.out.println(String.format("CREATE ({name:'%s', Campus:'%s', State:'%s', Country:'%s'})", 
                            inst.getName(), inst.getCampus(), inst.getState(), inst.getCountry()));
            }
            
        }
        finally 
        {
            if( beanReader != null ) 
            {
                    beanReader.close();
            }
        }
	}
	public static void readWithCsvBeanReader2(String fileName) throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("readWithCsvBeanReader2() entered");
        
        ICsvBeanReader beanReader = null;
        try 
        {
            beanReader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();
            
            Institution inst;
            while( (inst = beanReader.read(Institution.class, header, processors)) != null ) 
            {
	        	if (LOG.isDebugEnabled())
	        		LOG.debug(String.format("lineNo=%s, rowNo=%s, institution=%s", beanReader.getLineNumber(),
	                        beanReader.getRowNumber(), inst));
	            System.out.println(String.format("lineNo=%s, rowNo=%s, institution=%s", beanReader.getLineNumber(),
	                        beanReader.getRowNumber(), inst));
            }
            
        }
        finally 
        {
            if( beanReader != null ) 
            {
                beanReader.close();
            }
        }
	}
	/**
	 * An example of reading using CsvListReader.
	 */
	public static void readWithCsvListReader(String fileName) throws Exception 
	{
	        
		if (LOG.isDebugEnabled()) LOG.debug("readWithCsvListReader() entered");
	
		ICsvListReader listReader = null;
	    try 
	    {
            listReader = new CsvListReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            
            listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
            final CellProcessor[] processors = getProcessors();
            
            List<Object> instList;
            while( (instList = listReader.read(processors)) != null ) 
            {
	        	if (LOG.isDebugEnabled())
	        		LOG.debug(String.format("lineNo=%s, rowNo=%s, institution=%s", listReader.getLineNumber(),
	                        listReader.getRowNumber(), instList));
                System.out.println(String.format("lineNo=%s, rowNo=%s, customerList=%s", listReader.getLineNumber(),
                        listReader.getRowNumber(), instList));
            }
            
	    }
        finally 
        {
            if( listReader != null ) 
            {
                listReader.close();
            }
        }
	}
	/**
	 * An example of reading using CsvMapReader.
	 */
	public static void readWithCsvMapReader(String fileName) throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("readWithCsvListReader() entered");
        
		ICsvMapReader mapReader = null;
        try 
        {
            mapReader = new CsvMapReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            
            // the header columns are used as the keys to the Map
            final String[] header = mapReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();
            
            Map<String, Object> instMap;
            while( (instMap = mapReader.read(header, processors)) != null )
            {
	        	if (LOG.isDebugEnabled())
	        		LOG.debug(String.format("lineNo=%s, rowNo=%s, institution=%s", mapReader.getLineNumber(),
	                        mapReader.getRowNumber(), instMap));
                System.out.println(String.format("lineNo=%s, rowNo=%s, customerMap=%s", mapReader.getLineNumber(),
                    mapReader.getRowNumber(), instMap));
            }
        }
        finally 
        {
            if( mapReader != null ) 
            {
                mapReader.close();
            }
        }
	}
}
