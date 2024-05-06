package data.repositories;

import data.exceptions.DBException;

import java.util.List;

public interface Repository<K, E> {
    E create(E entity) throws DBException;
    E getById(K id) throws DBException;
    List<E> getAll() throws DBException;
    void update(E entity) throws DBException;
    boolean delete(K id) throws DBException;
}