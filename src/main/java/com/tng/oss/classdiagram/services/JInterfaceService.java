package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JInterface;

public interface JInterfaceService {
    void saveOrUpdate(JInterface entity);

    void delete(JInterface entity);

    boolean exists(JInterface entity);
}
