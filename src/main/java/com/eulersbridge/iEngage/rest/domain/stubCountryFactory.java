package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;

public class stubCountryFactory extends CountriesFactory
{
	Country cs[]=new Country[1];
	
	public stubCountryFactory()
	{
		University uniOfMelb=new University(1,"University of Melbourne");
		University deakin=new University(2,"Deakin University");
		University monash=new University(3,"Monash University");
		
		ArrayList<University> ozUniversities=new ArrayList<University>();
		ozUniversities.add(uniOfMelb);
		ozUniversities.add(deakin);
		ozUniversities.add(monash);
		
		University cambridge=new University(1,"Cambridge University");
		University oxford=new University(2,"Oxford University");
		ArrayList<University> ukUniversities=new ArrayList<University>();
		ukUniversities.add(cambridge);
		ukUniversities.add(oxford);
		
		University[] ozUnis=new University[1];
		ozUnis=ozUniversities.toArray(ozUnis);
		University[] ukUnis=new University[1];
		ukUnis=ukUniversities.toArray(ukUnis);
		Country australia=new Country(61,"Australia",ozUnis);
		Country uk=new Country(44,"United Kingdom",ukUnis);
		ArrayList<Country> cList=new ArrayList<Country>();
		cList.add(australia);
		cList.add(uk);
		
		cs=cList.toArray(cs);
		
	}
	
	public Country[] getCountries()
	{
		return cs;
	}
	

}
