package com.truecaller.prefixesmatch.repository.inmemory;

import com.truecaller.prefixesmatch.constants.Constants;
import com.truecaller.prefixesmatch.exception.GenericException;
import com.truecaller.prefixesmatch.model.MatchType;
import com.truecaller.prefixesmatch.model.TrieNode;
import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrieNodeRepository {
    private TrieNode root;

    public boolean insert(String str) {
        if (root == null) {
            root = getNewTrieNode();
        }

        TrieNode trieNode = root;
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            // if char does not match, insert a new node
            // else move to next children
            if (!trieNode.getChildren().containsKey(ch)) {
                trieNode.getChildren().put(ch, getNewTrieNode());
            }
            trieNode = trieNode.getChildren().get(ch);
        }
        // set isEndOfWord=true
        trieNode.setEndOfWord(true);
        return true;
    }

    public PrefixSearchResponse searchLongestPrefix(String str, boolean partial) {
        if (root == null) {
            throw GenericException.builder()
                    .httpCode(HttpStatus.PRECONDITION_FAILED.value())
                    .httpStatus(HttpStatus.PRECONDITION_REQUIRED.getReasonPhrase())
                    .context(Map.of("reason", "required preprocessing of prefix"))
                    .build();
        }

        int prefixMatchEndIdx = 0;
        boolean isEnd = false;
        TrieNode trieNode = root;
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            // get next children
            trieNode = trieNode.getChildren().get(ch);
            if (trieNode == null) {
                break;
            }
            prefixMatchEndIdx = i;
            isEnd = trieNode.isEndOfWord();
        }
        if (isEnd) {
            return PrefixSearchResponse.builder()
                    .matched(MatchType.FULL)
                    .prefix(str.substring(0, prefixMatchEndIdx + 1))
                    .build();
        }
        if (partial && prefixMatchEndIdx > 0) {
            return PrefixSearchResponse.builder()
                    .matched(MatchType.PARTIAL)
                    .prefix(str.substring(0, prefixMatchEndIdx + 1))
                    .build();
        }
        return PrefixSearchResponse.builder()
                .matched(MatchType.NONE)
                .prefix(Constants.EMPTY_STRING)
                .build();
    }

    private TrieNode getNewTrieNode() {
        return TrieNode.builder()
                .children(new HashMap<>())
                .isEndOfWord(false)
                .build();
    }
}
