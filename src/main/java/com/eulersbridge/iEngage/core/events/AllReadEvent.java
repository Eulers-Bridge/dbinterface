package com.eulersbridge.iEngage.core.events;

public class AllReadEvent
{
	Long parentId;
	Iterable<? extends Details> details;
	private Long totalItems;
	private Integer totalPages;

	protected boolean entityFound = true;

	/**
	 * @param parentId
	 */
	public AllReadEvent(Long parentId)
	{
		super();
		this.parentId = parentId;
	}

	/**
	 * @param parentId
	 * @param totalItems
	 * @param totalPages
	 */
	public AllReadEvent(Long parentId, Long totalItems, Integer totalPages)
	{
		super();
		this.parentId = parentId;
		this.totalItems = totalItems;
		this.totalPages = totalPages;
	}

	/**
	 * @param parentId
	 * @param details
	 */
	public AllReadEvent(Long parentId, Iterable<Details> details)
	{
		super();
		this.parentId = parentId;
		this.details = details;
	}

	/**
	 * @param parentId
	 * @param details
	 * @param totalItems
	 * @param totalPages
	 */
	public AllReadEvent(Long parentId, Iterable<Details> details,
			Long totalItems, Integer totalPages)
	{
		super();
		this.parentId = parentId;
		this.details = details;
		this.totalItems = totalItems;
		this.totalPages = totalPages;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return the details
	 */
	public Iterable<? extends Details> getDetails()
	{
		return details;
	}

	/**
	 * @return the totalItems
	 */
	public Long getTotalItems()
	{
		return totalItems;
	}

	/**
	 * @param totalItems the totalItems to set
	 */
	public void setTotalItems(Long totalEvents)
	{
		this.totalItems = totalEvents;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages()
	{
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages)
	{
		this.totalPages = totalPages;
	}

	public boolean isEntityFound()
	{
		return entityFound;
	}

	public static AllReadEvent notFound(Long id)
	{
		AllReadEvent ev = new AllReadEvent(id);
		ev.entityFound = false;
		return ev;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "AllReadEvent [parentId=" + parentId + ", details=" + details
				+ ", totalItems=" + totalItems + ", totalPages=" + totalPages
				+ ", entityFound=" + entityFound + "]";
	}
}
