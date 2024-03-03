package com.Inventory.management.Inventory.Management.System.repository;
import com.Inventory.management.Inventory.Management.System.model.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

}

