package com.truecaller.prefixesmatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class PrefixesMatchApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void init() throws Exception {
        shouldIngestPrefixes();
    }

    @Test
    void shouldIngestPrefixes() throws Exception {
        String samplePrefixes = "abracadabra\nindia\nsweden\ntruecaller";
        mockMvc.perform(post("/api/truecaller/index")
                        .contentType("text/plain")
                        .content(samplePrefixes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(4)))
                .andExpect(jsonPath("$.failed", is(0)));
    }

    @Test
    void shouldReturnLongestPrefixWhenExactMatching() throws Exception {
        String sampleStr = "truecaller";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
//                        .contentType("text/plain")
                        .param("partial", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is(sampleStr)))
                .andExpect(jsonPath("$.matched", is("FULL")));

    }

    @Test
    void shouldReturnLongestPrefixWhenSubMatching() throws Exception {
        String sampleStr = "truecallertruecallertruecaller";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
//                        .contentType("text/plain")
                        .param("partial", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is("truecaller")))
                .andExpect(jsonPath("$.matched", is("FULL")));

    }

    @Test
    void shouldReturnEmptyPrefixWhenNotExactMatching() throws Exception {
        String sampleStr = "atruecaller";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
//                        .contentType("text/plain")
                        .param("partial", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is("")))
                .andExpect(jsonPath("$.matched", is("NONE")));

    }

    @Test
    void shouldReturnEmptyPrefixWhenPartialMatchingDisabled1() throws Exception {
        String sampleStr = "trueca";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
                        .param("partial", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is("")))
                .andExpect(jsonPath("$.matched", is("NONE")));

    }

    @Test
    void shouldReturnEmptyPrefixWhenPartialMatchingDisabled2() throws Exception {
        String sampleStr = "truecatruecallertruecaller";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
                        .param("partial", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is("")))
                .andExpect(jsonPath("$.matched", is("NONE")));

    }

    @Test
    void shouldReturnLongestPrefixWhenPartialMatchingEnabled1() throws Exception {
        String sampleStr = "trueca";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
                        .param("partial", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is(sampleStr)))
                .andExpect(jsonPath("$.matched", is("PARTIAL")));

    }

    @Test
    void shouldReturnLongestPrefixWhenPartialMatchingEnabled2() throws Exception {
        String sampleStr = "truecatruecallertruecaller";
        mockMvc.perform(get("/api/truecaller/search/{input}", sampleStr)
                        .param("partial", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prefix", is("trueca")))
                .andExpect(jsonPath("$.matched", is("PARTIAL")));

    }
}
