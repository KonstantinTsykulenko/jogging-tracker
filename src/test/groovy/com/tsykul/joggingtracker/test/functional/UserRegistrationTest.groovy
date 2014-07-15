package com.tsykul.joggingtracker.test.functional

import groovyx.net.http.RESTClient
import spock.lang.Specification

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
class UserRegistrationTest extends Specification {
    def "Should be able to register a user"() {
        setup:
            def appEndpoint = new RESTClient('http://localhost:9090/')
            def userData = [email: 'test@gmaiil.com', password: 'password']
            def req = [path: 'user', requestContentType: 'application/json', body: userData]

        when:
            def resp = appEndpoint.post(req)
        then:
            with(resp) {
                status == 200
                contentType == "application/json"
                data == userData
            }
        cleanup:
            appEndpoint.post([path: 'user/delete', requestContentType: 'application/json', body: userData])
    }
}