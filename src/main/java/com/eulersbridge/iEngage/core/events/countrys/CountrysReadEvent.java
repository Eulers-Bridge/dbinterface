/**
 * 
 */
package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class CountrysReadEvent extends ReadEvent 
{
	Iterable<CountryDetails> countrys;
	
	public CountrysReadEvent(Iterable<CountryDetails> countrys)
	{
		super(1l);
		this.countrys=countrys;
	}

	/**
	 * @return the countrys
	 */
	public Iterable<CountryDetails> getCountrys() 
	{
		return countrys;
	}

	/**
	 * @param countrys the countrys to set
	 */
	public void setCountrys(Iterable<CountryDetails> countrys) 
	{
		this.countrys = countrys;
	}	
}
