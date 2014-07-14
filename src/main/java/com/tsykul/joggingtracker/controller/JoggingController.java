package com.tsykul.joggingtracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KonstantinTsykulenko
 * @since 7/14/2014.
 */
@RestController
public class JoggingController {

    @RequestMapping("/jogging")
    public String test() {
        return "test";
    }
}
