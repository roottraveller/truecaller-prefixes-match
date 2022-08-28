package com.truecaller.prefixesmatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrieNode {
    private Map<Character, TrieNode> children;
    private boolean isEndOfWord;
}
