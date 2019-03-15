package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Support;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.resultMap.SupportAndNum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface TicketRepository extends Neo4jRepository<Ticket, Long> {
  @Query(value = "Match (n:`" + DataConstants.ELECTION + "`)-[r:" + DataConstants.HAS_TICKET_LABEL +
    "]-(e:`" + DataConstants.TICKET + "`) where id(n)={elecId} with e return (e)-[*0..1]-()",
    countQuery = "Match (n:`" + DataConstants.ELECTION + "`)-[r:" + DataConstants.HAS_TICKET_LABEL +
      "]-(e:`" + DataConstants.TICKET + "`) where id(n)={elecId} return count(e)")
  Page<Ticket> findByElectionId(@Param("elecId") Long instId, Pageable p);

  @Query("Match (a:`User`),(b:`Ticket`) where a.email={email} and id(b)={ticketId} CREATE UNIQUE a-[r:" + DataConstants.SUPPORT_LABEL + "]->b SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Support' return r")
  Support supportTicket(@Param("ticketId") Long ticketId, @Param("email") String email);

  @Query("Match (a:`" + DataConstants.USER + "`),(b:`" + DataConstants.TICKET
    + "`) where a.email={email} and id(b)={ticketId} CREATE UNIQUE l=(a)-[r:"
    + DataConstants.SUPPORT_LABEL + "]->(b) SET r.timestamp=coalesce(r.timestamp,timestamp())" +
    " with r, b Match (b)-[s:" + DataConstants.SUPPORT_LABEL + "]-(a:`" + DataConstants.USER
    + "`) return r.timestamp as timestamp, count(s) as numOfSupport")
  SupportAndNum supportTicket_return_number_of_supports(@Param("ticketId") Long ticketId, @Param("email") String email);

  @Query("Match (a:`User`)-[r:" + DataConstants.SUPPORT_LABEL + "]-(b:`Ticket`) where a.email={email} and id(b)={ticketId} delete r")
  void withdrawSupportTicket(@Param("ticketId") Long ticketId, @Param("email") String email);

  @Query(value = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.SUPPORT_LABEL + "]-(b:`" + DataConstants.TICKET + "`) where id(b)={ticketId} return a",
    countQuery = "Match (a:`" + DataConstants.USER + "`)-[r:" + DataConstants.SUPPORT_LABEL + "]-(b:`" + DataConstants.TICKET + "`) where id(b)={ticketId} return count(a)")
  Page<User> findSupporters(@Param("ticketId") Long ticketId, Pageable pageable);
}

