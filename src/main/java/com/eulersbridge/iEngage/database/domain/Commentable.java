package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.GraphId;

/**
 * @author Yikai Gong
 */

public interface Commentable {
    public Long getNodeId();
    public void setNodeId(Long nodeId);
}
