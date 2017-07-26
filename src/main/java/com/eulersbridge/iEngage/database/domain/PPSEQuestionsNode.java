package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.rest.domain.PPSEQuestions;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class PPSEQuestionsNode extends Node {
  private static Logger LOG = LoggerFactory.getLogger(PPSEQuestionsNode.class);

  private Float q1;
  private Float q2;
  private Float q3;
  private Float q4;

  @Relationship(type = DataConstants.HAS_PPSEQuestions_LABEL, direction = Relationship.INCOMING)
  private Node user;

  public PPSEQuestionsNode() {
    super();
  }

  public PPSEQuestionsNode(Float q1, Float q2, Float q3, Float q4) {
    super();
    this.q1 = q1;
    this.q2 = q2;
    this.q3 = q3;
    this.q4 = q4;
  }

  public static PPSEQuestionsNode fromRestDomain(PPSEQuestions d) {
    PPSEQuestionsNode p = new PPSEQuestionsNode();
    p.setQ1(d.getQ1());
    p.setQ2(d.getQ2());
    p.setQ3(d.getQ3());
    p.setQ4(d.getQ4());
    return p;
  }

  public PPSEQuestions toRestDomain(){
    PPSEQuestions rest = new PPSEQuestions();
    rest.setNodeId(getNodeId());
    rest.setQ1(getQ1());
    rest.setQ2(getQ2());
    rest.setQ3(getQ3());
    rest.setQ4(getQ4());
    return rest;
  }

  public Float getQ1() {
    return q1;
  }

  public void setQ1(Float q1) {
    this.q1 = q1;
  }

  public Float getQ2() {
    return q2;
  }

  public void setQ2(Float q2) {
    this.q2 = q2;
  }

  public Float getQ3() {
    return q3;
  }

  public void setQ3(Float q3) {
    this.q3 = q3;
  }

  public Float getQ4() {
    return q4;
  }

  public void setQ4(Float q4) {
    this.q4 = q4;
  }

  public Node getUser() {
    return user;
  }

  public User getUser$() {
    return (User) user;
  }

  public void setUser(Node user) {
    this.user = user;
  }
}
