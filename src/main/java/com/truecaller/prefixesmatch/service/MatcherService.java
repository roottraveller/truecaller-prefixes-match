package com.truecaller.prefixesmatch.service;

import org.springframework.stereotype.Service;

@Service
public interface MatcherService {

    boolean ingestPrefixes(String payload);

    String searchLongestPrefix(String str);
}
