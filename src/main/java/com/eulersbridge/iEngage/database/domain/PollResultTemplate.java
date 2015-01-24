package com.eulersbridge.iEngage.database.domain;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult()
public interface PollResultTemplate
{
	@ResultColumn ("answer")
	Integer getAnswer();

	@ResultColumn ("frequency")
	Integer getFrequency();
}
