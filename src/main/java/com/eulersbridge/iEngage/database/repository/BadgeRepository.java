package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Badge;
import com.eulersbridge.iEngage.database.domain.BadgeComplete;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface BadgeRepository extends GraphRepository<Badge>
{	
	@Query("Match (a:`"+ DataConstants.USER+"`),(b:`"+ DataConstants.BADGE+"`) where id(a)={userId} and id(b)={badgeId} CREATE UNIQUE (a)-[r:"+ DataConstants.HAS_COMPLETED_BADGE_LABEL+
			"]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='BadgeComplete' return r")
	BadgeComplete badgeCompleted(@Param("badgeId") Long badgeId, @Param("userId") Long userId);

    @Query("Match (a:`"+ DataConstants.USER+"`),(b:`"+ DataConstants.BADGE+"`) where a.email={userEmail} and id(b)={badgeId} CREATE UNIQUE (a)-[r:"+ DataConstants.HAS_COMPLETED_BADGE_LABEL+
            "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='BadgeComplete' return r")
    BadgeComplete badgeCompleted(@Param("badgeId") Long badgeId, @Param("userEmail") String userEmail);

	@Query("Match (a:`"+ DataConstants.USER+"`)-[r:`"+ DataConstants.HAS_COMPLETED_BADGE_LABEL+"`]-(b:`"+ DataConstants.BADGE+"`) where id(a)={userId} return b")
	Page<Badge> findCompletedBadges(@Param("userId") Long userId, Pageable pageable);

    @Query("Match (a:`"+ DataConstants.USER+"`)-[r:`"+ DataConstants.HAS_COMPLETED_BADGE_LABEL+"`]-(b:`"+ DataConstants.BADGE+"`) where a.email={userEmail} and id(b)={badgeId} return b")
    Badge checkBadgeCompleted(@Param("badgeId") Long badgeId, @Param("userEmail") String userEmail);

	@Query("Match (u:`"+ DataConstants.USER+"`),(t:`"+ DataConstants.BADGE+"`) where id(u)={userId} and"+
			" not (u)-[:"+ DataConstants.HAS_COMPLETED_BADGE_LABEL+"]-(t) return t")
	Page<Badge> findRemainingBadges(@Param("userId") Long userId, Pageable pageable);

}
