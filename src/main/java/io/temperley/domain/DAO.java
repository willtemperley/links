package io.temperley.domain;

import java.util.Collection;

/**
 * Created by will on 01/05/2017.
 */
public interface DAO<T> {

    T create(T entity);
    T update(T entity);
    T delete(T entity);
    Collection<T> all();

}
