package com.eulersbridge.iEngage.email;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;

public class EmailVerification extends Email implements Serializable 
{

    private final String token;
    private final String tokenType;
    private final String velocityModel;
	@Autowired
    private VelocityEngine velocityEngine;

    private static Logger LOG = LoggerFactory.getLogger(EmailVerification.class);

    public EmailVerification(User user, VerificationToken token) 
    {
    	super(user.getEmail(),user.getFirstName() + " " + user.getLastName(),"greg.newitt@eulersbridge.com","Email Verification Test");
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.velocityModel = "/validateEmailBody.vm";
        VelocityEngineFactory vfact=new VelocityEngineFactory();
		Properties velocityProperties=new Properties();
		velocityProperties.setProperty("resource.loader", "class");
		velocityProperties.setProperty("class.respource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        vfact.setVelocityProperties(velocityProperties);
        VelocityConfigurer vcf=new VelocityConfigurer();
        HashMap<String,Object> vCfMap=new HashMap<String,Object>();
        vCfMap.put("runtime.log.invalid.reference", true);
        vCfMap.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
        vCfMap.put("runtime.log.logsystem.log4j.logger", "velocity");
        vCfMap.put("input.encoding", "UTF-8");
        vCfMap.put("output.encoding", "UTF-8");
        vCfMap.put("directive.include.output.errormsg.start","");
        vCfMap.put("directive.parse.max.depth","10");
        vCfMap.put("directive.set.null.allowed",true);
        vCfMap.put("velocimacro.library.autoreload",true);
        vCfMap.put("velocimacro.permissions.allow.inline",true);
        vCfMap.put("velocimacro.permissions.allow.inline.to.replace.global",false);
        vCfMap.put("velocimacro.permissions.allow.inline.local.scope",false);
        vCfMap.put("velocimacro.context.localscope","false");
        vCfMap.put("runtime.interpolate.string.literals","true");
        vCfMap.put("resource.manager.class","org.apache.velocity.runtime.resource.ResourceManagerImpl");
        vCfMap.put("resource.manager.cache.class","org.apache.velocity.runtime.resource.ResourceCacheImpl");
        vCfMap.put("resource.loader","webapp, class, ds");
        vCfMap.put("class.resource.loader.description","Velocity Classpath Resource Loader");
        vCfMap.put("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        vCfMap.put("webapp.resource.loader.class","org.apache.velocity.tools.view.WebappResourceLoader");
        vCfMap.put("webapp.resource.loader.path","/WEB-INF/views/");
        vCfMap.put("webapp.resource.loader.cache","false");
        vCfMap.put("webapp.resource.loader.modificationCheckInterval","2");
       
        vcf.setVelocityPropertiesMap(vCfMap);
/*        <property name="velocityPropertiesMap">
                <entry key="ds.resource.loader.instance"><ref bean="templateLoader"/></entry>
                <entry key="ds.resource.loader.resource.table"><value>templates</value></entry>
                <entry key="ds.resource.loader.resource.keycolumn"><value>code</value></entry>
                <entry key="ds.resource.loader.resource.templatecolumn"><value>content</value></entry>
                <entry key="ds.resource.loader.resource.timestampcolumn"><value>updated</value></entry>
                <entry key="ds.resource.loader.cache"><value>false</value></entry>
*/        
        
        try {
			this.velocityEngine = vfact.createVelocityEngine();
//			this.velocityEngine = vcf.createVelocityEngine();
		} catch (VelocityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String getEncodedToken() 
    {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() 
    {
        return tokenType;
    }
    
    @Override
    public MimeMessagePreparator generatePreparator() throws MessagingException
    {
    	MimeMessagePreparator preparator = new MimeMessagePreparator() 
    	{
    		public void prepare(MimeMessage mimeMessage) throws Exception 
    		{
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
    			message.setTo(new InternetAddress(getRecipientEmailAddress()));
    			String body=null;
    			message.setReplyTo(new InternetAddress(getSenderEmailAddress()));
    			message.setFrom(new InternetAddress(getSenderEmailAddress()));
    			message.setSubject(getSubject());
    			final Map<String, Object> hTemplateVariables = new HashMap<String,Object>();

    			hTemplateVariables.put("email", this);
    			if (LOG.isDebugEnabled()) LOG.debug("Velocity engine :"+velocityEngine);
    			if (LOG.isDebugEnabled())
    			body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityModel,"UTF-8", hTemplateVariables);
            
            if(LOG.isDebugEnabled()) LOG.debug("body={}", body);
            message.setText(body, true);

           /*for(String resourceIdentifier: hTemplateVariables.keySet()) {
          	 //Inline resources are added to the mime message using the specified Content-ID. 
          	  //The order in which you are adding the text and the resource are very important. 
          	  //Be sure to first add the text and after that the resources. 
          	  //If you are doing it the other way around, it won't work!
        	   addInlineResource(message, hTemplateVariables.get(resourceIdentifier).toString(), resourceIdentifier);
           }*/
                 
    		}
    	};
    	
    	return preparator;
    }

}
