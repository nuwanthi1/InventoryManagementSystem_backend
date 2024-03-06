package com.Inventory.management.Inventory.Management.System.controller;
import com.Inventory.management.Inventory.Management.System.security.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/total-assets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getTotalAssetsData() {
        Map<String, Integer> totalAssetsData = dashboardService.getTotalAssetsData();
        return ResponseEntity.ok(totalAssetsData);
    }

    @GetMapping("/assigned-assets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getAssignedAssetsData() {
        Map<String, Integer> assignedAssetsData = dashboardService.getAssignedAssetsData();
        return ResponseEntity.ok(assignedAssetsData);
    }

    @GetMapping("/free-assets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getFreeAssetsData() {
        Map<String, Integer> freeAssetsData = dashboardService.getFreeAssetsData();
        return ResponseEntity.ok(freeAssetsData);
    }
}
