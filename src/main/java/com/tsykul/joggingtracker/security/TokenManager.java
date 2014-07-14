package com.tsykul.joggingtracker.security;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
@Service
public class TokenManager {
    private Map<String, String> tokenMap;

}
