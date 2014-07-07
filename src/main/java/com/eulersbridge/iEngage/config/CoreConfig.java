package com.eulersbridge.iEngage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eulersbridge.iEngage.core.services.UserEventHandler;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

@Configuration
public class CoreConfig 
{
	@Autowired
	UserRepository userRepo;
	@Autowired
	InstitutionRepository instRepo;
	
    private static Logger LOG = LoggerFactory.getLogger(CoreConfig.class);

	public CoreConfig()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CoreConfig()");
	}

	@Bean
	public UserService createService() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("createService()");
	    return new UserEventHandler(userRepo,instRepo);
	}

/*	  @Bean
	  public OrdersRepository createRepo() {
	    return new OrdersMemoryRepository(new HashMap<UUID, Order>());
	  }
*/

}
