/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import org.springframework.hateoas.ResourceSupport;

import java.util.Iterator;

/**
 * @author Greg Newitt
 *
 */
public class Photos extends ResourceSupport
{
	Long totalPhotos;
	Integer totalPages;
	Iterator<PhotoDomain> photos;

	/**
	 * @return the totalPhotos
	 */
	public Long getTotalPhotos()
	{
		return totalPhotos;
	}

	/**
	 * @param totalPhotos the totalPhotos to set
	 */
	public void setTotalPhotos(Long totalPhotos)
	{
		this.totalPhotos = totalPhotos;
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

	/**
	 * @return the photos
	 */
	public Iterator<PhotoDomain> getPhotos()
	{
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(Iterator<PhotoDomain> photos)
	{
		this.photos = photos;
	}

	public static  Photos fromPhotosIterator(Iterator<PhotoDomain> iter, Long totalPhotos, Integer totalPages)
	{
	    Photos fotos = new Photos();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    fotos.totalPhotos= totalPhotos;
	    fotos.totalPages = totalPages;
	    fotos.photos = iter;
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
//	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}

	    return fotos;
	}

}
