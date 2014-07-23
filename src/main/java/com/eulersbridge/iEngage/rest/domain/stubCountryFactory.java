package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;

public class stubCountryFactory extends CountriesFactory
{
	Country cs[]=new Country[1];
	
	public stubCountryFactory()
	{
		Institution uniOfMelb=new Institution(new Long(26),"University of Melbourne");
		Institution deakin=new Institution(new Long(25),"Deakin University");
		Institution monash=new Institution(new Long(27),"Monash University");
		
		ArrayList<Institution> ozUniversities=new ArrayList<Institution>();
		ozUniversities.add(uniOfMelb);
		ozUniversities.add(deakin);
		ozUniversities.add(monash);
		
		Institution cambridge=new Institution(new Long(1),"Cambridge University");
		Institution oxford=new Institution(new Long(2),"Oxford University");
		ArrayList<Institution> ukUniversities=new ArrayList<Institution>();
		ukUniversities.add(cambridge);
		ukUniversities.add(oxford);
		
		Institution[] ozUnis=new Institution[1];
		ozUnis=ozUniversities.toArray(ozUnis);
		Institution[] ukUnis=new Institution[1];
		ukUnis=ukUniversities.toArray(ukUnis);
		Country australia=new Country(new Long(61),"Australia",ozUnis);
		Country uk=new Country(new Long(44),"United Kingdom",ukUnis);
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
