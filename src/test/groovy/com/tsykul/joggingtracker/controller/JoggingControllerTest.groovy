package com.tsykul.joggingtracker.controller

import com.tsykul.joggingtracker.model.JogRecordModel
import com.tsykul.joggingtracker.service.JoggingService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class JoggingControllerTest extends Specification {
    def "Should be able to add record"() {
        given:
            def service = Mock(JoggingService)
            service.saveJogRecord(_, _) >> new JogRecordModel(new Date(2014, 6, 10), 100, 1000, 1)
            def controller = new JoggingController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(post("/jogRecord")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"date\": \"2009-04-12T20:44:55\", \"distance\": 150, \"duration\": 15}"))
        then:
            1 * service.saveJogRecord(_, _)
            resultActions
                    .andExpect(status().isOk())
    }

    def "Should not able to add invalid record"() {
        given:
            def service = Mock(JoggingService)
            service.saveJogRecord(_, _) >> new JogRecordModel(new Date(2014, 6, 10), 100, 1000, 1)
            def controller = new JoggingController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        expect:
            def resultActions = mockMvc.perform(post("/jogRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            resultActions
                    .andExpect(status().isBadRequest())
        where:
            request | _
            "{\"date\": \"2009-04-12T20:44:55\", \"distance\": 0, \"duration\": 15}" | _
            "{\"date\": \"2009-04-12T20:44:55\", \"distance\": 150, \"duration\": 0}" | _
            "{\"distance\": 150, \"duration\": 15}" | _
            "{\"date\": \"2009-04-12T20:44:55\", \"distance\": 150}" | _
            "{\"date\": \"2009-04-12T20:44:55\", \"duration\": 15}" | _
    }

    def "Should be able to delete record"() {
        given:
            def service = Mock(JoggingService)
            def controller = new JoggingController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(delete("/jogRecord/1")
                    .contentType(MediaType.APPLICATION_JSON))
        then:
            1 * service.deleteRecord(_, 1)
            resultActions
                    .andExpect(status().isOk())
    }

    def "Should be able to get records"() {
        given:
            def service = Mock(JoggingService)
            def controller = new JoggingController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(get("/jogRecord"))
        then:
            1 * service.findByUserId(_, _, _)
            resultActions
                    .andExpect(status().isOk())
    }

    def "Should be able to get report"() {
        given:
            def service = Mock(JoggingService)
            def controller = new JoggingController(service)
            def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when:
            def resultActions = mockMvc.perform(get("/jogRecord/report"))
        then:
            1 * service.getReport(_)
            resultActions
                    .andExpect(status().isOk())
    }
}
