package com.tng.oss.classdiagram.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NodeEntity
public class JClass {
    @Id
    @GeneratedValue
    Long id;

    private String name;

    private String definedInPackage;

    @Relationship(type = "INHERITS")
    private JClass parent;

    @Relationship(type = "IMPLEMENTS")
    private Set<JInterface> implementedInterfaces;

    @Relationship(type = "INHERITED_BY", direction = Relationship.INCOMING)
    private Set<JClass> subclasses;
}
