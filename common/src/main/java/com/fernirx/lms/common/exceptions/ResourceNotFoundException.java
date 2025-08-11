package com.fernirx.lms.common.exceptions;

public class ResourceNotFoundException extends LmsException {
    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(String.format("%s with id '%s' not found", resourceName, resourceId));
    }
}