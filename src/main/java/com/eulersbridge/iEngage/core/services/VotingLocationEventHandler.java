/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.votingLocation.*;
import com.eulersbridge.iEngage.core.services.interfacePack.VotingLocationService;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.VotingLocation;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.NodeRepository;
import com.eulersbridge.iEngage.database.repository.VotingLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Greg Newitt
 */

@Service
public class VotingLocationEventHandler implements VotingLocationService {
  private static Logger LOG = LoggerFactory
    .getLogger(VotingLocationEventHandler.class);

  private VotingLocationRepository votingLocationRepository;
  private NodeRepository nodeRepository;
  private ElectionRepository electionRepository;

  @Autowired
  public VotingLocationEventHandler(VotingLocationRepository locationRepository,
                                    ElectionRepository electionRepository,
                                    NodeRepository nodeRepository) {
    this.votingLocationRepository = locationRepository;
    this.nodeRepository = nodeRepository;
    this.electionRepository = electionRepository;
  }

  @Override
  public ReadEvent readVotingLocation(
    ReadVotingLocationEvent readVotingLocationEvent) {
    VotingLocation votingLocation = votingLocationRepository
      .findOne(readVotingLocationEvent.getNodeId());
    ReadEvent votingLocationReadEvent;
    if (votingLocation != null) {
      votingLocationReadEvent = new VotingLocationReadEvent(
        votingLocation.getNodeId(),
        votingLocation.toVotingLocationDetails());
    } else {
      votingLocationReadEvent = VotingLocationReadEvent
        .notFound(readVotingLocationEvent.getNodeId());
    }
    return votingLocationReadEvent;
  }

  @Override
  public CreatedEvent createVotingLocation(
    CreateVotingLocationEvent createVotingLocationEvent) {
    VotingLocationDetails votingLocationDetails = (VotingLocationDetails) createVotingLocationEvent
      .getDetails();
    VotingLocation votingLocation = VotingLocation
      .fromVotingLocationDetails(votingLocationDetails);

    Long ownerId = votingLocationDetails.getOwnerId();
    if (LOG.isDebugEnabled())
      LOG.debug("Finding owner with nodeId = " + ownerId);
    CreatedEvent votingLocationCreatedEvent;
    if (ownerId != null) {
      Node owner = nodeRepository.findOne(ownerId);

      if (owner != null) {
        votingLocation.setOwner(new Node(owner.getNodeId()));
        VotingLocation result = votingLocationRepository.save(votingLocation);
        if ((null == result) || (null == result.getNodeId()))
          votingLocationCreatedEvent = CreatedEvent.failed(votingLocationDetails);
        else
          votingLocationCreatedEvent = new VotingLocationCreatedEvent(result.toVotingLocationDetails());
      } else {
        votingLocationCreatedEvent = VotingLocationCreatedEvent
          .ownerNotFound(ownerId);
      }
    } else {
      votingLocationCreatedEvent = VotingLocationCreatedEvent
        .ownerNotFound(ownerId);
    }
    return votingLocationCreatedEvent;
  }

  @Override
  public DeletedEvent deleteVotingLocation(
    DeleteVotingLocationEvent deleteVotingLocationEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteVotingLocationEvent= " + deleteVotingLocationEvent);
    Long positionId = deleteVotingLocationEvent.getNodeId();
    if (LOG.isDebugEnabled())
      LOG.debug("deleteVotingLocation(" + positionId + ")");
    VotingLocation position = votingLocationRepository.findOne(positionId);
    if (position == null) {
      return VotingLocationDeletedEvent.notFound(positionId);
    } else {
      votingLocationRepository.delete(positionId);
      VotingLocationDeletedEvent positionDeletedEvent = new VotingLocationDeletedEvent(
        positionId);
      return positionDeletedEvent;
    }
  }

  @Override
  public UpdatedEvent updateVotingLocation(
    UpdateVotingLocationEvent updateVotingLocationEvent) {
    VotingLocationDetails votingLocationDetails = (VotingLocationDetails) updateVotingLocationEvent
      .getDetails();
    VotingLocation votingLocation = VotingLocation
      .fromVotingLocationDetails(votingLocationDetails);
    Long votingLocationId = votingLocationDetails.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("positionId is " + votingLocationId);
    VotingLocation votingLocationOld = votingLocationRepository.findOne(votingLocationId);
    if (votingLocationOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("votingLocation entity not found " + votingLocationId);
      return VotingLocationUpdatedEvent.notFound(votingLocationId);
    } else if (votingLocationDetails.getOwnerId() == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("votingLocation entity not found " + votingLocationId);
      if (votingLocationOld.getOwner() != null)
        votingLocation.setOwner(votingLocationOld.getOwner());
      else
        return VotingLocationUpdatedEvent.notFound(votingLocationId);
    }
    VotingLocation result = votingLocationRepository.save(votingLocation, 0);
    if (LOG.isDebugEnabled())
      LOG.debug("updated successfully" + result.getNodeId());
    return new VotingLocationUpdatedEvent(result.getNodeId(),
      result.toVotingLocationDetails());
  }

