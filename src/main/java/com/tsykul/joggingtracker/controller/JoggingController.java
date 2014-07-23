package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.entity.User;
import com.tsykul.joggingtracker.model.JogRecordModel;
import com.tsykul.joggingtracker.model.JoggingReportModel;
import com.tsykul.joggingtracker.security.UserUtil;
import com.tsykul.joggingtracker.service.JoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
@RestController
public class JoggingController {

    private JoggingService service;

    @Autowired
    public JoggingController(JoggingService service) {
        this.service = service;
    }

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
        User user = UserUtil.getUser();
        service.deleteRecord(user != null ? user.getEmail() : null, recordId);
    }

    @RequestMapping(value = "/jogRecord",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<JogRecordModel> getJogRecords(@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(required = false) Date from,
                                              @DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(required = false) Date to) {
        User user = UserUtil.getUser();
        return service.findByUserId(user != null ? user.getEmail() : null,
                Optional.ofNullable(from),
                Optional.ofNullable(to));
    }

    @RequestMapping(value = "/jogRecord/report",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<JoggingReportModel> getReport() {
        User user = UserUtil.getUser();
        return service.getReport(user != null ? user.getEmail() : null);
    }
}
