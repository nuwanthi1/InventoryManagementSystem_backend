package com.Inventory.management.Inventory.Management.System.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/homepage")
    public String allAccess() {
        return "This is Home Page.";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public String userAccess() {
        return "This is DashBoard Page.";
    }


    @GetMapping("/manage-page")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "This is Manage Page.";
    }


}