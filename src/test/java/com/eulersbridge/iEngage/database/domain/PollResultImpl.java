/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

/**
 * @author Greg Newitt
 *
 */
public class PollResultImpl extends PollResultTemplate
{
	Integer answers;
	Integer frequency;

	/**
	 * @param answers
	 * @param frequency
	 */
	public PollResultImpl(Integer answers,
			Integer frequency)
	{
		super();
		this.answers = answers;
		this.frequency = frequency;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.database.domain.PollResultTemplate#getAnswer()
	 */
	@Override
	public Integer getAnswer()
	{
		return answers;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.database.domain.PollResultTemplate#getFrequency()
	 */
	@Override
	public Integer getFrequency()
	{
		return frequency;
	}

}
