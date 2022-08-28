package com.truecaller.prefixesmatch.service.impl;

import com.truecaller.prefixesmatch.constants.Constants;
import com.truecaller.prefixesmatch.exception.GenericException;
import com.truecaller.prefixesmatch.handler.MatcherHandler;
import com.truecaller.prefixesmatch.service.MatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MatcherServiceImpl implements MatcherService {
    @Autowired
    private MatcherHandler matcherHandler;

    @Override
    public boolean ingestPrefixes(String payload) {
        String[] split = payload.split(Constants.SEPARATOR_NEWLINE);
        boolean status = false;
        for (String str : split) {
            status = matcherHandler.processPrefix(str.trim());
            if (!status) {
                throw GenericException.builder()
                        .httpCode(HttpStatus.BAD_REQUEST.value())
                        .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .context(Map.of("reason", String.format("failed when processing prefix%s", str)))
                        .build();
            }
        }
        return status;
    }

    @Override
    public String searchLongestPrefix(String str) {
        return matcherHandler.searchLongestPrefix(str);
    }
}
