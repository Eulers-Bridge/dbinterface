package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */

public class LikesEventHandler implements LikesService {

    UserRepository userRepository;

    private static Logger LOG = LoggerFactory.getLogger(LikesEventHandler.class);

    public LikesEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LikeableObjectLikesEvent likes(LikesLikeableObjectEvent likesLikeableObjectEvent, Sort.Direction sortDirection, int pageNumber, int pageSize) {

        Long objId = likesLikeableObjectEvent.getLikeableObjId();
        ArrayList<UserDetails> userDetailses = new ArrayList<UserDetails>();
        LikeableObjectLikesEvent likeableObjectLikesEvent;

        if (LOG.isDebugEnabled()) LOG.debug("objId "+objId);
        Pageable pageable = new PageRequest(pageNumber,pageSize,sortDirection,"a.date");
        Page<User> users = userRepository.findByLikeableObjId(objId, pageable);
        if (LOG.isDebugEnabled())
            LOG.debug("Total elements = "+users.getTotalElements()+" total pages ="+users.getTotalPages());

        if (users != null)
        {
            Iterator<User> iter = users.iterator();
            while (iter.hasNext())
            {
                User user =iter.next();
                if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+user.getEmail());
                UserDetails userDetails = user.toUserDetails();
                userDetailses.add(userDetails);
            }

            likeableObjectLikesEvent = new LikeableObjectLikesEvent(objId, userDetailses);

        }
        else
        {
            if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByLikeableObjId");
            likeableObjectLikesEvent = LikeableObjectLikesEvent.objectNotFound(objId);
        }
        return likeableObjectLikesEvent;
    }
}
