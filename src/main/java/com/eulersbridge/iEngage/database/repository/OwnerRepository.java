package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Owner;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface OwnerRepository extends GraphRepository<Owner>
{

}
