package com.tsykul.joggingtracker.controller

import com.tsykul.joggingtracker.exception.UserExistsException
import com.tsykul.joggingtracker.model.Credentials
import com.tsykul.joggingtracker.service.UserService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class UserControllerTest extends Specification {
    def "Should be able to add user"() {
        given:
            def service = Mock(UserService)
            service.saveUser(_) >> new Credentials("tsykul@gmail.com", "123456")
            def controller = new UserController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\": \"tsykul@gmail.com\", \"password\": \"123456\"}"))
        then:
            1 * service.saveUser(_)
            resultActions
                    .andExpect(status().isOk())
    }

    def "Should not be able to add invalid user"() {
        given:
            def service = Mock(UserService)
            def controller = new UserController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        expect:
            def resultActions = mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            resultActions
                    .andExpect(status().isBadRequest())
        where:
            request | _
            "{\"email\": \"tsykulgmail.com\", \"password\": \"123456\"}" | _
            "{\"email\": \"tsykul@gmail.com\", \"password\": \"12345\"}" | _
            "{\"password\": \"123456\"}" | _
            "{\"email\": \"tsykul@gmail.com\"}" | _
    }

    def "Should be able to delete user"() {
        given:
            def service = Mock(UserService)
            def controller = new UserController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(delete("/user"))
        then:
            1 * service.removeUser(_)
            resultActions
                    .andExpect(status().isOk())
    }

    def "Should not be able to add existing user"() {
        given:
            def service = Mock(UserService)
            def controller = new UserController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\": \"tsykul@gmail.com\", \"password\": \"123456\"}"))
        then:
            1 * service.saveUser(_) >> { throw new UserExistsException("exists")}
            resultActions
                    .andExpect(status().isConflict())
    }
}
