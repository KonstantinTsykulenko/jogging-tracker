package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.JogRecord;
import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.model.JogRecordModel;
import com.tsykul.joggingtracker.model.JoggingReportModel;
import com.tsykul.joggingtracker.security.UserUtil;
import com.tsykul.joggingtracker.service.JoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private JoggingService service;

    @RequestMapping(value = "/jogRecord",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public JogRecordModel addJogRecord(@RequestBody JogRecord jogRecord) {
        jogRecord.setUser(UserUtil.getUser());
        return service.saveJogRecord(jogRecord);
    }

    @RequestMapping(value = "/jogRecord",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<JogRecordModel> getJogRecords() {
        return service.findByUserId(UserUtil.getUser().getEmail());
    }

    @RequestMapping(value = "/jogRecord/report",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<JoggingReportModel> getReport() {
        return service.getReport(UserUtil.getUser().getEmail());
    }
}
