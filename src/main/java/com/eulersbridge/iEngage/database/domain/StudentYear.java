package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.eulersbridge.iEngage.core.events.studentYear.StudentYearDetails;

@NodeEntity
public class StudentYear 
{
	@GraphId 
	Long nodeId;
	String year;
	Long start;
	Long end;
	@RelatedTo(type = "HAS_STUDENT_YEAR", direction=Direction.BOTH) @Fetch
	private	Institution  institution;
	@RelatedTo(type = "HAS_NEWS", direction=Direction.BOTH)
	private	Set<NewsArticle>  news;
	
	public static StudentYear fromDetails(StudentYearDetails newYear) 
	{
		StudentYear sy=new StudentYear();
		sy.setYear(newYear.getYear());
		sy.setStart(newYear.getStart());
		sy.setEnd(newYear.getEnd());
		Institution inst=new Institution();
		inst.setNodeId(newYear.getInstitutionId());
		sy.setInstitution(inst);
		sy.setNodeId(newYear.getNodeId());
		return sy;
	}
	
	public StudentYearDetails toDetails()
	{
		StudentYearDetails syd=new StudentYearDetails(getYear(), getStart(), getEnd(), getInstitution().getNodeId());
		syd.setNodeId(getNodeId());
		return syd;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the start
	 */
	public Long getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Long start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Long getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Long end) {
		this.end = end;
	}

	/**
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution inst) 
	{
		this.institution=inst;
	}

	/**
	 * @return the news
	 */
	public Set<NewsArticle> getNews() {
		return news;
	}

	/**
	 * @param news the news to set
	 */
	public void setNews(Set<NewsArticle> news) {
		this.news = news;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "StudentYear [nodeId=" + nodeId + ", year=" + year + ", start="
				+ start + ", end=" + end + ", institution=" + institution.getNodeId() + "]";
	} 

}
