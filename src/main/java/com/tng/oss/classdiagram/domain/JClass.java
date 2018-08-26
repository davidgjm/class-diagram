package com.tng.oss.classdiagram.domain;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NodeEntity
public class JClass {
    @Id
    @GeneratedValue
    Long id;

    @Index
    private String className;

    @Index
    private String packageName;

    private boolean isInterface;

    @Relationship(type = "INHERITS")
    private JClass parent;

    @Relationship(type = "EXTENDS")
    private Set<JClass> extendedInterfaces = new HashSet<>();

    @Relationship(type = "INHERITED_BY")
    private Set<JClass> subclasses=new HashSet<>();

    @Relationship(type = "IMPLEMENTED_BY")
    private Set<JClass> implementations = new HashSet<>();

    public JClass(String packageName, String className) {
        this.className = className;
        this.packageName = packageName;
    }

    public JClass(Class<?> klass) {
        this.className = klass.getSimpleName();
        this.packageName = klass.getPackage().getName();
        this.isInterface = klass.isInterface();
    }

    public JClass() {
    }

    public String getFQDN() {
        return packageName + "." + className;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public JClass getParent() {
        return parent;
    }

    public void setParent(JClass parent) {
        this.parent = parent;
    }

    public Set<JClass> getExtendedInterfaces() {
        return extendedInterfaces;
    }

    public void setExtendedInterfaces(Set<JClass> extendedInterfaces) {
        this.extendedInterfaces = extendedInterfaces;
    }

    public Set<JClass> getSubclasses() {
        return subclasses;
    }

    public void setSubclasses(Set<JClass> subclasses) {
        this.subclasses = subclasses;
    }

    public Set<JClass> getImplementations() {
        return implementations;
    }

    public void setImplementations(Set<JClass> implementations) {
        this.implementations = implementations;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JClass jClass = (JClass) o;
        return Objects.equals(getClassName(), jClass.getClassName()) &&
                Objects.equals(getPackageName(), jClass.getPackageName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClassName(), getPackageName());
    }

    @Override
    public String toString() {
        return "JClass{" +
                "id=" + id +
                ", isInterface='" + isInterface() + '\'' +
                ", name='" + getFQDN() + '\'' +
                '}';
    }


    public static JClass from(Class<?> klass) {
        return new JClass(klass);
    }
}
