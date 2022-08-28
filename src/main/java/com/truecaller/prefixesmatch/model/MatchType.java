package com.truecaller.prefixesmatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MatchType {
    FULL("Full"),
    PARTIAL("Partial"),
    NONE("None");

    private String matchType;
}
