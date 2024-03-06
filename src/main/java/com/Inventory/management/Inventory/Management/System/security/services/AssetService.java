package com.Inventory.management.Inventory.Management.System.security.services;
import com.Inventory.management.Inventory.Management.System.model.Asset;
import com.Inventory.management.Inventory.Management.System.model.User;
import com.Inventory.management.Inventory.Management.System.payload.request.AssignRequest;
import com.Inventory.management.Inventory.Management.System.repository.AssetRepository;
import com.Inventory.management.Inventory.Management.System.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserRepo userRepo;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset createAsset(Asset asset) {
        if (assetRepository.existsById(asset.getAssetId())) {
            throw new RuntimeException("Asset with ID " + asset.getAssetId() + " already exists");
        }
        assetRepository.save(asset);
        return asset;
    }

    public Asset getAssetById(String assetId) {
        Optional<Asset> optionalAsset = assetRepository.findById(assetId);
        if (optionalAsset.isPresent()) {
            return optionalAsset.get();
        } else {
            throw new RuntimeException("Asset not found with id: " + assetId);
        }
    }

    public Asset updateAsset(String assetId, Asset newAsset) {
        Optional<Asset> optionalAsset = assetRepository.findById(assetId);
        if (optionalAsset.isPresent()) {
            Asset existingAsset = optionalAsset.get();
            existingAsset.setName(newAsset.getName());
            existingAsset.setType(newAsset.getType());
            return assetRepository.save(existingAsset);
        } else {
            throw new RuntimeException("Asset not found with id: " + assetId);
        }
    }

    public void deleteAsset(String assetId) {
        Optional<Asset> optionalAsset = assetRepository.findById(assetId);
        if (optionalAsset.isPresent()) {
            assetRepository.deleteById(assetId);
        } else {
            throw new RuntimeException("Asset not found with id: " + assetId);
        }
    }
    @Transactional
    public void assignAssetToUser(AssignRequest assignRequest) {
        String assetId = assignRequest.getAssetId();
        String username = assignRequest.getAssignedTo();

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found with ID: " + assetId));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        asset.setAssignedTo(username);
        assetRepository.save(asset);
    }

}