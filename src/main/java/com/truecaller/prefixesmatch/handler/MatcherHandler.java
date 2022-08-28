package com.truecaller.prefixesmatch.handler;


import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import com.truecaller.prefixesmatch.repository.inmemory.TrieNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MatcherHandler {
    @Autowired
    private TrieNodeRepository trieNodeRepository;

    public boolean processPrefix(String prefix) {
        return trieNodeRepository.insert(prefix);
    }

    public PrefixSearchResponse searchLongestPrefix(String str, boolean partial) {
        return trieNodeRepository.searchLongestPrefix(str, partial);
    }
}
