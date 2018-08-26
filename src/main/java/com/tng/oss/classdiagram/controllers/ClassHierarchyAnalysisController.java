package com.tng.oss.classdiagram.controllers;

import com.tng.oss.classdiagram.services.JavaReflectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/diagram/class")
public class ClassHierarchyAnalysisController {
    private final JavaReflectionService reflectionService;

    public ClassHierarchyAnalysisController(JavaReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void analyze(@RequestBody String[] packageNames) {
        log.info("Analyzing class hierarchy for package {}", packageNames);
        Stream.of(packageNames).forEach(reflectionService::scanJavaTypes);
    }
}
