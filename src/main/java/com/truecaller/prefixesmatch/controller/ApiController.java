package com.truecaller.prefixesmatch.controller;

import com.truecaller.prefixesmatch.model.response.PrefixIngestResponse;
import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import com.truecaller.prefixesmatch.service.MatcherService;
import com.truecaller.prefixesmatch.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/truecaller")
public class ApiController {
    @Autowired
    private MatcherService matcherService;

    @PostMapping(value = "/index", consumes = "text/plain")
    public ResponseEntity<PrefixIngestResponse> ingestPrefixes(@RequestBody String payload) {
        Utils.validate(payload);
        return ResponseEntity.ok(matcherService.ingestPrefixes(payload));
    }

    @GetMapping("/search/{input}")
    public ResponseEntity<PrefixSearchResponse> searchLongestPrefix(@PathVariable("input") String input,
                                                                    @RequestParam(name = "partial", defaultValue = "false") boolean partial) {
        Utils.validate(input);
        return ResponseEntity.ok(matcherService.searchLongestPrefix(input, partial));
    }
}
