package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Candidate {
    @GraphId
    private Long candidateId;
    private String candidateEmail;
    private String information;
    private String policyStatement;
    private Iterable<String> pictures;
    private String familyName;
    private String givenName;

    private static Logger LOG = LoggerFactory.getLogger(Candidate.class);

    public Candidate() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Candidate fromCandidateDetails(CandidateDetails candidateDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromCandidateDetails()");
        Candidate candidate = new Candidate();
        if (LOG.isTraceEnabled()) LOG.trace("candidateDetails "+candidateDetails);
        candidate.setCandidateId(candidateDetails.getNodeId());
        candidate.setCandidateEmail(candidateDetails.getCandidateEmail());
        candidate.setInformation(candidateDetails.getInformation());
        candidate.setPolicyStatement(candidateDetails.getPolicyStatement());
        candidate.setPictures(candidateDetails.getPictures());
        candidate.setFamilyName(candidateDetails.getFamilyName());
        candidate.setGivenName(candidateDetails.getGivenName());

        if (LOG.isTraceEnabled()) LOG.trace("candidate "+candidate);
        return candidate;
    }

    public CandidateDetails toCandidateDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toCandidateDetails()");
        CandidateDetails candidateDetails = new CandidateDetails();
        if (LOG.isTraceEnabled()) LOG.trace("candidate "+this);
        candidateDetails.setNodeId(getCandidateId());
        candidateDetails.setCandidateEmail(getCandidateEmail());
        candidateDetails.setInformation(getInformation());
        candidateDetails.setPolicyStatement(getPolicyStatement());
        candidateDetails.setFamilyName(getFamilyName());
        candidateDetails.setGivenName(getGivenName());

        HashSet<String> pictures = new HashSet<String>();
        if (getPictures()!=null)
        {
            Iterator<String> iter = getPictures().iterator();
            while(iter.hasNext())
            {
                String url = iter.next();
                pictures.add(url);
            }
        }
        candidateDetails.setPictures(pictures);
        if (LOG.isTraceEnabled()) LOG.trace("candidateDetails; "+ candidateDetails);
        return candidateDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getCandidateId());
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

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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

    public Iterable<String> getPictures() {
        return pictures;
    }

    public void setPictures(Iterable<String> picture) {
        this.pictures = picture;
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
