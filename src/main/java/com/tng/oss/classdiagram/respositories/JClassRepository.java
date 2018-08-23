package com.tng.oss.classdiagram.respositories;

import com.tng.oss.classdiagram.domain.JClass;

import java.util.Optional;

public interface JClassRepository extends GenericNeo4jRepository<JClass> {
    Optional<JClass> findByNameAndAndDefinedInPackage(String name, String definedInPackage);

    boolean existsJClassByNameAndDefinedInPackage(String name, String definedInPackage);
}
