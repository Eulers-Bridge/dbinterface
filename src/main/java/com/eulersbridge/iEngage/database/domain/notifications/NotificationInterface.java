/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.HashMap;

/**
 * @author Greg Newitt
 *
 */
public interface NotificationInterface
{
    public Boolean setupForSave(HashMap<String,GraphRepository<?>> repos);
}
