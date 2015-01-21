package com.eulersbridge.iEngage.core.events.polls;

public class PollResult
{
	Integer answer;
	Integer count;
	/**
	 * 
	 */
	public PollResult()
	{
		super();
	}
	/**
	 * @param answer
	 * @param count
	 */
	public PollResult(Integer answer, Integer count)
	{
		super();
		this.answer = answer;
		this.count = count;
	}
	/**
	 * @return the answer
	 */
	public Integer getAnswer()
	{
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(Integer answer)
	{
		this.answer = answer;
	}
	/**
	 * @return the count
	 */
	public Integer getCount()
	{
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count)
	{
		this.count = count;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PollResult other = (PollResult) obj;
		if (answer == null)
		{
			if (other.answer != null) return false;
		}
		else if (!answer.equals(other.answer)) return false;
		if (count == null)
		{
			if (other.count != null) return false;
		}
		else if (!count.equals(other.count)) return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PollResult [answer=" + answer + ", count=" + count + "]";
	}
	
	

}
