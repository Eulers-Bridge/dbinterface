package com.eulersbridge.iEngage.config;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=ApplicationTest.class)
//@ContextConfiguration(classes = {ApplicationTest.class})
//@ContextConfiguration("/test-application-context.xml")
public class CoreDomainITCase 
{
    private static Logger LOG = LoggerFactory.getLogger(CoreDomainITCase.class);

//	@Autowired 
	UserService userService;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Ignore
	@Test
	public final void shouldAddNewUserToSystem() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("shouldAddNewUserToSystem()");
		ReadUserEvent reusev=RestDataFixture.customEmailUser2("greg.newitt@eulersbridge.com");
		CreateUserEvent ev = new CreateUserEvent((UserDetails) reusev.getDetails());

		if (LOG.isDebugEnabled()) LOG.debug(ev.getDetails().toString());
		if (userService!=null)
		{
			CreateUserEvent crusev=new CreateUserEvent((UserDetails) ev.getDetails());
			userService.signUpNewUser(crusev);
			
			ReadUserEvent foundUser=userService.readUser(new RequestReadUserEvent(((UserDetails)ev.getDetails()).getEmail()));
			assertTrue(foundUser.isEntityFound());
//			userService.deleteUser(ev.getUserDetails().getEmail())
			UserDeletedEvent udev=userService.deleteUser(new DeleteUserEvent(((UserDetails)ev.getDetails()).getEmail()));
			assertTrue(udev.isDeletionCompleted());
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("UserService NOT BEING AUTOWIRED");
		}
//TODO		fail("Not yet implemented"); 
	}


}
