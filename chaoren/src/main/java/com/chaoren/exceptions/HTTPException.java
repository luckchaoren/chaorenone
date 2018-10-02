package com.chaoren.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class HTTPException extends Exception {
    private String message;
    private int statusCode;

    public HTTPException(String message) {
        this.message = message;
    }
}
