package com.tsykul.joggingtracker.test.functional

import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
@Stepwise
class APITest extends Specification {

    @Shared
    def appEndpoint = new RESTClient('http://localhost:9090/')
    @Shared
    def userData = [email: 'test6@gmaiil.com', password: 'password']

    def "Should be able to register a user"() {
        given:
            def req = [path: 'user', requestContentType: 'application/json', body: userData]
        when:
            def resp = appEndpoint.post(req)
        then:
            with(resp) {
                status == 200
                contentType == "application/json"
                data == userData
            }
    }

    def "Should be able to add jog records"() {
        given:
            def appEndpoint = new RESTClient('http://localhost:9090/')
        when:
            def loginRequest = [path: 'login', requestContentType: 'application/json', body: userData]
            def loginResponse = appEndpoint.post(loginRequest)
            def token = loginResponse.data.token
        then:
            token != null
        expect:
            appEndpoint.post([path: 'jogRecord', requestContentType: 'application/json', body: record, 'headers': ['Auth-Token': token]])
        where:
            record | _
            ['date': '2014-06-01T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-03T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-04T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-08T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-09T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-16T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-18T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-20T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-28T20:44:55', 'distance': 150, 'duration': 15] | _
            ['date': '2014-06-30T20:44:55', 'distance': 150, 'duration': 15] | _
    }

    def cleanupSpec() {
        appEndpoint.post([path: 'user/delete', requestContentType: 'application/json', body: userData])
    }
}