package com.Inventory.management.Inventory.Management.System.controller;
import com.Inventory.management.Inventory.Management.System.model.Asset;
import com.Inventory.management.Inventory.Management.System.payload.request.AssignRequest;
import com.Inventory.management.Inventory.Management.System.security.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssignRequest assignRequest;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAssets")
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createAsset")
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset createdAsset = assetService.createAsset(asset);
        return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/getAsset/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable String assetId) {
        Asset asset = assetService.getAssetById(assetId);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateAsset/{assetId}")
    public ResponseEntity<Asset> updateAsset(@PathVariable String assetId, @RequestBody Asset asset) {
        Asset updatedAsset = assetService.updateAsset(assetId, asset);
        return new ResponseEntity<>(updatedAsset, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteAsset/{assetId}")
    public ResponseEntity<Void> deleteAsset(@PathVariable String assetId) {
        assetService.deleteAsset(assetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> assignAssetToUser(@RequestBody AssignRequest assignRequest) {
        try {
            assetService.assignAssetToUser(assignRequest);
            return ResponseEntity.ok("Asset assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to assign asset: " + e.getMessage());
        }
    }

}