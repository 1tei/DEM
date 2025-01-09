package com.datzm029.dem.dao;

import java.util.List;
import java.util.UUID;

public interface Dao<T> {
    T insert(T t);

    List<T> selectAll();

    default int selectAllById(String id) {
        return 0;
    }

    default T selectById(UUID id) {
        return null;
    }

    T checkIfExist(T object);

    default void update(UUID userIdNo, int energija) {

    }
    default void updateUz(UUID userIdUz, int energija){

    }
    default UUID getId(String login){
        return null;
    }
}
