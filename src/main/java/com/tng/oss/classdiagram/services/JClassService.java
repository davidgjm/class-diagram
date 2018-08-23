package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;

public interface JClassService {
    void saveOrUpdate(JClass instance);

    void delete(JClass instance);

    boolean exists(JClass entity);
}
