package com.emre.utility;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenCreator {
    public String createToken(Long authid) {
        return "token: ext:" + System.currentTimeMillis() + "-5533-TK/" + authid;
    }

    public Optional<Long> getAuthId(String token) {
        try {
            int position = token.indexOf("ext:");
            int endposition = token.indexOf("-5533-TK");
            Long time = Long.parseLong(token.substring(position + 4, endposition));
            if ((System.currentTimeMillis() - time) > 1000L * 60) return Optional.empty();
            Long authid = Long.parseLong(token.split("/")[1]);
            return Optional.of(authid);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
