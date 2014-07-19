package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.JogRecord;
import com.tsykul.joggingtracker.model.JogRecordModel;
import com.tsykul.joggingtracker.model.JoggingReportModel;
import com.tsykul.joggingtracker.security.UserUtil;
import com.tsykul.joggingtracker.service.JoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public JogRecordModel addJogRecord(@RequestBody @Valid JogRecordModel jogRecord) {
        return service.saveJogRecord(jogRecord, UserUtil.getUser());
    }

    @RequestMapping(value = "/jogRecord/{recordId}",
            method = RequestMethod.DELETE)
    public void deleteRecord(@PathVariable Long recordId) {
        service.deleteRecord(UserUtil.getUser().getEmail(), recordId);
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
