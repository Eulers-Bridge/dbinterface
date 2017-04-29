package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Node {
  @GraphId
  protected Long nodeId;

  public Node() {
  }

  public Node(Long nodeId) {
    this.nodeId = nodeId;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public Node toNode() {
    return new Node(nodeId);
  }

  public static <T extends Node> List<T> castList(List<Node> nodeList, Class<T> tClass)
    throws ClassCastException {
    if (nodeList == null)
      return null;
    return nodeList.stream().map(node -> (T) node).collect(Collectors.toList());
  }

  public static <T extends Node> List<Node> castList(List<T> tList)
    throws ClassCastException {
    if (tList == null)
      return null;
    return tList.stream().map(t -> (Node) t).collect(Collectors.toList());
  }

  public static <T extends Node> List<Node> toNodeList(List<T> list) {
    if (list == null) {
      return null;
    }
    return list.stream()
      .map(node -> new Node(node.getNodeId()))
      .collect(Collectors.toList());
  }
}
