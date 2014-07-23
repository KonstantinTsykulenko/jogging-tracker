package com.tsykul.joggingtracker.service

import com.tsykul.joggingtracker.entity.JogRecord
import com.tsykul.joggingtracker.entity.User
import com.tsykul.joggingtracker.model.JogRecordModel
import com.tsykul.joggingtracker.repository.JogRecordRepository
import spock.lang.Specification

/**
 * @author KonstantinTsykulenko
 * @since 7/20/2014.
 */
class JoggingServiceTest extends Specification {
    def "Should retrieve user records"() {
        given:
            def email = "test@gmail.com"
            def repository = Mock(JogRecordRepository)
            def date = new Date()
            repository.findByUserId(email) >> [new JogRecord(date, 30, 3), new JogRecord(date, 20, 2), new JogRecord(date, 10, 1)]
            def service = new JoggingService(repository)
        when:
            def records = service.findByUserId(email, Optional.empty(), Optional.empty())
        then:
            records.size() == 3
            records[0].date == date
            records[0].distance == 30
            records[0].duration == 3
            records[1].date == date
            records[1].distance == 20
            records[1].duration == 2
            records[2].date == date
            records[2].distance == 10
            records[2].duration == 1
    }

    def "Should be able to get report"() {
        given:
            def email = "test@gmail.com"
            def repository = Mock(JogRecordRepository)
            repository.getWeeklyUserReport(email) >> [[BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "1"] as Object[],
                                                      [BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "2"] as Object[]]
            def service = new JoggingService(repository)
        when:
            def records = service.getReport(email)
        then:
            records.size() == 2
            records[0].weekOfYear == "1"
            records[1].weekOfYear == "2"
    }

    def "Should be able to add records"() {
        given:
            def repository = Mock(JogRecordRepository)
            def date = new Date()
            repository.save(_) >> new JogRecord(date, 10, 1)
            def service = new JoggingService(repository)
        when:
            def model = service.saveJogRecord(new JogRecordModel(), new User())
        then:
            model.date == date
            model.distance == 10
            model.duration == 1
    }
}
