package com.tsykul.joggingtracker.test.functional

import com.tsykul.joggingtracker.test.util.Integration
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
            ['date': '2014-06-03T20:44:55', 'distance': 5000, 'duration': 240] | _
            ['date': '2014-06-04T20:44:55', 'distance': 4800, 'duration': 300] | _
            ['date': '2014-06-05T20:44:55', 'distance': 4900, 'duration': 360] | _
            ['date': '2014-06-09T20:44:55', 'distance': 10000, 'duration': 360] | _
            ['date': '2014-06-10T20:44:55', 'distance': 20000, 'duration': 360] | _
            ['date': '2014-06-16T20:44:55', 'distance': 5000, 'duration': 160] | _
            ['date': '2014-06-18T20:44:55', 'distance': 7000, 'duration': 180] | _
            ['date': '2014-06-20T20:44:55', 'distance': 6000, 'duration': 200] | _
            ['date': '2014-06-30T20:44:55', 'distance': 20000, 'duration': 720] | _
            ['date': '2014-07-01T20:44:55', 'distance': 40000, 'duration': 720] | _
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

    def "Should be able to get jog records"() {
        when:
            def response = appEndpoint.get([path: 'jogRecord', requestContentType: 'application/json', 'headers': ['Auth-Token': token]])
        then:
            response.status == 200
            def expected = [['duration': 720, 'date': '2014/07/01', 'distance': 40000, 'id': 422, 'speed': 20.00],
                              ['duration': 720, 'date': '2014/06/30', 'distance': 20000, 'id': 421, 'speed': 10.00],
                              ['duration': 200, 'date': '2014/06/20', 'distance': 6000, 'id': 420, 'speed': 10.80],
                              ['duration': 180, 'date': '2014/06/18', 'distance': 7000, 'id': 419, 'speed': 14.00],
                              ['duration': 160, 'date': '2014/06/16', 'distance': 5000, 'id': 418, 'speed': 11.25],
                              ['duration': 360, 'date': '2014/06/10', 'distance': 20000, 'id': 417, 'speed': 20.00],
                              ['duration': 360, 'date': '2014/06/09', 'distance': 10000, 'id': 416, 'speed': 10.00],
                              ['duration': 360, 'date': '2014/06/05', 'distance': 4900, 'id': 415, 'speed': 4.90],
                              ['duration': 300, 'date': '2014/06/04', 'distance': 4800, 'id': 414, 'speed': 5.76],
                              ['duration': 240, 'date': '2014/06/03', 'distance': 5000, 'id': 413, 'speed': 7.50]]
            def zipped = [response.data, expected].transpose()
            zipped.forEach {
                it[0]['duration'] == it[1]['duration']
                it[0]['date'] == it[1]['date']
                it[0]['distance'] == it[1]['distance']
                it[0]['speed'] == it[1]['speed']
            }
    }

    def "Should be able to delete user"() {
        when:
            def resp = appEndpoint.delete([path: 'user', requestContentType: 'application/json', 'headers': ['Auth-Token': token]])
        then:
            resp.status == 200
    }
}