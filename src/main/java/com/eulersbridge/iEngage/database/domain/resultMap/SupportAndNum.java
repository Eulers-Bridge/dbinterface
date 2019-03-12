package com.eulersbridge.iEngage.database.domain.resultMap;

import com.eulersbridge.iEngage.database.domain.Support;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author Yikai Gong
 * This is a result map class used for TicketRepository.supportTicket_return_number_of_supports()
 * It contains the created 'support' relationship entity as well as the total support number
 * of that ticket after user's support behavior.
 */

@QueryResult
public class SupportAndNum {
    @Property
    Long timestamp;
    @Property(name = "numOfSupport")
    Long numOfSupport;



    public Long getNumOfSupport() {
        return numOfSupport;
    }

    public void setNumOfSupport(Long numOfSupport) {
        this.numOfSupport = numOfSupport;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SupportAndNum{" +
          "timestamp=" + timestamp +
          ", numOfSupport=" + numOfSupport +
          '}';
    }
}