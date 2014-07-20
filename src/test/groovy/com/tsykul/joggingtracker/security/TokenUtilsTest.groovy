package com.tsykul.joggingtracker.security

import spock.lang.Specification

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class TokenUtilsTest extends Specification {
    def "Should be able to encode and extract username"() {
        given:
            def email = "test@gmail.com"
        when:
            def token = TokenUtils.createToken(email)
            def username = TokenUtils.getUsername(token)
        then:
            token != null
            username == email
    }
}
