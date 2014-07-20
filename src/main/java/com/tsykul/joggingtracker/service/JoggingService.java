package com.tsykul.joggingtracker.service;

import com.tsykul.joggingtracker.entity.JogRecord;
import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.model.JogRecordModel;
import com.tsykul.joggingtracker.model.JoggingReportModel;
import com.tsykul.joggingtracker.repository.JogRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
@Service
public class JoggingService {

    @Autowired
    private JogRecordRepository repository;

    @Transactional
    public JogRecordModel saveJogRecord(JogRecordModel record, User user) {

        JogRecord recordToSave = new JogRecord();
        recordToSave.setUser(user);
        recordToSave.setDate(record.getDate());
        recordToSave.setDistance(record.getDistance());
        recordToSave.setDuration(record.getDuration());

        JogRecord savedRecord = repository.save(recordToSave);

        return new JogRecordModel(savedRecord.getDate(),
                savedRecord.getDuration(),
                savedRecord.getDistance(), savedRecord.getId());
    }

    @Transactional
    public List<JogRecordModel> findByUserId(String userId) {
        List<JogRecord> jogRecords = repository.findByUserId(userId);
        return jogRecords.stream().map(record -> new JogRecordModel(record.getDate(),
                record.getDuration(),
                record.getDistance(),
                record.getId())).collect(Collectors.toList());
    }

    @Transactional
    public List<JoggingReportModel> getReport(String userId) {
        List<Object[]> weeklyUserReport = repository.getWeeklyUserReport(userId);
        return weeklyUserReport.stream().map(val ->
                new JoggingReportModel((BigDecimal ) val[0], (BigDecimal) val[1], (BigDecimal) val[2], (String) val[3])).
                collect(Collectors.toList());
    }

    @Transactional
    public void deleteRecord(String userId, Long recordId) {
        repository.deleteRecord(userId, recordId);
    }
}
