package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.rest.controller.CanditateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Candidate extends ResourceSupport{
    private Long candidateId;
    private String candidateEmail;
    private String information;
    private String policyStatement;
    private Set<String> pictures;
    private String familyName;
    private String givenName;

    private static Logger LOG = LoggerFactory.getLogger(Candidate.class);

    public Candidate() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Candidate fromCandidateDetails(CandidateDetails candidateDetails){
        Candidate candidate = new Candidate();
        String simpleName = Candidate.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        candidate.setCandidateId(candidateDetails.getCandidateId());
        candidate.setCandidateEmail(candidateDetails.getCandidateEmail());
        candidate.setInformation(candidateDetails.getInformation());
        candidate.setPolicyStatement(candidateDetails.getPolicyStatement());
        candidate.setPictures(candidateDetails.getPictures());
        candidate.setFamilyName(candidateDetails.getFamilyName());
        candidate.setGivenName(candidateDetails.getGivenName());

        // {!begin selfRel}
        candidate.add(linkTo(CanditateController.class).slash(name)
                .slash(candidate.candidateId).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        candidate.add(linkTo(CanditateController.class).slash(name + 's')
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}

        return candidate;
    }

    public CandidateDetails toCandidateDetails(){
        CandidateDetails candidateDetails = new CandidateDetails();
        candidateDetails.setCandidateId(getCandidateId());
        candidateDetails.setCandidateEmail(getCandidateEmail());
        candidateDetails.setInformation(getInformation());
        candidateDetails.setPolicyStatement(getPolicyStatement());
        candidateDetails.setPictures(getPictures());
        candidateDetails.setFamilyName(getFamilyName());
        candidateDetails.setGivenName(getGivenName());
        return candidateDetails;
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
