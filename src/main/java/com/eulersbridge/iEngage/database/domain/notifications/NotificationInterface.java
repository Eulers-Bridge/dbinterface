/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import java.util.HashMap;

import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Greg Newitt
 *
 */
public interface NotificationInterface
{
    public Boolean setupForSave(HashMap<String,GraphRepository<?>> repos);
}
