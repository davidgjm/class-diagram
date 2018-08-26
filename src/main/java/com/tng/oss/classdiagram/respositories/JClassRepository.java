package com.tng.oss.classdiagram.respositories;

import com.tng.oss.classdiagram.domain.JClass;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface JClassRepository extends GenericNeo4jRepository<JClass> {
    Optional<JClass> findJClassByClassNameAndPackageName(String className, String packageName);

    List<JClass> findAllByClassNameAndPackageName(String className, String packageName);

    boolean existsJClassByClassNameAndPackageName(String className, String packageName);
}
