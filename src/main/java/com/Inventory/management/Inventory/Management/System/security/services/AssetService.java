package com.Inventory.management.Inventory.Management.System.security.services;
import com.Inventory.management.Inventory.Management.System.model.Asset;
import com.Inventory.management.Inventory.Management.System.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public Asset getAssetById(String id) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        if (optionalAsset.isPresent()) {
            return optionalAsset.get();
        } else {
            throw new RuntimeException("Asset not found with id: " + id);
        }
    }

    public Asset updateAsset(String id, Asset newAsset) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        if (optionalAsset.isPresent()) {
            Asset existingAsset = optionalAsset.get();
            existingAsset.setName(newAsset.getName());
            existingAsset.setType(newAsset.getType());
            return assetRepository.save(existingAsset);
        } else {
            throw new RuntimeException("Asset not found with id: " + id);
        }
    }

    public void deleteAsset(String id) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        if (optionalAsset.isPresent()) {
            assetRepository.deleteById(id);
        } else {
            throw new RuntimeException("Asset not found with id: " + id);
        }
    }

    
}
