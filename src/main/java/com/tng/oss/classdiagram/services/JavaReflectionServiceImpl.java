package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;
import com.tng.oss.classdiagram.domain.JInterface;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Slf4j
@Service
public class JavaReflectionServiceImpl implements JavaReflectionService {
    private Reflections reflections;

    @PostConstruct
    private void init() {
        String specifiedPackage = System.getProperty("targetPackage");
        log.info("User specified package: {}", specifiedPackage);
        reflections = new Reflections(specifiedPackage);
    }

    @Override
    public Set<JClass> scanForClasses() {
        return null;
    }

    @Override
    public Set<JInterface> scanForInterfaces() {
        return null;
    }
}
