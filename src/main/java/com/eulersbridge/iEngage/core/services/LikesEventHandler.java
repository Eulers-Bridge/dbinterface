package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.LikesService;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.NodeRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */
@Service
public class LikesEventHandler implements LikesService {
  private static Logger LOG = LoggerFactory.getLogger(LikesEventHandler.class);

  UserRepository userRepository;
  NodeRepository nodeRepository;

  @Autowired
  public LikesEventHandler(UserRepository userRepository, NodeRepository nodeRepository) {
    this.userRepository = userRepository;
    this.nodeRepository = nodeRepository;
  }

  @Override
  public LikedEvent isLikedBy(LikeEvent likeEvent) {
    boolean result = true;
    LikedEvent retValue;
    String email = likeEvent.getEmailAddress();
    Long nodeId = likeEvent.getNodeId();

    retValue = checkParams(email, nodeId);
    if (null == retValue) {
      Long like = userRepository.isLikedBy(email, nodeId);
      if (like != null)
        result = true;
      else result = false;
      retValue = new LikedEvent(nodeId, email, result);
    }
    return retValue;
  }

  @Override
  public LikedEvent like(LikeEvent likeEvent) {
    boolean result = true;
    LikedEvent retValue;
    String email = likeEvent.getEmailAddress();
    Long nodeId = likeEvent.getNodeId();

    retValue = checkParams(email, nodeId);
    if (null == retValue) {
      Long like = userRepository.like(email, nodeId);
      System.out.println("like:" + like);
      if (like != null)
        result = true;
      else
        result = false;
      retValue = new LikedEvent(nodeId, email, result);
    }
    return retValue;
  }

  @Override
  public LikedEvent unlike(LikeEvent unlikeEvent) {
    boolean result = true;
    LikedEvent retValue = null;
    String email = unlikeEvent.getEmailAddress();
    Long nodeId = unlikeEvent.getNodeId();

    retValue = checkParams(email, nodeId);
    if (null == retValue) {
      userRepository.unlike(email, nodeId);
      retValue = new LikedEvent(nodeId, email, result);
    }
    return retValue;
  }

  private LikedEvent checkParams(String email, Long nodeId) {
    User user = userRepository.findByEmail(email, 0);
    if (null == user) {
      return LikedEvent.userNotFound(nodeId, email);
    }
    Node item = nodeRepository.findOne(nodeId, 0);
    if (null == item) {
      return LikedEvent.entityNotFound(nodeId, email);
    }
    Long like = userRepository.isLikedBy(email, nodeId);
    if (like > 0) {
      return new LikedEvent(nodeId, email, false);
    }
    return null;
  }

  @Override
  public LikeableObjectLikesEvent likes(LikesLikeableObjectEvent likesLikeableObjectEvent, Sort.Direction sortDirection, int pageNumber, int pageSize) {

    Long objId = likesLikeableObjectEvent.getLikeableObjId();
    ArrayList<UserDetails> userDetailses = new ArrayList<UserDetails>();
    LikeableObjectLikesEvent likeableObjectLikesEvent;

    if (LOG.isDebugEnabled()) LOG.debug("objId " + objId);
    Pageable pageable = new PageRequest(pageNumber, pageSize, sortDirection, "a.date");
    Page<User> users = userRepository.findByLikeableObjId(objId, pageable);
    if (users != null) {
      Iterator<User> iter = users.iterator();
      while (iter.hasNext()) {
        if (LOG.isDebugEnabled())
          LOG.debug("Total elements = " + users.getTotalElements() + " total pages =" + users.getTotalPages());

        User user = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + user.getEmail());
        UserDetails userDetails = user.toUserDetails();
        userDetailses.add(userDetails);
      }

      likeableObjectLikesEvent = new LikeableObjectLikesEvent(objId, userDetailses);

    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByLikeableObjId");
      likeableObjectLikesEvent = LikeableObjectLikesEvent.objectNotFound(objId);
    }
    return likeableObjectLikesEvent;
  }
}
