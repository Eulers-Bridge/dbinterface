package com.eulersbridge.iEngage.database.domain.resultMap;

import com.eulersbridge.iEngage.database.domain.Support;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

/**
 * @author Yikai Gong
 * This is a result map class used for TicketRepository.supportTicket_return_number_of_supports()
 * It contains the created 'support' relationship entity as well as the total support number
 * of that ticket after user's support behavior.
 */

@QueryResult
public class SupportAndNum {
    @ResultColumn("support")
    Support support;
    @ResultColumn("numOfSupport")
    Long numOfSupport;

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public Long getNumOfSupport() {
        return numOfSupport;
    }

    public void setNumOfSupport(Long numOfSupport) {
        this.numOfSupport = numOfSupport;
    }
}