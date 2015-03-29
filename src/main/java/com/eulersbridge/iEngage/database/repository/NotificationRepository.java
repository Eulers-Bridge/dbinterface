/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.notifications.Notification;

/**
 * @author Greg Newitt
 *
 */
public interface NotificationRepository extends GraphRepository<Notification>
{

	Page<Notification> findByUser(Long userId, Pageable pageable);

}
