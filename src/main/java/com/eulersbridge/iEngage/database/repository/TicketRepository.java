package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Support;
import com.eulersbridge.iEngage.database.domain.Ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface TicketRepository extends GraphRepository<Ticket>
{
	@Query("Match (n:`"+DatabaseDomainConstants.ELECTION+"`)-[r:"+DatabaseDomainConstants.HAS_TICKET_LABEL+
			"]-(e:`"+DatabaseDomainConstants.TICKET+"`) where id(n)={elecId} return e")
	Page<Ticket> findByElectionId(@Param("elecId")Long instId,Pageable p);

    @Query("Match (a:`User`),(b:`Ticket`) where a.email={email} and id(b)={ticketId} CREATE UNIQUE a-[r:SUPPORTS]-b SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Support' return r")
    Support supportTicket(@Param("ticketId")Long ticketId, @Param("email")String email);

    @Query("Match (a:`User`)-[r:SUPPORTS]-(b:`Ticket`) where a.email={email} and id(b)={ticketId} delete r")
    void withdrawSupportTicket(@Param("ticketId")Long ticketId, @Param("email")String email);
}
