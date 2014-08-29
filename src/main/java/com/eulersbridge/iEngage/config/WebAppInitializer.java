/**
 * 
 */
package com.eulersbridge.iEngage.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author Greg Newitt
 *
 */
public class WebAppInitializer /*implements WebApplicationInitializer 
{

	* (non-Javadoc)
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 *
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException 
	{
		WebApplicationContext rootContext=createRootContext(servletContext);
		
		configureSpringSecurity(servletContext,rootContext);

	}

	private void configureSpringSecurity(ServletContext servletContext,
			WebApplicationContext rootContext) 
	{
		FilterRegistration.Dynamic springSecurity = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain",rootContext));
		springSecurity.addMappingForUrlPatterns(null, true, "/*");
		
	}

	private WebApplicationContext createRootContext(ServletContext servletContext) 
	{
		AnnotationConfigWebApplicationContext rootContext=new AnnotationConfigWebApplicationContext();
		rootContext.register(CoreConfig.class,SecurityConfig.class);
		rootContext.refresh();
		
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.setInitParameter("defaultHtmlEscape", "true");
		return rootContext;
	}
*/{
}
