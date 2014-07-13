package com.eulersbridge.iEngage.config;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eulersbridge.iEngage.Application;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class CoreDomainIntegrationTest 
{
    private static Logger LOG = LoggerFactory.getLogger(CoreDomainIntegrationTest.class);

	@Autowired UserService userService;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public final void shouldAddNewUserToSystem() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("shouldAddNewUserToSystem()");
		ReadUserEvent reusev=RestDataFixture.customEmailUser2("greg.newitt2@unimelb.edu.au");
		CreateUserEvent ev = new CreateUserEvent(reusev.getReadUserDetails());

		if (LOG.isDebugEnabled()) LOG.debug(ev.getUserDetails().toString());
		if (userService!=null)
		{
			CreateUserEvent crusev=new CreateUserEvent(ev.getUserDetails());
			userService.signUpNewUser(crusev);
			
			ReadUserEvent foundUser=userService.requestReadUser(new RequestReadUserEvent(ev.getUserDetails().getEmail()));
			assertTrue(foundUser.isEntityFound());
//			userService.deleteUser(ev.getUserDetails().getEmail())
			UserDeletedEvent udev=userService.deleteUser(new DeleteUserEvent(ev.getUserDetails().getEmail()));
			assertTrue(udev.isDeletionCompleted());
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("UserService NOT BEING AUTOWIRED");
		}
//TODO		fail("Not yet implemented"); 
	}


}
