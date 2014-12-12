package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Yikai Gong
 */

public class TicketDetails extends Details{
    private String name;
    private String logo;
    private Set<String> pictures;
    private String information;
    private String candidatesEmails;
    private String candidatesNames;

    private static Logger LOG = LoggerFactory.getLogger(TicketDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", logo = ");
        buff.append(getLogo());
        buff.append(", pictures = ");
        buff.append(getPictures());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", candidatesEmails = ");
        buff.append(getCandidatesEmails());
        buff.append(", givenName = ");
        buff.append(getCandidatesNames());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if (getNodeId()!=null)
        {
            result = prime * result	+ getNodeId().hashCode();
        }
        else
        {
            result = prime * result	+ ((name == null) ? 0 : name.hashCode());
            result = prime * result	+ ((logo == null) ? 0 : logo.hashCode());
            result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
            result = prime * result + ((information == null) ? 0 : information.hashCode());
            result = prime * result + ((candidatesEmails == null) ? 0 : candidatesEmails.hashCode());
            result = prime * result + ((candidatesNames == null) ? 0 : candidatesNames.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TicketDetails other = (TicketDetails) obj;
        if (nodeId != null)
        {
            if (nodeId.equals(other.nodeId))
                return true;
            else return false;
        }
        else
        {
            if (other.nodeId != null)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (information == null) {
                if (other.information != null)
                    return false;
            } else if (!information.equals(other.information))
                return false;
            if (candidatesEmails == null) {
                if (other.candidatesEmails != null)
                    return false;
            } else if (!candidatesEmails.equals(other.candidatesEmails))
                return false;
            if (candidatesNames == null) {
                if (other.candidatesNames != null)
                    return false;
            } else if (!candidatesNames.equals(other.candidatesNames))
                return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCandidatesEmails() {
        return candidatesEmails;
    }

    public void setCandidatesEmails(String candidatesEmails) {
        this.candidatesEmails = candidatesEmails;
    }

    public String getCandidatesNames() {
        return candidatesNames;
    }

    public void setCandidatesNames(String candidatesNames) {
        this.candidatesNames = candidatesNames;
    }
}