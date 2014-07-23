package com.tsykul.joggingtracker.test.functional

import com.tsykul.joggingtracker.test.util.Integration
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
@Integration
@Stepwise
class APITest extends Specification {

    @Shared
    def appEndpoint = new RESTClient('http://localhost:9090/')
    @Shared
    def userData = [email: 'test@gmail.com', password: 'password']
    @Shared
    def token

    def setupSpec() {
        try {
            def resp = appEndpoint.post([path: 'login', requestContentType: 'application/json', body: userData])
            appEndpoint.delete([path: 'user', requestContentType: 'application/json', 'headers': ['Auth-Token': resp.data.token]])
        }
        catch (Exception e) {
            //no op
        }
    }

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

    def "Should be able to login"() {
        given:
            def loginRequest = [path: 'login', requestContentType: 'application/json', body: userData]
        when:
            def loginResponse = appEndpoint.post(loginRequest)
            token = loginResponse.data.token
        then:
            token != null
    }

    def "Should be able to add jog records"() {
        expect:
            appEndpoint.post([path: 'jogRecord', requestContentType: 'application/json', body: record, 'headers': ['Auth-Token': token]])
        where:
            record | _
            ['date': '2014-06-03', 'distance': 5000, 'duration': 240] | _
            ['date': '2014-06-05', 'distance': 4900, 'duration': 360] | _
            ['date': '2014-06-09', 'distance': 10000, 'duration': 360] | _
            ['date': '2014-06-04', 'distance': 4800, 'duration': 300] | _
            ['date': '2014-06-10', 'distance': 20000, 'duration': 360] | _
            ['date': '2014-06-16', 'distance': 5000, 'duration': 160] | _
            ['date': '2014-06-18', 'distance': 7000, 'duration': 180] | _
            ['date': '2014-06-20', 'distance': 6000, 'duration': 200] | _
            ['date': '2014-06-30', 'distance': 20000, 'duration': 720] | _
            ['date': '2014-07-01', 'distance': 40000, 'duration': 720] | _
    }

    def "Should not be able to add jog records unauthenticated"() {
        when:
            appEndpoint.post([path: 'jogRecord', requestContentType: 'application/json', body: ['date': '2014-06-03T20:44:55', 'distance': 5000, 'duration': 240]])
        then:
            HttpResponseException e = thrown()
            e.response.status == 403
    }

    def "Should be able to get report"() {
        when:
            def response = appEndpoint.get([path: 'jogRecord/report', requestContentType: 'application/json', 'headers': ['Auth-Token': token]])
        then:
            response.status == 200
            def expected = [['weekOfYear': '2014-27', 'averageTime': 720.00, 'averageDistance': 30000.00, 'averageSpeed': 15.00],
                              ['weekOfYear': '2014-25', 'averageTime': 180.00, 'averageDistance': 6000.00, 'averageSpeed': 12.00],
                              ['weekOfYear': '2014-24', 'averageTime': 360.00, 'averageDistance': 15000.00, 'averageSpeed': 15.00],
                              ['weekOfYear': '2014-23', 'averageTime': 300.00, 'averageDistance': 4900.00, 'averageSpeed': 5.88]]
            def zipped = [response.data, expected].transpose()
            zipped.forEach {
                it[0]['weekOfYear'] == it[1]['weekOfYear']
                it[0]['averageTime'] == it[1]['averageTime']
                it[0]['averageDistance'] == it[1]['averageDistance']
                it[0]['averageSpeed'] == it[1]['averageSpeed']
            }
    }

    def "Should not be able to get report unauthenticated"() {
        when:
        appEndpoint.get([path: 'jogRecord/report', requestContentType: 'application/json'])
        then:
            HttpResponseException e = thrown()
            e.response.status == 403
    }

    def "Should be able to get jog records"() {
        when:
            def response = appEndpoint.get([path: 'jogRecord', requestContentType: 'application/json', 'headers': ['Auth-Token': token]])
        then:
            response.status == 200
            response.data.size == 10
    }

    def "Should be able to filter jog records from"() {
        when:
            def response = appEndpoint.get([path: 'jogRecord',
                                            requestContentType: 'application/json',
                                            'headers': ['Auth-Token': token],
                                            'query': ['from': '2014/06/09']])
        then:
            response.status == 200
            response.data.size == 7
    }

    def "Should be able to filter jog records to"() {
        when:
            def response = appEndpoint.get([path: 'jogRecord',
                                            requestContentType: 'application/json',
                                            'headers': ['Auth-Token': token],
                                            'query': ['to': '2014/06/17']])
        then:
            response.status == 200
            response.data.size == 6
    }

    def "Should be able to filter jog records from to"() {
        when:
            def response = appEndpoint.get([path: 'jogRecord',
                                            requestContentType: 'application/json',
                                            'headers': ['Auth-Token': token],
                                            'query': ['from': '2014/06/09', 'to': '2014/06/17']])
        then:
            response.status == 200
            response.data.size == 3
    }

    def "Should not be able to get jog records unauthenticated"() {
        when:
            appEndpoint.get([path: 'jogRecord', requestContentType: 'application/json'])
        then:
            HttpResponseException e = thrown()
            e.response.status == 403
    }

    def "Should be able to delete user"() {
        when:
            def resp = appEndpoint.delete([path: 'user', requestContentType: 'application/json', 'headers': ['Auth-Token': token]])
        then:
            resp.status == 200
    }

    def "Should not be able to delete user unauthenticated"() {
        when:
        appEndpoint.delete([path: 'user', requestContentType: 'application/json'])
        then:
            HttpResponseException e = thrown()
            e.response.status == 403
    }
}