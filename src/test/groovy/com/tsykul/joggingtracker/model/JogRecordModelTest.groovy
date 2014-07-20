package com.tsykul.joggingtracker.model

import spock.lang.Specification

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class JogRecordModelTest extends Specification {
    def "Should calculate speed"() {
        given:
            def date = new Date()
        expect:
            def model = new JogRecordModel(date, dur, dist, 1)
            model.speed == speed
        where:
            dur | dist | speed
            360 | 5000 | 5
            180 | 5000 | 10
            1   | 0    | 0
            0   | 1    | Double.POSITIVE_INFINITY
    }
}
