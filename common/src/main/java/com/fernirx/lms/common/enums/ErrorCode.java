package com.fernirx.lms.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // System
    METHOD_NOT_ALLOWED("ERR_METHOD_NOT_ALLOWED", "HTTP method not allowed", ErrorCategory.METHOD_NOT_ALLOWED),
    TOO_MANY_REQUESTS("ERR_TOO_MANY_REQUESTS", "Too many requests", ErrorCategory.RATE_LIMITING),
    SERVICE_UNAVAILABLE("ERR_SERVICE_UNAVAILABLE", "Service unavailable", ErrorCategory.SERVER),

    // Validation
    REQUIRED_FIELD("ERR_REQUIRED", "This field is required", ErrorCategory.VALIDATION),
    INVALID_EMAIL("ERR_INVALID_EMAIL", "Invalid email format", ErrorCategory.VALIDATION),
    VALUE_TOO_SHORT("ERR_TOO_SHORT", "Value too short", ErrorCategory.VALIDATION),

    // Auth
    INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS", "Invalid credentials", ErrorCategory.AUTHENTICATION),
    EXPIRED_TOKEN("AUTH_EXPIRED_TOKEN", "Token expired", ErrorCategory.AUTHENTICATION),
    ACCESS_DENIED("AUTH_ACCESS_DENIED", "Access denied", ErrorCategory.AUTHORIZATION),

    // Resource
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", ErrorCategory.NOT_FOUND),
    COURSE_NOT_FOUND("COURSE_NOT_FOUND", "Course not found", ErrorCategory.NOT_FOUND),

    // More Resource
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Role not found", ErrorCategory.NOT_FOUND),
    DEPARTMENT_NOT_FOUND("DEPARTMENT_NOT_FOUND", "Department not found", ErrorCategory.NOT_FOUND),
    MAJOR_NOT_FOUND("MAJOR_NOT_FOUND", "Major not found", ErrorCategory.NOT_FOUND),
    TEACHER_NOT_FOUND("TEACHER_NOT_FOUND", "Teacher not found", ErrorCategory.NOT_FOUND),
    CLASS_NOT_FOUND("CLASS_NOT_FOUND", "Class not found", ErrorCategory.NOT_FOUND),
    STUDENT_NOT_FOUND("STUDENT_NOT_FOUND", "Student not found", ErrorCategory.NOT_FOUND),
    OFFICE_NOT_FOUND("OFFICE_NOT_FOUND", "Office not found", ErrorCategory.NOT_FOUND),
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND", "Employee not found", ErrorCategory.NOT_FOUND),
    SCHOOL_YEAR_NOT_FOUND("SCHOOL_YEAR_NOT_FOUND", "School year not found", ErrorCategory.NOT_FOUND),
    SEMESTER_NOT_FOUND("SEMESTER_NOT_FOUND", "Semester not found", ErrorCategory.NOT_FOUND),
    SUBJECT_NOT_FOUND("SUBJECT_NOT_FOUND", "Subject not found", ErrorCategory.NOT_FOUND),
    OFFERING_NOT_FOUND("OFFERING_NOT_FOUND", "Course offering not found", ErrorCategory.NOT_FOUND),
    CLASSROOM_NOT_FOUND("CLASSROOM_NOT_FOUND", "Classroom not found", ErrorCategory.NOT_FOUND),
    ASSIGNMENT_NOT_FOUND("ASSIGNMENT_NOT_FOUND", "Course assignment not found", ErrorCategory.NOT_FOUND),
    REGISTRATION_NOT_FOUND("REGISTRATION_NOT_FOUND", "Registration not found", ErrorCategory.NOT_FOUND),
    TUITION_NOT_FOUND("TUITION_NOT_FOUND", "Tuition fee record not found", ErrorCategory.NOT_FOUND),
    NOTICE_NOT_FOUND("NOTICE_NOT_FOUND", "Notice not found", ErrorCategory.NOT_FOUND),
    DOCUMENT_NOT_FOUND("DOCUMENT_NOT_FOUND", "Course document not found", ErrorCategory.NOT_FOUND),
    GRADE_NOT_FOUND("GRADE_NOT_FOUND", "Grade record not found", ErrorCategory.NOT_FOUND),
    ASSIGNMENT_CHANGE_NOT_FOUND("ASSIGNMENT_CHANGE_NOT_FOUND", "Assignment change record not found", ErrorCategory.NOT_FOUND),
    DEPT_HEAD_NOT_FOUND("DEPT_HEAD_NOT_FOUND", "Department head not found", ErrorCategory.NOT_FOUND),
    TEACHER_MAJOR_NOT_FOUND("TEACHER_MAJOR_NOT_FOUND", "Teacher major not found", ErrorCategory.NOT_FOUND),
    EXAM_SCHEDULE_NOT_FOUND("EXAM_SCHEDULE_NOT_FOUND", "Exam schedule not found", ErrorCategory.NOT_FOUND),
    OFFICE_HEAD_NOT_FOUND("OFFICE_HEAD_NOT_FOUND", "Office head not found", ErrorCategory.NOT_FOUND),
    CONDUCT_SCORE_NOT_FOUND("CONDUCT_SCORE_NOT_FOUND", "Conduct score not found", ErrorCategory.NOT_FOUND),
    STUDENT_CREDITS_NOT_FOUND("STUDENT_CREDITS_NOT_FOUND", "Student credits not found", ErrorCategory.NOT_FOUND),
    PREREQUISITE_NOT_FOUND("PREREQUISITE_NOT_FOUND", "Course prerequisite not found", ErrorCategory.NOT_FOUND);

    private final String code;
    private final String message;
    private final ErrorCategory category;

    public HttpStatus getHttpStatus() {
        return category.getHttpStatus();
    }

    public int getPriority() {
        return category.getPriority();
    }

    public static Optional<ErrorCode> fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
    }

}
