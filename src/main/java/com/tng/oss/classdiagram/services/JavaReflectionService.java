package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;
import com.tng.oss.classdiagram.domain.JInterface;

import java.util.Set;

public interface JavaReflectionService {

    void scanJavaTypes(String basePackage);


}
