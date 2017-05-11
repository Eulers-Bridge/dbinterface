package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface PositionRepository extends GraphRepository<Position>
{
    static Logger LOG = LoggerFactory.getLogger(PositionRepository.class);

	@Query("Match (n:`"+ DataConstants.ELECTION+"`)-[r:"+ DataConstants.HAS_POSITION_LABEL+
			"]-(e:`"+ DataConstants.POSITION+"`) where id(n)={electionId} return e")
	Page<Position> findByElectionId(@Param("electionId")Long electionId, Pageable pageable);

}

