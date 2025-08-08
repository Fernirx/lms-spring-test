package com.fernirx.lms.common.constants;

public final class ApiConstants {

    // Base Paths
    public static final String AUTH_PATH = "/auth";
    public static final String USERS_PATH = "/users";
    public static final String STUDENTS_PATH = "/students";
    public static final String TEACHERS_PATH = "/teachers";
    public static final String COURSES_PATH = "/courses";
    public static final String MAJORS_PATH = "/majors";
    public static final String CLASSES_PATH = "/classes";
    public static final String SCHEDULES_PATH = "/schedules";
    public static final String GRADES_PATH = "/grades";
    public static final String REGISTRATIONS_PATH = "/registrations";
    public static final String NOTICES_PATH = "/notices";
    public static final String REPORTS_PATH = "/reports";

    // HTTP Headers
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CONTENT_TYPE_JSON = "application/json";

    private ApiConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
