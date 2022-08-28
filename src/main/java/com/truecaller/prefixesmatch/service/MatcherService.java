package com.truecaller.prefixesmatch.service;

import com.truecaller.prefixesmatch.model.response.PrefixIngestResponse;
import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import org.springframework.stereotype.Service;

@Service
public interface MatcherService {

    PrefixIngestResponse ingestPrefixes(String payload);

    PrefixSearchResponse searchLongestPrefix(String str, boolean partial);
}
