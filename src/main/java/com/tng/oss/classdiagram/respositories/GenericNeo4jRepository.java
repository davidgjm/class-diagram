package com.tng.oss.classdiagram.respositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericNeo4jRepository<T> extends Neo4jRepository<T, Long> {
}
