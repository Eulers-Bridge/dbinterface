package com.eulersbridge.iEngage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class iEngageWebXml extends SpringBootServletInitializer 
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
    	return application.sources(Application.class);
    }    
}
