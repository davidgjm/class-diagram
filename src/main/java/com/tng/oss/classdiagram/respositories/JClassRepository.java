package com.tng.oss.classdiagram.respositories;

import com.tng.oss.classdiagram.domain.JClass;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface JClassRepository extends GenericNeo4jRepository<JClass> {
    Optional<JClass> findJClassByClassNameAndPackageName(String className, String packageName);

    List<JClass> findAllByClassNameAndPackageName(String className, String packageName);

    boolean existsJClassByClassNameAndPackageName(String className, String packageName);

    @Query("MATCH (c:JClass) " +
            "WHERE c.className = {className} AND c.packageName = {packageName} " +
            "RETURN COUNT(c) > 0")
    boolean exists(@Param("className") String className, @Param("packageName") String packageName);
}
