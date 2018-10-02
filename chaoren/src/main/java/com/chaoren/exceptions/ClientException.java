package com.chaoren.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ClientException extends Exception {
    private int retCode;
    private String message;

    public ClientException(String message) {
        this.message = message;
    }

}
