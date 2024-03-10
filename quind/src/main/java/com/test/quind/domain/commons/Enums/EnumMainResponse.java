package com.test.quind.domain.commons.Enums;

public enum EnumMainResponse {
    
	OK(0, "successful"),
	ERROR(2, "Internal error");

    
	private final int code;
    private final String message;

    EnumMainResponse(int code, String message) {
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
