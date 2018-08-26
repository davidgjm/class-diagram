package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;
import com.tng.oss.classdiagram.domain.JInterface;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class JavaReflectionServiceImpl implements JavaReflectionService {
    private Reflections reflections;
    private final JInterfaceService interfaceService;
    private final JClassService classService;

    public JavaReflectionServiceImpl(JInterfaceService interfaceService, JClassService classService) {
        this.interfaceService = interfaceService;
        this.classService = classService;
    }

    private void setupReflections(String basePackage) {
        log.info("Initializing reflections with specified package: {}", basePackage);
        reflections = new Reflections(basePackage, new SubTypesScanner(false));
    }

    @Override
    public void scanJavaTypes(String basePackage) {
        log.info("Scanning classes/interfaces in {}", basePackage);
        setupReflections(basePackage);

        Set<Class<?>> allClasses = getAllClasses();
        allClasses.forEach(this::checkSingleClass);

    }

    private Set<Class<?>> getAllClasses() {
        return reflections.getSubTypesOf(Object.class);
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

    private void saveClass(Class<?> javaClass) {

    }
}
