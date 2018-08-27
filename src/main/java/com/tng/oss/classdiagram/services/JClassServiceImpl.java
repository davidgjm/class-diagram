package com.tng.oss.classdiagram.services;

import com.tng.oss.classdiagram.domain.JClass;
import com.tng.oss.classdiagram.respositories.JClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JClassServiceImpl implements JClassService {
    private final JClassRepository classRepository;

    public JClassServiceImpl(JClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Transactional
    @Override
    public void saveOrUpdate(JClass entity) {
        log.info("Saving entity into graph db. {}", entity);
        Optional<JClass> persistedOptional = find(entity);
        JClass target;
        /*
         * 1. Saves the class itself
         * 2. parent
         * 3. implementedInterfaces
         * 4. subclasses
         */

        if (!persistedOptional.isPresent()) {
            target = entity;
            log.info("The provided entity does not exist in db. Creating {}", target);
        } else {
            target = persistedOptional.get();
            log.info("The entity already exists. {}", target);
            target.setImplementations(entity.getImplementations());
            target.setExtendedInterfaces(entity.getExtendedInterfaces());
            target.setParent(entity.getParent());
            target.setInterface(entity.isInterface());
            target.setSubclasses(entity.getSubclasses());
        }
//        classRepository.save(target, 1);
        doSaveJclass(target);
    }

    @Transactional
    protected void doSaveJclass(JClass entity) {
        doAtomicSave(entity);
        entity.getImplementations().forEach(this::doAtomicSave);
        entity.getSubclasses().forEach(this::doAtomicSave);
        entity.getExtendedInterfaces().forEach(this::doAtomicSave);
    }

    private void doAtomicSave(JClass entity, int depth) {
        if (!classRepository.exists(entity.getClassName(), entity.getPackageName())) {
            JClass saved = classRepository.save(entity, depth);
            log.info("Saved entity: {}", saved);
        }
    }

    private void doAtomicSave(JClass entity) {
        this.doAtomicSave(entity, 1);
    }

    @Override
    public void delete(JClass instance) {
        Assert.notNull(instance, "Instance cannot be null!");
        log.info("Deleting class {}", instance);
        classRepository.delete(instance);

    }

    @Transactional
    protected Optional<JClass> find(JClass entity) {
/*        if (entity.getId() != null) {
            return classRepository.findById(entity.getId());
        } else {
            List<JClass> matches = classRepository.findAllByClassNameAndPackageName(entity.getClassName(), entity.getPackageName());
            int size = matches.size();
            if (size > 1) {
                log.error("Multiple classes with the same name found!");
                matches.forEach(k -> log.error(k.toString()));
            }
//            return classRepository.findJClassByClassNameAndPackageName(entity.getClassName(), entity.getPackageName());
            return matches.stream().findAny();
        }*/


        List<JClass> matches = classRepository.findAllByClassNameAndPackageName(entity.getClassName(), entity.getPackageName());
        int size = matches.size();
        if (size > 1) {
            log.error("Multiple classes with the same name found!");
            matches.forEach(k -> log.error(k.toString()));
        }
        return matches.stream().findFirst();
    }

    @Override
    public boolean exists(JClass entity) {
        Assert.notNull(entity, "entity cannot be null!");
        return classRepository.existsJClassByClassNameAndPackageName(entity.getClassName(), entity.getPackageName());
    }
}
