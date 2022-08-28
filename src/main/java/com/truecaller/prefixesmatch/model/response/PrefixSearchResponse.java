package com.truecaller.prefixesmatch.model.response;

import com.truecaller.prefixesmatch.model.MatchType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrefixSearchResponse {
    private String prefix;
    private MatchType matched;
}
