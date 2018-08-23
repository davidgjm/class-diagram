package com.tng.oss.classdiagram.respositories;

import com.tng.oss.classdiagram.domain.JInterface;

import java.util.Optional;

public interface JInterfaceRepository extends GenericNeo4jRepository<JInterface> {
    Optional<JInterface> findJInterfaceByNameAndDefinedInPackage(String name, String definedInPackage);

    boolean existsByNameAndDefinedInPackage(String name, String definedInPackage);

    Iterable<JInterface> findByDefinedInPackage(String definedInPackage);
}
