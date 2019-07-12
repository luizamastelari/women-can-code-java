package com.womencancode.rbac.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private int status;
    private String message;
}
