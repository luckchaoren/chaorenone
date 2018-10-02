package com.chaoren.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ServerExeception extends Exception {
    private int retCode;
    private String message;

    public ServerExeception(String message) {
        this.message = message;
    }

}
