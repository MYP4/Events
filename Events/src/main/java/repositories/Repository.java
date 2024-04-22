package repositories;

import java.util.List;

public interface Repository<K, E> {
    E create(E entity);
    E getById(K id);
    List<E> findAll();
    void update(E entity);
    boolean delete(K id);
}