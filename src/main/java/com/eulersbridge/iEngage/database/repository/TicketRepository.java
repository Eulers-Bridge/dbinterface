package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Ticket;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface TicketRepository extends GraphRepository<Ticket>{
}
