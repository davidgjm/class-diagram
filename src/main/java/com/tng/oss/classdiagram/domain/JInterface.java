package com.tng.oss.classdiagram.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NodeEntity
public class JInterface {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    Long id;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String definedInPackage;

    @Relationship(type = "EXTENDS")
    private JInterface parent;

    @Relationship(type = "IMPLEMENTED_BY", direction = Relationship.INCOMING)
    private Set<JClass> implementations;
}
