package com.eulersbridge.iEngage.database.repository;


import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import java.util.*;

import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.repository.query.Param;

public class UserMemoryRepository implements UserRepository {

  private Map<Long, User> users;
	Long maxKey=(long) 0;

  public UserMemoryRepository(final Map<Long, User> users) {
    this.users = Collections.unmodifiableMap(users);
  }

  @Override
  public synchronized <S extends User> S save(S user) {

    Map<Long, User> modifiableUsers = new HashMap<Long, User>(users);
    if (null==user.getNodeId())
    {
    	maxKey++;
    	user.setNodeId(maxKey);
    }

    modifiableUsers.put(user.getNodeId(), user);
    this.users = Collections.unmodifiableMap(modifiableUsers);

    return user;
  }

  @Override
  public synchronized void delete(Long key) 
  {
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
public Class<?> getStoredJavaType(Object arg0) {
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
public User findOne(Long nodeId) 
{
	return users.get(nodeId);
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
public User findByEmail(String email) 
{
	Collection<User> values = users.values();
	Iterator<User> iter=values.iterator();
	while (iter.hasNext())
	{
		User user=iter.next();
		if (user.getEmail().equals(email))
		{
			return user;
		}
	}
	return null;
}

@Override
public void verifyUser(Long userId, Long tokenId, boolean isVerified) 
{
	// TODO Auto-generated method stub
	
}

@Override
public Personality addPersonality(Long userId, Long personalityId) 
{
	Personality retValue=new Personality();
	if (findOne(userId)!=null)
	{
		retValue.setNodeId(1L);
	}
	return retValue;
}

@Override
public VoteReminder addVoteReminder(Long userId, Long electionId, Long date,
		String location) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public VoteRecord addVoteRecord(Long userId, Long electionId, String location) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public VoteRecord readVoteRecord(Long id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public VoteReminder readVoteReminder(Long id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public VoteRecord deleteVoteRecord(Long id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public VoteReminder deleteVoteReminder(Long id) {
	// TODO Auto-generated method stub
	return null;
}

    @Override
    public Page<User> findByArticleId(@Param("articleId") Long id, Pageable p) {
        return null;
    }

    @Override
    public Page<User> findByLikeableObjId(@Param("objId") Long id, Pageable p) {
        return null;
    }

    @Override
    public Like like(@Param("email") String email, @Param("likedId") Long likedId) {
        return null;
    }

    @Override
    public void unlike(@Param("email") String email, @Param("likedId") Long likedId) {

    }

	@Override
	public User findByContactNumber(String contactNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
