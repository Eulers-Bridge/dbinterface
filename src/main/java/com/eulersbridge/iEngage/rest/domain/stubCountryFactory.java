package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class stubCountryFactory extends CountriesFactory {
  Country cs[] = new Country[1];

  public stubCountryFactory() {
    InstitutionDomain uniOfMelb = new InstitutionDomain(new Long(26), "University of Melbourne");
    InstitutionDomain deakin = new InstitutionDomain(new Long(25), "Deakin University");
    InstitutionDomain monash = new InstitutionDomain(new Long(27), "Monash University");

    ArrayList<InstitutionDomain> ozUniversities = new ArrayList<InstitutionDomain>();
    ozUniversities.add(uniOfMelb);
    ozUniversities.add(deakin);
    ozUniversities.add(monash);

    InstitutionDomain cambridge = new InstitutionDomain(new Long(1), "Cambridge University");
    InstitutionDomain oxford = new InstitutionDomain(new Long(2), "Oxford University");
    ArrayList<InstitutionDomain> ukUniversities = new ArrayList<InstitutionDomain>();
    ukUniversities.add(cambridge);
    ukUniversities.add(oxford);

    InstitutionDomain[] ozUnis = new InstitutionDomain[1];
    ozUnis = ozUniversities.toArray(ozUnis);
    InstitutionDomain[] ukUnis = new InstitutionDomain[1];
    ukUnis = ukUniversities.toArray(ukUnis);
    Country australia = new Country(new Long(61), "Australia", Arrays.asList(ozUnis));
    Country uk = new Country(new Long(44), "United Kingdom", Arrays.asList(ukUnis));
    ArrayList<Country> cList = new ArrayList<Country>();
    cList.add(australia);
    cList.add(uk);

    cs = cList.toArray(cs);

  }

  public Country[] getCountries() {
    return cs;
  }


}
