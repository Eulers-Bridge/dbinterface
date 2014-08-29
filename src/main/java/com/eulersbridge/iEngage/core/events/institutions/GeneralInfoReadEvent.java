package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.database.repository.InstitutionRepository.GeneralInfo;

import java.util.Iterator;

/**
 * @author Yikai Gong
 */

public class GeneralInfoReadEvent {
    Iterator<GeneralInfo> generalInfos;

    public GeneralInfoReadEvent(Iterator<GeneralInfo> generalInfos){
        this.generalInfos = generalInfos;
    }

    public Iterator<GeneralInfo> getGeneralInfos(){
        return this.generalInfos;
    }
}
