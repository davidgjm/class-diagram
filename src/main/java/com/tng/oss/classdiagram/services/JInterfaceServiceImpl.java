package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JInterface;
import com.tng.oss.classdiagram.respositories.JInterfaceRepository;
import org.springframework.stereotype.Service;

@Service
public class JInterfaceServiceImpl implements JInterfaceService {
    private final JInterfaceRepository repository;

    public JInterfaceServiceImpl(JInterfaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveOrUpdate(JInterface entity) {
        repository.save(entity);
    }

    @Override
    public void delete(JInterface entity) {
        repository.delete(entity);
    }

    @Override
    public boolean exists(JInterface entity) {
        return repository.existsByNameAndDefinedInPackage(entity.getName(), entity.getDefinedInPackage());
    }
}
