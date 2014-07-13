package com.eulersbridge.iEngage.database.repository;


import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.users.*;

import java.util.*;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;

public class UserMemoryRepository implements UserRepository {

  private Map<Long, User> users;

  public UserMemoryRepository(final Map<Long, User> users) {
    this.users = Collections.unmodifiableMap(users);
  }

  @Override
  public synchronized User save(User user) {

    Map<Long, User> modifiableUsers = new HashMap<Long, User>(users);
    modifiableUsers.put(user.getNodeId(), user);
    this.users = Collections.unmodifiableMap(modifiableUsers);

    return user;
  }

  @Override
  public synchronized void delete(Long key) {
    if (users.containsKey(key)) {
      Map<Long, User> modifiableusers = new HashMap<Long, User>(users);
      modifiableusers.remove(key);
      this.users = Collections.unmodifiableMap(modifiableusers);
    }
  }

/*  @Override
  public User findById(Long key) {
    return users.get(key);
  }
*/
  /*
  @Override
  public List<User> findAll() {
    return Collections.unmodifiableList(new ArrayList<User>(users.values()));
  }
*/
@Override
public Result<User> findAll() 
{
	// TODO Auto-generated method stub
	return null;
}

@Override
public Result<User> findAll(Sort arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Class getStoredJavaType(Object arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Result<User> query(String arg0, Map<String, Object> arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Page<User> findAll(Pageable arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public long count() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void delete(User arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void delete(Iterable<? extends User> arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteAll() {
	// TODO Auto-generated method stub
	
}

@Override
public boolean exists(Long arg0) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Iterable<User> findAll(Iterable<Long> arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public User findOne(Long arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public <S extends User> Iterable<S> save(Iterable<S> arg0) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Result<User> findAllByPropertyValue(String arg0, Object arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Result<User> findAllByQuery(String arg0, Object arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Result<User> findAllByRange(String arg0, Number arg1, Number arg2) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public User findByPropertyValue(String arg0, Object arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Result<User> findAllBySchemaPropertyValue(String arg0, Object arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public User findBySchemaPropertyValue(String arg0, Object arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public <N> Iterable<User> findAllByTraversal(N arg0, TraversalDescription arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public User findByEmail(String email) {
	// TODO Auto-generated method stub
	return null;
}

}
