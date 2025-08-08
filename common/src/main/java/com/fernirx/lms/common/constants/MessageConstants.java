package com.fernirx.lms.common.constants;

public final class MessageConstants {

    // Success Messages
    public static final String SUCCESS_CREATE = "Created successfully";
    public static final String SUCCESS_UPDATE = "Updated successfully";
    public static final String SUCCESS_DELETE = "Deleted successfully";
    public static final String SUCCESS_LOGIN = "Login successful";
    public static final String SUCCESS_LOGOUT = "Logout successful";
    public static final String SUCCESS_PASSWORD_RESET = "Password reset successful";

    // Error Messages
    public static final String ERROR_UNAUTHORIZED = "Unauthorized access";
    public static final String ERROR_FORBIDDEN = "Access forbidden";
    public static final String ERROR_NOT_FOUND = "Resource not found";
    public static final String ERROR_BAD_REQUEST = "Invalid request";
    public static final String ERROR_INTERNAL_SERVER = "Internal server error";
    public static final String ERROR_CONFLICT = "Resource conflict";
    public static final String ERROR_VALIDATION_FAILED = "Validation failed";

    private MessageConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
