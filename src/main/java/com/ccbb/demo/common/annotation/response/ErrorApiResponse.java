package com.ccbb.demo.common.annotation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorApiResponse extends ApiResponse{
    private ErrorApiResponse(int status, String errorMessage){
        super(status, errorMessage);
    }
    public static ErrorApiResponse of(int status, String errorMessage){
        return new ErrorApiResponse(status, errorMessage);
    }
}