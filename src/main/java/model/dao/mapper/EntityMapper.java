package body.dao.mapper;


import body.dao.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface EntityMapper <T extends Entity> {
    default T makeUnique(Map<Integer, T> cache, T entity) {
        if (entity!=null) {
            cache.putIfAbsent(entity.getId(), entity);
            return cache.get(entity.getId());
        }
        return null;
    }
    T extract(ResultSet resultSet) throws SQLException;
}
