package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.Student;

public interface StudentRepository extends GraphRepository<Student> 
{
}