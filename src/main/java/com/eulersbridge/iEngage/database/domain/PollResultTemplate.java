package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult()
public class PollResultTemplate {
  Integer answer;
  Integer frequency;

  public PollResultTemplate() {
  }

  public Integer getAnswer() {
    return answer;
  }

  public void setAnswer(Integer answer) {
    this.answer = answer;
  }

  public Integer getFrequency() {
    return frequency;
  }

  public void setFrequency(Integer frequency) {
    this.frequency = frequency;
  }
}
