package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Support;
import com.eulersbridge.iEngage.database.domain.Ticket;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.resultMap.SupportAndNum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Yikai Gong
 */

public interface TicketRepository extends GraphRepository<Ticket>
{
	@Query("Match (n:`"+DatabaseDomainConstants.ELECTION+"`)-[r:"+DatabaseDomainConstants.HAS_TICKET_LABEL+
			"]-(e:`"+DatabaseDomainConstants.TICKET+"`) where id(n)={elecId} return e")
	Page<Ticket> findByElectionId(@Param("elecId")Long instId,Pageable p);

    @Query("Match (a:`User`),(b:`Ticket`) where a.email={email} and id(b)={ticketId} CREATE UNIQUE (a)-[r:"+DatabaseDomainConstants.SUPPORT_LABEL+"]->(b) SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Support' return r")
    Support supportTicket(@Param("ticketId")Long ticketId, @Param("email")String email);

    @Query("Match (a:`User`),(b:`Ticket`) where a.email={email} and id(b)={ticketId} CREATE UNIQUE (a)-[r:"+DatabaseDomainConstants.SUPPORT_LABEL+"]->(b) SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Support'"+
            " with r as support Match (b:`Ticket`)-[s:"+DatabaseDomainConstants.SUPPORT_LABEL+"]-(a:`User`) where id(b)={ticketId} return support,  count(s) as numOfSupport")
    SupportAndNum supportTicket_return_number_of_supports(@Param("ticketId")Long ticketId, @Param("email")String email);

    @Query("Match (a:`User`)-[r:"+DatabaseDomainConstants.SUPPORT_LABEL+"]-(b:`Ticket`) where a.email={email} and id(b)={ticketId} delete r")
    void withdrawSupportTicket(@Param("ticketId")Long ticketId, @Param("email")String email);

    @Query("Match (a:`"+DatabaseDomainConstants.USER+"`)-[r:"+DatabaseDomainConstants.SUPPORT_LABEL+"]-(b:`"+DatabaseDomainConstants.TICKET+"`) where id(b)={ticketId} return a")
    Page<User> findSupporters(@Param("ticketId") Long ticketId, Pageable pageable);
}

