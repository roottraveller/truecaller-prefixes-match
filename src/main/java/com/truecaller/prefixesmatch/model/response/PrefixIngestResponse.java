package com.truecaller.prefixesmatch.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrefixIngestResponse {
    private long success;
    private long failed;
}
