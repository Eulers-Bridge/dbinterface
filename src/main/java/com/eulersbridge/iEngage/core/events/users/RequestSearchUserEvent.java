package com.eulersbridge.iEngage.core.events.users;

/**
 * @author Yikai Gong
 */

public class RequestSearchUserEvent {
    private String queryString;

    public RequestSearchUserEvent(String queryString)
    {
        this.queryString = queryString;
    }

    public String getqueryString()
    {
        return queryString;
    }
}
