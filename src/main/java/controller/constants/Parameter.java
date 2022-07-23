package controller.constants;

public enum Parameter {
    FIRST_NAME("firstName"),
    COURSE_SORT_OPTION("sortOption"),
    COURSE_SORT_DURATION_ASC("courseDurationAsc"),
    COURSE_SORT_DURATION_DESC("courseDurationDesc"),
    COURSE_SORT_STUDENTS_ASC("courseStudentsAsc"),
    COURSE_SORT_STUDENTS_DESC("courseStudentsDesc"),
    COURSE_SORT_NAME_DESC("courseNameDesc"),
    COURSE_SORT_NAME_ASC("courseNameAsc"),
    COURSE_TUTOR_ID("tutorId"),
    COURSE_THEME_ID("themeId"),
    PAGE("page");

    private final String value;

    Parameter(String value) { this.value = value; }

    public String getValue() { return value; }
}
