package com.eulersbridge.iEngage.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eulersbridge.iEngage.core.services.UserEventHandler;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityMemoryRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityRepository;
import com.eulersbridge.iEngage.database.repository.UserMemoryRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;

@Configuration
public class TestCoreConfig 
{
	Map<Long, User> users= new HashMap<Long,User>();
	Map<Long, Institution> institutions= new HashMap<Long,Institution>();
	Map<Long, VerificationToken> tokens= new HashMap<Long,VerificationToken>();
	UserRepository userRepo=new UserMemoryRepository(users);
	PersonalityRepository personRepo=new PersonalityMemoryRepository();
	InstitutionRepository instRepo;
	VerificationTokenRepository tokenRepo;
	
    private static Logger LOG = LoggerFactory.getLogger(TestCoreConfig.class);

	public TestCoreConfig()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CoreConfig()");
	}

	@Bean
	public UserService createUserService() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("createService()");
	    return new UserEventHandler(userRepo,personRepo,instRepo,tokenRepo);
	}

/*	  @Bean
	  public OrdersRepository createRepo() {
	    return new OrdersMemoryRepository(new HashMap<UUID, Order>());
	  }
*/

}
