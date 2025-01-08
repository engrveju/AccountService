package com.threeline.AccountService.util;

import com.threeline.AccountService.dto.response.DefaultApiResponse;

public class AppUtils {
    public static DefaultApiResponse defaultErrorResponse() {
        return new DefaultApiResponse("99","Request Failed",null);
    }

    public static DefaultApiResponse defaultErrorResponse(String message) {
        return new DefaultApiResponse("99",message,null);
    }

    public static DefaultApiResponse defaultErrorResponse(String status, String message) {
        return new DefaultApiResponse(status,message,null);
    }

    public static DefaultApiResponse    defaultSuccessResponse() {
        return new DefaultApiResponse("00","Request Successful",null);
    }

    public static DefaultApiResponse defaultSuccessResponse(Object data) {
        return new DefaultApiResponse("00","Request Successful",data);
    }

    public static DefaultApiResponse defaultSuccessResponse(String message, Object data) {
        return new DefaultApiResponse("00",message,data);
    }
}
