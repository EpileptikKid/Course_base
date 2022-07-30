package model.dao.mapper;

import model.entity.Course;
import model.entity.Theme;
import model.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class CourseMapper implements EntityMapper<Course> {
    @Override
    public Course extract(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String courseName = resultSet.getString("name");
        LocalDate startDate = LocalDate.parse(resultSet.getString("start_date"));
        LocalDate endDate = LocalDate.parse(resultSet.getString("end_date"));
        String description = resultSet.getString("description");
        Theme theme = new Theme.Builder()
                .setName(resultSet.getString("theme_name"))
                .setId(resultSet.getInt("theme_id"))
                .build();
        Optional<String> tutorLogin = Optional.ofNullable(resultSet.getString("user_id"));

        User tutor = null;
        if (tutorLogin.isPresent()) {
            tutor = new User.Builder()
                    .setId(resultSet.getInt("user_id"))
                    .setLogin(tutorLogin.get())
                    .setFirstName(resultSet.getString("first_name"))
                    .setLastName(resultSet.getString("last_name"))
                    .build();
        }
        return new Course.Builder()
                .setId(id)
                .setName(courseName)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setTutor(tutor)
                .setTheme(theme)
                .setStudentCount(10)
                .build();
    }
}
