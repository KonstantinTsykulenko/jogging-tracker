package com.tsykul.joggingtracker.controller

import com.tsykul.joggingtracker.security.TokenStorage
import com.tsykul.joggingtracker.security.TokenUtils
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class LoginControllerTest extends Specification {

    def "Should be able to login"() {
        given:
            def authManager = Mock(AuthenticationManager)
            def tokenStorage = Mock(TokenStorage)
            def controller = new LoginController(authManager, tokenStorage)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
            def generatedToken = ""
        when:
            def resultActions = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\": \"tsykul@gmail.com\", \"password\": \"123456\"}"))
        then:
            1 * authManager.authenticate(_)
            1 * tokenStorage.putToken("tsykul@gmail.com", _) >> { email, token -> generatedToken = token}
            resultActions.andExpect(status().isOk()).andExpect(content().string("{\"token\":\"$generatedToken\"}"))
    }

    def "Should be able to logout with token"() {
        given:
            def authManager = Mock(AuthenticationManager)
            def tokenStorage = Mock(TokenStorage)
            def controller = new LoginController(authManager, tokenStorage)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(post("/logout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Auth-Token", TokenUtils.createToken("tsykul@gmail.com")))
        then:
            1 * tokenStorage.removeToken("tsykul@gmail.com")
            resultActions.andExpect(status().isOk())
    }

    def "Should be able to logout without token"() {
        given:
            def authManager = Mock(AuthenticationManager)
            def tokenStorage = Mock(TokenStorage)
            def controller = new LoginController(authManager, tokenStorage)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(post("/logout")
                    .contentType(MediaType.APPLICATION_JSON))
        then:
            0 * tokenStorage.removeToken(_)
            resultActions.andExpect(status().isOk())
    }
}
