package com.truecaller.prefixesmatch.repository.inmemory;

import com.truecaller.prefixesmatch.model.response.PrefixSearchResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefixRepository {

    boolean insertPrefix(String str);

    PrefixSearchResponse searchLongestPrefix(String str, boolean partial);

}
