package com.tsykul.joggingtracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KonstantinTsykulenko
 * @since 7/13/2014.
 */
@RestController
public class TestPageController {

    @RequestMapping("/")
    public String index() {
        return "Jogging Tracker is running";
    }

}