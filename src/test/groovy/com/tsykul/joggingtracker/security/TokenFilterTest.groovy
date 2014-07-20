package com.tsykul.joggingtracker.security

import com.tsykul.joggingtracker.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class TokenFilterTest extends Specification {
    def "Should not authorize if token header is not present"() {
        given:
            def tokenStorage = Mock(TokenStorage)
            def service = Mock(UserService)
            def filter = new TokenFilter(service, tokenStorage)
            def request = Mock(HttpServletRequest)
            def response = Mock(HttpServletResponse)
            def filterChain = Mock(FilterChain)
        when:
            filter.doFilter(request, response, filterChain)
        then:
            1 * filterChain.doFilter(request, response)
    }

    def "Should not authorize if token is not present in storage"() {
        given:
            def tokenStorage = Mock(TokenStorage)
            def service = Mock(UserService)
            def filter = new TokenFilter(service, tokenStorage)
            def request = Mock(HttpServletRequest)
            def response = Mock(HttpServletResponse)
            def filterChain = Mock(FilterChain)
            def email = "test@gmail.com"
            def token = TokenUtils.createToken(email)
            request.getHeader("Auth-Token") >> token
        when:
            filter.doFilter(request, response, filterChain)
        then:
            1 * filterChain.doFilter(request, response)
            1 * tokenStorage.getToken(email)
    }

    def "Should authorize if token present in storage"() {
        given:
            def tokenStorage = new TokenStorage()
            def service = Mock(UserService)
            def filter = new TokenFilter(service, tokenStorage)
            def request = Mock(HttpServletRequest)
            def response = Mock(HttpServletResponse)
            def filterChain = Mock(FilterChain)
            def email = "test@gmail.com"
            def token = TokenUtils.createToken(email)
            request.getHeader("Auth-Token") >> token
            tokenStorage.putToken(email, token)
            service.loadUserByUsername(email) >> Mock(UserDetails)
        when:
            filter.doFilter(request, response, filterChain)
        then:
            1 * filterChain.doFilter(request, response)
            1 * service.loadUserByUsername(email)
    }
}
