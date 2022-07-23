package model.dao.mapper;

import model.entity.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper implements EntityMapper<Theme> {
    @Override
    public Theme extract(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Theme.Builder()
                .setId(id)
                .setName(name)
                .build();
    }
}
