package com.eulersbridge.iEngage.rest.domain;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author Yikai Gong
 */

public class PPSEQuestions extends ResourceSupport {

  private Long nodeId;
  private Float q1;
  private Float q2;
  private Float q3;
  private Float q4;

  public PPSEQuestions() {
    super();
  }

  public PPSEQuestions(Float q1, Float q2, Float q3, Float q4) {
    super();
    this.q1 = q1;
    this.q2 = q2;
    this.q3 = q3;
    this.q4 = q4;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
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

  @Override
  public String toString() {
    return "PPSEQuestions{" +
      "nodeId=" + nodeId +
      ", q1=" + q1 +
      ", q2=" + q2 +
      ", q3=" + q3 +
      ", q4=" + q4 +
      '}';
  }
}
