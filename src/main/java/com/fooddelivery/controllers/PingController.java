package com.fooddelivery.controllers;

import com.fooddelivery.constants.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/health")
public class PingController {

    // this is for service to check the health of the system.
    @GetMapping("/ping")
    public String pingResponse(HttpServletResponse response) {
        return Constants.HEALTH_CHECK_MESSAGE;
    }
}
