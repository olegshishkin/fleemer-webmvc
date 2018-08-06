package com.fleemer.webmvc.service.implementation;

import java.util.Optional;
import com.fleemer.webmvc.service.BaseService;
import com.fleemer.webmvc.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractService <T, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {
    @Override
    public long count() {
        return getOperationRepository().count();
    }

    @Override
    public boolean existsById(ID id) {
        return getOperationRepository().existsById(id);
    }

    @Override
    public T getOne(ID id) {
        return getOperationRepository().getOne(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return getOperationRepository().findById(id);
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return getOperationRepository().findAllById(ids);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getOperationRepository().findAll(pageable);
    }

    @Override
    public Iterable<T> findAll() {
        return getOperationRepository().findAll();
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return getOperationRepository().findAll(sort);
    }

    @Override
    public <S extends T> S save(S entity) throws ServiceException {
        return getOperationRepository().save(entity);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return getOperationRepository().saveAll(entities);
    }

    @Override
    public void deleteById(ID id) {
        getOperationRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        getOperationRepository().delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        getOperationRepository().deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        getOperationRepository().deleteAll();
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        getOperationRepository().deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        getOperationRepository().deleteAllInBatch();
    }

    protected abstract R getOperationRepository();
}
