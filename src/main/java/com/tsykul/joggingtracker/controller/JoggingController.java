package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.JogRecord;
import com.tsykul.joggingtracker.repository.JogRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
@RestController
public class JoggingController {

    @Autowired
    private JogRecordRepository repository;

    @RequestMapping(value = "/jogRecord",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public JogRecord addJogRecord(@RequestBody JogRecord jogRecord) {
        return repository.save(jogRecord);
    }

    @RequestMapping(value = "/jogRecord",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<JogRecord> getJogRecords() {
        return repository.findAll();
    }
}
