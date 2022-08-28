package com.truecaller.prefixesmatch.handler;


import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import com.truecaller.prefixesmatch.repository.inmemory.TrieNodePrefixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MatcherHandler {
    @Autowired
    private TrieNodePrefixRepository trieNodePrefixRepository;

    public boolean processPrefix(String prefix) {
        return trieNodePrefixRepository.insertPrefix(prefix);
    }

    public PrefixSearchResponse searchLongestPrefix(String str, boolean partial) {
        return trieNodePrefixRepository.searchLongestPrefix(str, partial);
    }
}