  @Override
  public AllReadEvent findVotingLocations(
    ReadAllEvent readVotingLocationsEvent,
    Direction sortDirection, int pageNumber, int pageLength) {
    Long ownerId = readVotingLocationsEvent.getParentId();
    Page<VotingLocation> votingLocations = null;
    ArrayList<VotingLocationDetails> dets = new ArrayList<VotingLocationDetails>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("InstitutionId " + ownerId);
    Pageable pageable = new PageRequest(pageNumber, pageLength,
      sortDirection, "p.name");
    votingLocations = votingLocationRepository.findByInstitutionId(ownerId, pageable);
    if (votingLocations != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + votingLocations.getTotalElements()
          + " total pages =" + votingLocations.getTotalPages());
      Iterator<VotingLocation> iter = votingLocations.iterator();
      while (iter.hasNext()) {
        VotingLocation na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getName());
        VotingLocationDetails det = na.toVotingLocationDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        Node elec = nodeRepository.findOne(ownerId, 0);
        if (null == elec) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ownerId)");
          nare = AllReadEvent.notFound(ownerId);
        } else {
          nare = new AllReadEvent(ownerId, dets,
            votingLocations.getTotalElements(),
            votingLocations.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(ownerId, dets,
          votingLocations.getTotalElements(), votingLocations.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(ownerId);
    }
    return nare;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.eulersbridge.iEngage.core.services.interfacePack.VotingLocationService#
   * findVotingBooths
   * (com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationsEvent
   * , org.springframework.data.domain.Sort.Direction, int, int)
   */
  @Override
  public AllReadEvent findVotingBooths(
    ReadAllEvent readVotingLocationsEvent,
    Direction sortDirection, int pageNumber, int pageLength) {
    Long ownerId = readVotingLocationsEvent.getParentId();
    Page<VotingLocation> votingLocations = null;
    ArrayList<VotingLocationDetails> dets = new ArrayList<VotingLocationDetails>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("ElectionId " + ownerId);
    Pageable pageable = new PageRequest(pageNumber, pageLength,
      sortDirection, "p.name");
    votingLocations = votingLocationRepository.findByElectionId(ownerId, pageable);
    if (votingLocations != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + votingLocations.getTotalElements()
          + " total pages =" + votingLocations.getTotalPages());
      Iterator<VotingLocation> iter = votingLocations.iterator();
      while (iter.hasNext()) {
        VotingLocation na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getName());
        VotingLocationDetails det = na.toVotingLocationDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        Node elec = nodeRepository.findOne(ownerId, 0);
        if (null == elec) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ownerId)");
          nare = AllReadEvent.notFound(ownerId);
        } else {
          nare = new AllReadEvent(ownerId, dets,
            votingLocations.getTotalElements(),
            votingLocations.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(ownerId, dets,
          votingLocations.getTotalElements(), votingLocations.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByElectionId");
      nare = AllReadEvent.notFound(ownerId);
    }
    return nare;
  }


  @Override
  public UpdatedEvent addVotingLocationToElection(
    AddVotingLocationEvent addVotingLocationEvent) {
    UpdatedEvent evt;

    if (addVotingLocationEvent != null) {
      Long votingLocationId = addVotingLocationEvent.getVotingLocationId();
      Long electionId = addVotingLocationEvent.getElectionId();

      VotingLocation votingLocation = votingLocationRepository.findOne(votingLocationId);
      if (votingLocation != null) { // Valid VotingLocation
        if (LOG.isDebugEnabled())
          LOG.debug("Found votingLocation - " + votingLocation);
        Election election = electionRepository.findOne(electionId);
        if (election != null) {
          if (LOG.isDebugEnabled())
            LOG.debug("votingLocationRepository.addElection(" + votingLocationId + ", " + electionId + ')');
          VotingLocation relNodeId = votingLocationRepository.addElection(votingLocationId, electionId);
          if (relNodeId != null) {
            evt = new VotingLocationAddedEvent(votingLocationId, relNodeId.toVotingLocationDetails());
          } else {
            if (LOG.isDebugEnabled()) LOG.debug("Failed");
            evt = VotingLocationAddedEvent.electionNotFound();
          }

        } else {
          evt = VotingLocationAddedEvent.electionNotFound();
        }
      } else {
        if (LOG.isDebugEnabled()) LOG.debug("No such account.");
        evt = VotingLocationAddedEvent.votingLocationNotFound();
      }
    } else {
      evt = VotingLocationAddedEvent.notFound(null);
    }
    return evt;
  }

  @Override
  public DeletedEvent removeVotingLocationFromElection(
    RemoveVotingLocationEvent removeVotingLocationEvent) {
    DeletedEvent response;
    if (removeVotingLocationEvent != null) {
      Long votingLocationId = removeVotingLocationEvent.getVotingLocationId();
      Long electionId = removeVotingLocationEvent.getElectionId();

      if (LOG.isDebugEnabled())
        LOG.debug("removeVotingLocation(" + removeVotingLocationEvent.getVotingLocationId()
          + ',' + removeVotingLocationEvent.getElectionId() + ")");


      VotingLocation votingLocation = votingLocationRepository.findOne(votingLocationId);
      if (votingLocation != null) { // Valid VotingLocation
        Election election = electionRepository.findOne(electionId);
        if (election != null) {
          VotingLocation vl = votingLocationRepository.deleteElection(votingLocationId, electionId);
          if (vl != null) {
            response = new VotingLocationRemovedEvent(electionId);
          } else
            response = DeletedEvent.deletionForbidden(votingLocationId);

        } else {
          response = VotingLocationRemovedEvent.electionNotFound();
        }
      } else {
        if (LOG.isDebugEnabled()) LOG.debug("No such account.");
        response = VotingLocationRemovedEvent.votingLocationNotFound();
      }
    } else {
      response = DeletedEvent.notFound(null);
    }
    return response;
  }
}
