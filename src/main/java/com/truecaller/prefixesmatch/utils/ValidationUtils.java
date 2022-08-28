package com.truecaller.prefixesmatch.utils;

import com.truecaller.prefixesmatch.exception.GenericException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.Map;

@UtilityClass
public class ValidationUtils {

    public static void validate(String payload) {
        if (payload == null || payload.length() == 0) {
            throw GenericException.builder()
                    .httpCode(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .context(Map.of("reason", "empty prefix payload"))
                    .build();
        }
    }
}
