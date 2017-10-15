package com.eulersbridge.iEngage.core.beans;

import com.eulersbridge.iEngage.database.domain.Node;
import org.neo4j.ogm.session.event.Event;
import org.neo4j.ogm.session.event.EventListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Yikai Gong
 */

@Component
public class PreSaveEventListener extends EventListenerAdapter {
  public PreSaveEventListener() {
  }

  @Override
  public void onPreSave(Event event) {
    event.getLifeCycle();
    if (event.getObject().getClass().equals(Node.class))
      return;
    if (! (event.getObject() instanceof Node))
      return;
    Node node = (Node) event.getObject();
//    System.out.println("Fired event " + node.getNodeId());

    List<Long> timestampList = node.getTimestampList() == null
      ? new ArrayList<>()
      : new ArrayList<>(); //node.getTimestampList();
    Long time = new Date().getTime();
    if (timestampList.isEmpty() || !timestampList.get(timestampList.size() - 1).equals(time)) {
      timestampList.add(time);
      node.setTimestampList(timestampList);
    }
  }
}
