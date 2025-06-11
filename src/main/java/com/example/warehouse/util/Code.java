package com.example.warehouse.util;

public enum Code {

    SUCCESS(200, "Success"),
    CREATED(201, "Created successfully"),

    UPDATED_SUCCESSFULLY(200, "Update successful"),
    ACCEPTED(202, "Update request accepted"),
    REJECT(203, "Update request reject"),
    NO_CONTENT(204, "Update successful, no content returned"),

    BAD_REQUEST(400, "Invalid request data"),
    UNAUTHORIZED(401, "Unauthorized access"),
    FORBIDDEN(403, "Permission denied"),
    NOT_FOUND(404, "Resource not found"),
    INTERNAL_SERVER_ERROR(500, "An unexpected error occurred");

    private final int code;
    private final String message;

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
