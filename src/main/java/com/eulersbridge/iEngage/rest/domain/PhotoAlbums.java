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
public class PhotoAlbums extends ResourceSupport
{
	Long totalPhotoAlbums;
	Integer totalPages;
	Iterator<PhotoAlbum> photoAlbums;

	/**
	 * @return the totalPhotos
	 */
	public Long getTotalPhotoAlbums()
	{
		return totalPhotoAlbums;
	}

	/**
	 * @param totalPhotoAlbums the totalPhotoAlbums to set
	 */
	public void setTotalPhotoAlbums(Long totalPhotoAlbums)
	{
		this.totalPhotoAlbums = totalPhotoAlbums;
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
	 * @return the photoAlbums
	 */
	public Iterator<PhotoAlbum> getPhotoAlbums()
	{
		return photoAlbums;
	}

	/**
	 * @param photoAlbums the photoAlbums to set
	 */
	public void setPhotoAlbums(Iterator<PhotoAlbum> photoAlbums)
	{
		this.photoAlbums = photoAlbums;
	}

	public static  PhotoAlbums fromPhotoAlbumsIterator(Iterator<PhotoAlbum> iter, Long totalPhotoAlbums, Integer totalPages)
	{
	    PhotoAlbums fotoAlbums = new PhotoAlbums();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    fotoAlbums.totalPhotoAlbums= totalPhotoAlbums;
	    fotoAlbums.totalPages = totalPages;
	    fotoAlbums.photoAlbums = iter;
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
//	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}

	    return fotoAlbums;
	}

}
