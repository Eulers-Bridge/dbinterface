package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Likeable;
import com.eulersbridge.iEngage.database.domain.User;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * @author Yikai Gong
 */

@RelationshipEntity(type= DatabaseDomainConstants.HAS_NOTIFICATION_LABEL)
public class HasNotification {
    @GraphId
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Notification notification;

    private Boolean read = false;

    public HasNotification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
