package com.truecaller.prefixesmatch.controller;

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

    @PostMapping(name = "/preprocess", consumes = "text/plain")
    public ResponseEntity<Boolean> ingestMatch(@RequestBody String payload) {
        Utils.validate(payload);
        return ResponseEntity.ok(matcherService.ingestPrefixes(payload));
    }

    @GetMapping("/longestprefix/{str}")
    public ResponseEntity<String> getUserInfo(@PathVariable("str") String str) {
        Utils.validate(str);
        return ResponseEntity.ok(matcherService.searchLongestPrefix(str));
    }
}
