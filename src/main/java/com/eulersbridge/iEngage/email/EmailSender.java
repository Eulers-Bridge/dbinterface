package com.eulersbridge.iEngage.email;


import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

//import com.eulersbridge.iEngage.rest.controller.ElectionController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;

public class EmailSender {

    private final JavaMailSender mailSender;
    private final VelocityEngine velocityEngine;
    //private ApplicationConfig config;
    private static Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    public EmailSender(JavaMailSender mailSender, VelocityEngine velocityEngine) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }


    public EmailVerification sendVerificationEmail(final EmailVerification emailVerificationModel) {
        Map<String, Object> resources = new HashMap<String, Object>();
          return sendVerificationEmail(emailVerificationModel, "Email Verification",
                  "/validateEmailBody.vm", resources);
    }

    /*public EmailServiceTokenModel sendRegistrationEmail(final EmailServiceTokenModel emailVerificationModel) {
        Map<String, String> resources = new HashMap<String, String>();
          return sendVerificationEmail(emailVerificationModel, config.getEmailRegistrationSubjectText(),
                  "META-INF/velocity/RegistrationEmail.vm", resources);
    }
/*
    public EmailServiceTokenModel sendLostPasswordEmail(final EmailServiceTokenModel emailServiceTokenModel) {
        Map<String, String> resources = new HashMap<String, String>();
         return sendVerificationEmail(emailServiceTokenModel, config.getLostPasswordSubjectText(),
                 "META-INF/velocity/LostPasswordEmail.vm", resources);
    }*/


    private void addInlineResource(MimeMessageHelper messageHelper, String resourcePath, String resourceIdentifier) throws MessagingException {
        Resource resource = new ClassPathResource(resourcePath);
        messageHelper.addInline(resourceIdentifier, resource);
    }

    /**
     * Sends e-mail using Velocity template for the body and 
     * the properties passed in as Velocity variables.
     * 
     * @param   msg                 The e-mail message to be sent, except for the body.
     * @param   hTemplateVariables  Variables to use when processing the template. 
     */
    private EmailVerification sendVerificationEmail(final EmailVerification emailVerificationModel,
    		final String emailSubject, final String velocityModel, final Map<String, Object> hTemplateVariables) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
               message.setTo(emailVerificationModel.getEmailAddress());
               //message.setReplyTo("info@eulersbridge.com");
               //message.setFrom("info@eulersbridge.com");
               message.setSubject(emailSubject);
               hTemplateVariables.put("email", emailVerificationModel);
               String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityModel, hTemplateVariables);
               
               LOG.debug("body={}", body);

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
         
         LOG.debug("Sending {} token to : {}",emailVerificationModel.getTokenType().toString(), emailVerificationModel.getEmailAddress());
         //this.mailSender.setHost("mail.eulersbridge.com");
         this.mailSender.send(preparator);
         return emailVerificationModel;
    }
}
