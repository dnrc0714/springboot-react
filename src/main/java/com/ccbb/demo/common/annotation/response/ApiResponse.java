package com.ccbb.demo.common.annotation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class ApiResponse {
    protected final int status;
    protected final String message;
}