package com.tng.oss.classdiagram.sandbox;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ReflectionsSandbox {
    private Reflections reflections;
    String packageName = "org.springframework";

    @Before
    public void setUp() throws Exception {
        reflections = new Reflections(packageName, new SubTypesScanner(false));
    }

    @Test
    public void testGetAll() {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));

        Set<String> allClasses = reflections.getAllTypes();
        allClasses.forEach(System.out::println);
    }

    @Test
    public void testGetClasses() {
        Set<Class<?>> classTypes = reflections.getSubTypesOf(Object.class);
        classTypes.stream()
                .filter(c -> !c.isAnnotation() && !c.isMemberClass() && !c.isAnonymousClass())
                .forEach(this::checkSingleClass);

    }


    private void checkSingleClass(Class<?> javaClass) {
        System.out.println(javaClass);
        AtomicReference<Class<?>> sup = new AtomicReference<>();
        AtomicReference<Class<?>> sub = new AtomicReference<>();

        ReflectionUtils.getSuperTypes(javaClass).forEach(s -> {
            System.out.println("\t--> " + s.getName());
            sup.set(s);
            if (sub.get() != null && !sub.get().equals(s))
                checkSingleClass(s);
        });

        reflections.getSubTypesOf(javaClass).forEach(s -> {
            sub.set(s);
            System.out.println("\t<-- " + s.getName());
            if (sup.get() != null && !sup.get().equals(s))
                checkSingleClass(s);
        });
    }


}
