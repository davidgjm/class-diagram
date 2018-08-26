package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;
import com.tng.oss.classdiagram.respositories.JClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
public class JClassServiceImpl implements JClassService {
    private final JClassRepository classRepository;

    public JClassServiceImpl(JClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public void saveOrUpdate(JClass entity) {
        Optional<JClass> persistedOptional = find(entity);
        JClass target;
        /*
         * 1. Saves the class itself
         * 2. parent
         * 3. implementedInterfaces
         * 4. subclasses
         */

        if (!persistedOptional.isPresent()) {
            log.info("The provided entity does not exist in db. Creating...");
            target = entity;
        } else {
            target = persistedOptional.get();
            target.setImplementations(entity.getImplementations());
            target.setImplementedInterfaces(entity.getImplementedInterfaces());
            target.setParent(entity.getParent());
            target.setInterface(entity.isInterface());
            target.setSubclasses(entity.getSubclasses());
        }
        classRepository.save(target);
    }

    @Override
    public void delete(JClass instance) {
        Assert.notNull(instance, "Instance cannot be null!");
        log.info("Deleting class {}", instance);
        classRepository.delete(instance);

    }

    private Optional<JClass> find(JClass entity) {
        if (entity.getId() != null) {
            return classRepository.findById(entity.getId());
        } else {
            return classRepository.findJClassByClassNameAndPackageName(entity.getClassName(), entity.getPackageName());
        }
    }

    @Override
    public boolean exists(JClass entity) {
        Assert.notNull(entity, "entity cannot be null!");
        return classRepository.existsJClassByClassNameAndPackageName(entity.getClassName(), entity.getPackageName());
    }
}
