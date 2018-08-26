package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Service;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class JavaReflectionServiceImpl implements JavaReflectionService {
    private Reflections reflections;
    private final JClassService classService;

    public JavaReflectionServiceImpl(JClassService classService) {
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

        Set<JClass> entites = new HashSet<>();
        allClasses.stream()
                .filter(c -> !c.isAnnotation() && !c.isMemberClass() && !c.isAnonymousClass())
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .forEach(c -> checkSingleClass(entites, c));

        entites.forEach(this::saveClass);

    }

    private Set<Class<?>> getAllClasses() {
        return reflections.getSubTypesOf(Object.class);
    }

    private JClass getJavaClass(final Set<JClass> entities, final Class<?> javaClass) {
        JClass entity = JClass.from(javaClass);
        Optional<JClass> jClassOptional = entities.stream().filter(e -> e.equals(JClass.from(javaClass)))
                .findAny();
        if (jClassOptional.isPresent()) {
            entity = jClassOptional.get();
        } else {
            entities.add(entity);
        }
        return entity;
    }

    private void checkSingleClass(final Set<JClass> entities, final Class<?> javaClass) {
        log.info("Checking Type: {}, Name: {}", javaClass.isInterface() ? "Interface" : "Class", javaClass.getName());
        if(javaClass.getSimpleName().isEmpty()) return;
        JClass entity = getJavaClass(entities, javaClass);

        AtomicReference<Class<?>> sup = new AtomicReference<>();
        AtomicReference<Class<?>> sub = new AtomicReference<>();
        AtomicReference<JClass> entityReference = new AtomicReference<>(entity);

        ReflectionUtils.getSuperTypes(javaClass).forEach(s -> {
            log.debug("SUPER - {}: {}", s.isInterface() ? "Interface" : "Class", s.getName());
            JClass superType = getJavaClass(entities, s);

            if (s.isInterface()) {
                entityReference.get().getExtendedInterfaces().add(superType);
            } else {
                entityReference.get().setParent(superType);
            }
            sup.set(s);
            if (sub.get() != null && !sub.get().equals(s)) {
                checkSingleClass(entities, s);
            }
        });

/*        reflections.getSubTypesOf(javaClass).forEach(s -> {
            log.debug("SUB - {}: {}", s.isInterface() ? "Interface" : "Class", s.getName());
            JClass subtype = getJavaClass(entities, s);
            Type superType = s.getGenericSuperclass();
            if (superType instanceof Class) {
                Class type = (Class) superType;
                if (type.equals(javaClass)) {
                    if (!entityReference.get().isInterface()) {
//                        entityReference.get().getSubclasses().add(subtype);
                    } else {
                        if (s.isInterface()) {
//                            entityReference.get().getSubclasses().add(subtype);
                        } else {
                            entityReference.get().getImplementations().add(subtype);
                        }
                    }
                } else {
                    log.warn("Indirect implementation: {}", s);
                }
            }


            sub.set(s);
            if (sup.get() != null && !sup.get().equals(s))
                checkSingleClass(entities, s);
        });*/
    }

    private void saveClass(JClass entity) {
        log.info("Saving entity: {}", entity.getFQDN());
        classService.saveOrUpdate(entity);

    }
}
