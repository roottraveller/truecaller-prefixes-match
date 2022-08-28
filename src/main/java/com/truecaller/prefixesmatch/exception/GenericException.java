package com.truecaller.prefixesmatch.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class GenericException extends RuntimeException {
    private int httpCode;
    private String httpStatus;
    private Map<String, Object> context;

}
