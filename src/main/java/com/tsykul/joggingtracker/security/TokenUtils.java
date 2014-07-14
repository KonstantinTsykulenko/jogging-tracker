package com.tsykul.joggingtracker.security;

import java.util.Base64;
import java.util.UUID;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
public class TokenUtils {
    public static String createToken(String username) {
        String rawToken = username + ":" + UUID.randomUUID();
        return new String(Base64.getEncoder().encode(rawToken.getBytes()));
    }

    public static String getUsername(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token.getBytes()));
        return decodedToken.split(":")[0];
    }
}
