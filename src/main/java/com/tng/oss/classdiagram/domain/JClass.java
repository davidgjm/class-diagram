package com.tng.oss.classdiagram.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NodeEntity
public class JClass {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    Long id;

    @EqualsAndHashCode.Include
    @Index
    private String name;

    @EqualsAndHashCode.Include
    @Index
    private String definedInPackage;

    @Relationship(type = "INHERITS")
    private JClass parent;

    @Relationship(type = "IMPLEMENTS")
    private Set<JInterface> implementedInterfaces;

    @Relationship(type = "INHERITED_BY", direction = Relationship.INCOMING)
    private Set<JClass> subclasses;
}
