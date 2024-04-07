package dao;


import java.util.List;
import java.util.Optional;


public interface Dao<K, E> {
    E create(E entity);

    E getById(K id);

    List<E> findAll();

    void update(E entity);

    boolean delete(K id);
}