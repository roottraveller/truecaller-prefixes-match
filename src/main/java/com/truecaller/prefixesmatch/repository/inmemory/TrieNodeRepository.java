package com.truecaller.prefixesmatch.repository.inmemory;

import com.truecaller.prefixesmatch.constants.Constants;
import com.truecaller.prefixesmatch.exception.GenericException;
import com.truecaller.prefixesmatch.model.TrieNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrieNodeRepository {
    private TrieNode root;

    public boolean insert(String str) {
        if (root == null) {
            root = TrieNode.builder()
                    .children(Map.of())
                    .isEnd(false)
                    .build();
        }

        Map<Character, TrieNode> children = root.getChildren();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            // if matched, move to next children
            if (children.get(ch) != null) {
                children = children.get(ch).getChildren();
            } else {
                children.put(ch, TrieNode.builder()
                        .children(Map.of())
                        .isEnd(false)
                        .build());
            }

            // set isEnd=true when end of str
            if (i == str.length() - 1) {
                children.get(ch).setEnd(true);
            }
        }
        return true;
    }

    public String searchLongestPrefix(String str, boolean partial) {
        if (root == null) {
            throw GenericException.builder()
                    .httpCode(HttpStatus.PRECONDITION_FAILED.value())
                    .httpStatus(HttpStatus.PRECONDITION_REQUIRED.getReasonPhrase())
                    .context(Map.of("reason", "required preprocessing of prefix"))
                    .build();
        }

        int prefixMatchEndIdx = 0;
        boolean isEnd = false;
        Map<Character, TrieNode> children = root.getChildren();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            // if matched, move to next children
            if (children.get(ch) != null) {
                prefixMatchEndIdx = i;
                children = children.get(ch).getChildren();
                isEnd = children.get(ch).isEnd();
            } else {
                break;
            }
        }
        if (isEnd || partial) {
            return str.substring(prefixMatchEndIdx + 1);
        }
        return Constants.EMPTY_STRING;
    }
}
