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
public class TrieNodePrefixRepository implements PrefixRepository{
    private TrieNode root;

    public boolean insertPrefix(String str) {
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
        // set isEndOfWord=true for each word
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
        boolean isEndOfWord = false;
        TrieNode trieNode = root;
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            // get next children
            trieNode = trieNode.getChildren().get(ch);
            if (trieNode == null) {
                break;
            }
            prefixMatchEndIdx = i;
            isEndOfWord = trieNode.isEndOfWord();
        }

        if (isEndOfWord) {
            return buildPrefixSearchResponse(MatchType.FULL, str.substring(0, prefixMatchEndIdx + 1));
        }
        if (partial && prefixMatchEndIdx > 0) {
            return buildPrefixSearchResponse(MatchType.PARTIAL, str.substring(0, prefixMatchEndIdx + 1));
        }
        return buildPrefixSearchResponse(MatchType.NONE, Constants.EMPTY_STRING);
    }

    private TrieNode getNewTrieNode() {
        return TrieNode.builder()
                .children(new HashMap<>())
                .isEndOfWord(false)
                .build();
    }

    private PrefixSearchResponse buildPrefixSearchResponse(MatchType matchType, String prefix) {
        return PrefixSearchResponse.builder()
                .matched(matchType)
                .prefix(prefix)
                .build();
    }
}
