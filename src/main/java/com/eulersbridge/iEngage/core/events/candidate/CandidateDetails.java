package com.eulersbridge.iEngage.core.events.candidate;
import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Yikai Gong
 */

public class CandidateDetails extends Details {
    private String candidateEmail;
    private String information;
    private String policyStatement;
    private Set<String> pictures;
    private String familyName;
    private String givenName;

    private static Logger LOG = LoggerFactory.getLogger(CandidateDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", candidateEmail = ");
        buff.append(getCandidateEmail());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", policyStatement = ");
        buff.append(getPolicyStatement());
        buff.append(", pictures = ");
        buff.append(getPictures());
        buff.append(", familyName = ");
        buff.append(getFamilyName());
        buff.append(", givenName = ");
        buff.append(getGivenName());
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
            result = prime * result	+ ((candidateEmail == null) ? 0 : candidateEmail.hashCode());
            result = prime * result	+ ((information == null) ? 0 : information.hashCode());
            result = prime * result + ((policyStatement == null) ? 0 : policyStatement.hashCode());
            result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
            result = prime * result + ((familyName == null) ? 0 : familyName.hashCode());
            result = prime * result + ((givenName == null) ? 0 : givenName.hashCode());
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
        CandidateDetails other = (CandidateDetails) obj;
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
            if (candidateEmail == null) {
                if (other.candidateEmail != null)
                    return false;
            } else if (!candidateEmail.equals(other.candidateEmail))
                return false;
            if (information == null) {
                if (other.information != null)
                    return false;
            } else if (!information.equals(other.information))
                return false;
            if (policyStatement == null) {
                if (other.policyStatement != null)
                    return false;
            } else if (!policyStatement.equals(other.policyStatement))
                return false;
        }
        return true;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPolicyStatement() {
        return policyStatement;
    }

    public void setPolicyStatement(String policyStatement) {
        this.policyStatement = policyStatement;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
