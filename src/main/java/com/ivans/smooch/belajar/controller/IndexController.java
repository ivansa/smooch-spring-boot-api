package com.ivans.smooch.belajar.controller;

import com.ivans.smooch.belajar.service.SmoochServiceApi;
import io.smooch.client.model.AppUserPreCreate;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ivans
 */
@RestController
public class IndexController {

    @Autowired
    private SmoochServiceApi smoochServiceApi;

    @GetMapping("/appDetail")
    public Object getAppDetail() {
        return smoochServiceApi.getApp();
    }

    @PostMapping("/createUser")
    public Object createNewUser(
            @RequestParam String email,
            @RequestParam String givenName,
            @RequestParam String surname) {
        String userId = UUID.randomUUID().toString().replaceAll("-", "");
        
        AppUserPreCreate body = new AppUserPreCreate(); // AppUserPreCreate | Body for a preCreateAppUser request.
        body.setEmail(email);
        body.setGivenName(givenName);
        body.setSurname(surname);
        body.setSignedUpAt("AppBelajar");
        
        // Required
        body.setUserId(userId);
        
        return smoochServiceApi.createUser(body);
    }
    
    @GetMapping("/sendMessage")
    public Object sendMessage() {
        return smoochServiceApi.sendMessage();
    }
    
    @GetMapping("/getMessage")
    public Object getMessage() {
        return smoochServiceApi.getMessage();
    }
}
