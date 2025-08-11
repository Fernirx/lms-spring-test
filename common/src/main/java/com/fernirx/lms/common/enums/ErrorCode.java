package com.fernirx.lms.common.enums;

import com.fernirx.lms.common.constants.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // ========== SYSTEM ERRORS ==========
    METHOD_NOT_ALLOWED("ERR_METHOD_NOT_ALLOWED", ErrorMessages.METHOD_NOT_ALLOWED, ErrorCategory.METHOD_NOT_ALLOWED),
    TOO_MANY_REQUESTS("ERR_TOO_MANY_REQUESTS", ErrorMessages.TOO_MANY_REQUESTS, ErrorCategory.RATE_LIMITING),
    SERVICE_UNAVAILABLE("ERR_SERVICE_UNAVAILABLE", ErrorMessages.SERVICE_UNAVAILABLE, ErrorCategory.SERVER),

    // ========== VALIDATION ERRORS ==========
    REQUIRED_FIELD("ERR_REQUIRED", ErrorMessages.REQUIRED_FIELD, ErrorCategory.VALIDATION),
    INVALID_EMAIL("ERR_INVALID_EMAIL", ErrorMessages.INVALID_EMAIL, ErrorCategory.VALIDATION),
    VALUE_TOO_SHORT("ERR_TOO_SHORT", ErrorMessages.VALUE_TOO_SHORT, ErrorCategory.VALIDATION),
    INVALID_PHONE("ERR_INVALID_PHONE", ErrorMessages.INVALID_PHONE, ErrorCategory.VALIDATION),

    // ========== AUTHENTICATION ERRORS ==========
    INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS", ErrorMessages.INVALID_CREDENTIALS, ErrorCategory.AUTHENTICATION),
    EXPIRED_TOKEN("AUTH_EXPIRED_TOKEN", ErrorMessages.EXPIRED_TOKEN, ErrorCategory.AUTHENTICATION),
    ACCESS_DENIED("AUTH_ACCESS_DENIED", ErrorMessages.ACCESS_DENIED, ErrorCategory.AUTHORIZATION),

    // ========== RESOURCE NOT FOUND ERRORS ==========
    USER_NOT_FOUND("USER_NOT_FOUND", ErrorMessages.USER_NOT_FOUND, ErrorCategory.NOT_FOUND),
    COURSE_NOT_FOUND("COURSE_NOT_FOUND", ErrorMessages.COURSE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", ErrorMessages.ROLE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    DEPARTMENT_NOT_FOUND("DEPARTMENT_NOT_FOUND", ErrorMessages.DEPARTMENT_NOT_FOUND, ErrorCategory.NOT_FOUND),
    MAJOR_NOT_FOUND("MAJOR_NOT_FOUND", ErrorMessages.MAJOR_NOT_FOUND, ErrorCategory.NOT_FOUND),
    TEACHER_NOT_FOUND("TEACHER_NOT_FOUND", ErrorMessages.TEACHER_NOT_FOUND, ErrorCategory.NOT_FOUND),
    CLASS_NOT_FOUND("CLASS_NOT_FOUND", ErrorMessages.CLASS_NOT_FOUND, ErrorCategory.NOT_FOUND),
    STUDENT_NOT_FOUND("STUDENT_NOT_FOUND", ErrorMessages.STUDENT_NOT_FOUND, ErrorCategory.NOT_FOUND),
    OFFICE_NOT_FOUND("OFFICE_NOT_FOUND", ErrorMessages.OFFICE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND", ErrorMessages.EMPLOYEE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    SCHOOL_YEAR_NOT_FOUND("SCHOOL_YEAR_NOT_FOUND", ErrorMessages.SCHOOL_YEAR_NOT_FOUND, ErrorCategory.NOT_FOUND),
    SEMESTER_NOT_FOUND("SEMESTER_NOT_FOUND", ErrorMessages.SEMESTER_NOT_FOUND, ErrorCategory.NOT_FOUND),
    SUBJECT_NOT_FOUND("SUBJECT_NOT_FOUND", ErrorMessages.SUBJECT_NOT_FOUND, ErrorCategory.NOT_FOUND),
    OFFERING_NOT_FOUND("OFFERING_NOT_FOUND", ErrorMessages.OFFERING_NOT_FOUND, ErrorCategory.NOT_FOUND),
    CLASSROOM_NOT_FOUND("CLASSROOM_NOT_FOUND", ErrorMessages.CLASSROOM_NOT_FOUND, ErrorCategory.NOT_FOUND),
    ASSIGNMENT_NOT_FOUND("ASSIGNMENT_NOT_FOUND", ErrorMessages.ASSIGNMENT_NOT_FOUND, ErrorCategory.NOT_FOUND),
    REGISTRATION_NOT_FOUND("REGISTRATION_NOT_FOUND", ErrorMessages.REGISTRATION_NOT_FOUND, ErrorCategory.NOT_FOUND),
    TUITION_NOT_FOUND("TUITION_NOT_FOUND", ErrorMessages.TUITION_NOT_FOUND, ErrorCategory.NOT_FOUND),
    NOTICE_NOT_FOUND("NOTICE_NOT_FOUND", ErrorMessages.NOTICE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    DOCUMENT_NOT_FOUND("DOCUMENT_NOT_FOUND", ErrorMessages.DOCUMENT_NOT_FOUND, ErrorCategory.NOT_FOUND),
    GRADE_NOT_FOUND("GRADE_NOT_FOUND", ErrorMessages.GRADE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    ASSIGNMENT_CHANGE_NOT_FOUND("ASSIGNMENT_CHANGE_NOT_FOUND", ErrorMessages.ASSIGNMENT_CHANGE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    DEPT_HEAD_NOT_FOUND("DEPT_HEAD_NOT_FOUND", ErrorMessages.DEPT_HEAD_NOT_FOUND, ErrorCategory.NOT_FOUND),
    TEACHER_MAJOR_NOT_FOUND("TEACHER_MAJOR_NOT_FOUND", ErrorMessages.TEACHER_MAJOR_NOT_FOUND, ErrorCategory.NOT_FOUND),
    EXAM_SCHEDULE_NOT_FOUND("EXAM_SCHEDULE_NOT_FOUND", ErrorMessages.EXAM_SCHEDULE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    OFFICE_HEAD_NOT_FOUND("OFFICE_HEAD_NOT_FOUND", ErrorMessages.OFFICE_HEAD_NOT_FOUND, ErrorCategory.NOT_FOUND),
    CONDUCT_SCORE_NOT_FOUND("CONDUCT_SCORE_NOT_FOUND", ErrorMessages.CONDUCT_SCORE_NOT_FOUND, ErrorCategory.NOT_FOUND),
    STUDENT_CREDITS_NOT_FOUND("STUDENT_CREDITS_NOT_FOUND", ErrorMessages.STUDENT_CREDITS_NOT_FOUND, ErrorCategory.NOT_FOUND),
    PREREQUISITE_NOT_FOUND("PREREQUISITE_NOT_FOUND", ErrorMessages.PREREQUISITE_NOT_FOUND, ErrorCategory.NOT_FOUND);

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

    public boolean isCategory(ErrorCategory category) {
        return this.category == category;
    }

    public boolean isValidationError() {
        return this.category == ErrorCategory.VALIDATION;
    }

    public boolean isNotFoundError() {
        return this.category == ErrorCategory.NOT_FOUND;
    }

    public boolean isAuthError() {
        return this.category == ErrorCategory.AUTHENTICATION ||
                this.category == ErrorCategory.AUTHORIZATION;
    }

}
