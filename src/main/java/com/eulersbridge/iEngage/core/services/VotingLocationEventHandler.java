/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.AddVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.DeleteVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.RemoveVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.UpdateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationAddedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationCreatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDeletedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationReadEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationRemovedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationUpdatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationsReadEvent;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.VotingLocation;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.VotingLocationRepository;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationEventHandler implements VotingLocationService
{

	private static Logger LOG = LoggerFactory
			.getLogger(VotingLocationEventHandler.class);

	private VotingLocationRepository votingLocationRepository;
	private OwnerRepository ownerRepository;
	private ElectionRepository electionRepository;

	public VotingLocationEventHandler(
			VotingLocationRepository locationRepository,ElectionRepository electionRepository,
			OwnerRepository ownerRepository)
	{
		this.votingLocationRepository = locationRepository;
		this.ownerRepository = ownerRepository;
		this.electionRepository=electionRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eulersbridge.iEngage.core.services.VotingLocationService#
	 * readVotingLocation
	 * (com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent
	 * )
	 */
	@Override
	public ReadEvent readVotingLocation(
			ReadVotingLocationEvent readVotingLocationEvent)
	{
		VotingLocation votingLocation = votingLocationRepository
				.findOne(readVotingLocationEvent.getNodeId());
		ReadEvent votingLocationReadEvent;
		if (votingLocation != null)
		{
			votingLocationReadEvent = new VotingLocationReadEvent(
					votingLocation.getNodeId(),
					votingLocation.toVotingLocationDetails());
		}
		else
		{
			votingLocationReadEvent = VotingLocationReadEvent
					.notFound(readVotingLocationEvent.getNodeId());
		}
		return votingLocationReadEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eulersbridge.iEngage.core.services.VotingLocationService#
	 * createVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.
	 * CreateVotingLocationEvent)
	 */
	@Override
	public CreatedEvent createVotingLocation(
			CreateVotingLocationEvent createVotingLocationEvent)
	{
		VotingLocationDetails votingLocationDetails = (VotingLocationDetails) createVotingLocationEvent
				.getDetails();
		VotingLocation votingLocation = VotingLocation
				.fromVotingLocationDetails(votingLocationDetails);

		Long ownerId = votingLocationDetails.getOwnerId();
		if (LOG.isDebugEnabled())
			LOG.debug("Finding owner with nodeId = " + ownerId);
		Owner owner = ownerRepository.findOne(ownerId);

		CreatedEvent votingLocationCreatedEvent;
		if (owner != null)
		{
			votingLocation.setOwner(owner);
			VotingLocation result = votingLocationRepository.save(votingLocation);
	        if ((null==result)||(null==result.getNodeId()))
	        	votingLocationCreatedEvent = CreatedEvent.failed(votingLocationDetails);
	        else
	        	votingLocationCreatedEvent = new VotingLocationCreatedEvent(result.toVotingLocationDetails());
		}
		else
		{
			votingLocationCreatedEvent = VotingLocationCreatedEvent
					.ownerNotFound(votingLocationDetails.getOwnerId());
		}
		return votingLocationCreatedEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eulersbridge.iEngage.core.services.VotingLocationService#
	 * deleteVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.
	 * DeleteVotingLocationEvent)
	 */
	@Override
	public DeletedEvent deleteVotingLocation(
			DeleteVotingLocationEvent deleteVotingLocationEvent)
	{
		if (LOG.isDebugEnabled())
			LOG.debug("Entered deleteVotingLocationEvent= " + deleteVotingLocationEvent);
		Long positionId = deleteVotingLocationEvent.getNodeId();
		if (LOG.isDebugEnabled())
			LOG.debug("deleteVotingLocation(" + positionId + ")");
		VotingLocation position = votingLocationRepository.findOne(positionId);
		if (position == null)
		{
			return VotingLocationDeletedEvent.notFound(positionId);
		}
		else
		{
			votingLocationRepository.delete(positionId);
			VotingLocationDeletedEvent positionDeletedEvent = new VotingLocationDeletedEvent(
					positionId);
			return positionDeletedEvent;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eulersbridge.iEngage.core.services.VotingLocationService#
	 * updateVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.
	 * UpdateVotingLocationEvent)
	 */
	@Override
	public UpdatedEvent updateVotingLocation(
			UpdateVotingLocationEvent updateVotingLocationEvent)
	{
		VotingLocationDetails votingLocationDetails = (VotingLocationDetails) updateVotingLocationEvent
				.getDetails();
		VotingLocation votingLocation = VotingLocation
				.fromVotingLocationDetails(votingLocationDetails);
		Long votingLocationId = votingLocationDetails.getNodeId();
		if (LOG.isDebugEnabled()) LOG.debug("positionId is " + votingLocationId);
		VotingLocation votingLocationOld = votingLocationRepository.findOne(votingLocationId);
		if (votingLocationOld == null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("votingLocation entity not found " + votingLocationId);
			return VotingLocationUpdatedEvent.notFound(votingLocationId);
		}
		else if (votingLocationDetails.getOwnerId()==null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("votingLocation entity not found " + votingLocationId);
			if (votingLocationOld.getOwner()!=null)
				votingLocation.setOwner(votingLocationOld.getOwner());
			else
				return VotingLocationUpdatedEvent.notFound(votingLocationId);
		}
		VotingLocation result = votingLocationRepository.save(votingLocation);
		if (LOG.isDebugEnabled())
			LOG.debug("updated successfully" + result.getNodeId());
		return new VotingLocationUpdatedEvent(result.getNodeId(),
					result.toVotingLocationDetails());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eulersbridge.iEngage.core.services.VotingLocationService#
	 * findVotingLocations
	 * (com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationsEvent
	 * , org.springframework.data.domain.Sort.Direction, int, int)
	 */
	@Override
	public VotingLocationsReadEvent findVotingLocations(
			ReadAllEvent readVotingLocationsEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		Long ownerId = readVotingLocationsEvent.getParentId();
		Page<VotingLocation> votingLocations = null;
		ArrayList<VotingLocationDetails> dets = new ArrayList<VotingLocationDetails>();
		VotingLocationsReadEvent nare = null;

		if (LOG.isDebugEnabled()) LOG.debug("ElectionId " + ownerId);
		Pageable pageable = new PageRequest(pageNumber, pageLength,
				sortDirection, "p.name");
		votingLocations = votingLocationRepository.findByOwnerId(ownerId, pageable);
		if (votingLocations != null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = " + votingLocations.getTotalElements()
						+ " total pages =" + votingLocations.getTotalPages());
			Iterator<VotingLocation> iter = votingLocations.iterator();
			while (iter.hasNext())
			{
				VotingLocation na = iter.next();
				if (LOG.isTraceEnabled())
					LOG.trace("Converting to details - " + na.getName());
				VotingLocationDetails det = na.toVotingLocationDetails();
				dets.add(det);
			}
			if (0 == dets.size())
			{
				// Need to check if we actually found parentId.
				Owner elec = ownerRepository.findOne(ownerId);
				if (null == elec)
				{
					if (LOG.isDebugEnabled())
						LOG.debug("Null or null properties returned by findOne(ownerId)");
					nare = VotingLocationsReadEvent.notFound(ownerId);
				}
				else
				{
					nare = new VotingLocationsReadEvent(dets,
							votingLocations.getTotalElements(),
							votingLocations.getTotalPages());
				}
			}
			else
			{
				nare = new VotingLocationsReadEvent(dets,
						votingLocations.getTotalElements(), votingLocations.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Null returned by findByInstitutionId");
			nare = VotingLocationsReadEvent.notFound(ownerId);
		}
		return nare;
	}


	@Override
	public CreatedEvent addVotingLocationToElection(
			AddVotingLocationEvent addVotingLocationEvent)
	{
		CreatedEvent evt;

		if (addVotingLocationEvent!=null)
		{	
			Long votingLocationId = addVotingLocationEvent.getVotingLocationId();
			Long electionId = addVotingLocationEvent.getElectionId();
			
			if (LOG.isDebugEnabled()) LOG.debug("votingLocationId - " + votingLocationId+", electionId - "+electionId);
	
			VotingLocation votingLocation = votingLocationRepository.findOne(votingLocationId);
			if (votingLocation != null)
			{ // Valid VotingLocation
				Election election = electionRepository.findOne(electionId);
				if (election!=null)
				{
					Long relNodeId = votingLocationRepository.addElection(votingLocationId, electionId);
					if (relNodeId != null)
					{
						evt = new VotingLocationAddedEvent();
					}
					else
						evt = VotingLocationAddedEvent.failed(null);

				}
				else
				{
					evt = VotingLocationAddedEvent.electionNotFound();
				}
			}
			else
			{
				if (LOG.isDebugEnabled()) LOG.debug("No such account.");
				evt = VotingLocationAddedEvent.votingLocationNotFound();
			}
		}
		else
		{
			evt = VotingLocationAddedEvent.failed(null);
		}
		return evt;
	}

	@Override
	public DeletedEvent removeVotingLocationFromElection(
			RemoveVotingLocationEvent removeVotingLocationEvent)
	{
		DeletedEvent response;
		if (removeVotingLocationEvent!=null)
		{	
			Long votingLocationId = removeVotingLocationEvent.getVotingLocationId();
			Long electionId = removeVotingLocationEvent.getElectionId();
			
			if (LOG.isDebugEnabled())
				LOG.debug("removeVotingLocation(" + removeVotingLocationEvent.getVotingLocationId()
						+ ','+removeVotingLocationEvent.getElectionId()+")");
			
			
			VotingLocation votingLocation = votingLocationRepository.findOne(votingLocationId);
			if (votingLocation != null)
			{ // Valid VotingLocation
				Election election = electionRepository.findOne(electionId);
				if (election!=null)
				{
					VotingLocation vl = votingLocationRepository.deleteElection(votingLocationId, electionId);
					if (vl != null)
					{
						response = new VotingLocationRemovedEvent(electionId);
					}
					else
						response = DeletedEvent.deletionForbidden(votingLocationId);

				}
				else
				{
					response = VotingLocationRemovedEvent.electionNotFound();
				}
			}
			else
			{
				if (LOG.isDebugEnabled()) LOG.debug("No such account.");
				response = VotingLocationRemovedEvent.votingLocationNotFound();
			}
		}
		else
		{
			response = DeletedEvent.notFound(null);
		}
		return response;
	}
}
