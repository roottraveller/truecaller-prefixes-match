package com.truecaller.prefixesmatch.service.impl;

import com.truecaller.prefixesmatch.constants.Constants;
import com.truecaller.prefixesmatch.handler.MatcherHandler;
import com.truecaller.prefixesmatch.model.response.PrefixIngestResponse;
import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import com.truecaller.prefixesmatch.service.MatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatcherServiceImpl implements MatcherService {
    @Autowired
    private MatcherHandler matcherHandler;

    @Override
    public PrefixIngestResponse ingestPrefixes(String payload) {
        String[] split = payload.split(Constants.SEPARATOR_NEWLINE);
        long successCount = 0, failedCount = 0;
        for (String str : split) {
            String trim = str.trim();
            if (trim.length() == 0) {
                ++failedCount;
                continue;
            }
            boolean status = matcherHandler.processPrefix(trim);
            if (status) {
                ++successCount;
            } else {
                ++failedCount;
            }
        }
        return PrefixIngestResponse.builder()
                .success(successCount)
                .failed(failedCount)
                .build();
    }

    @Override
    public PrefixSearchResponse searchLongestPrefix(String str, boolean partial) {
        return matcherHandler.searchLongestPrefix(str.trim(), partial);
    }
}