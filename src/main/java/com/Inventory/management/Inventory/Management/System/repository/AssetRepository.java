package com.Inventory.management.Inventory.Management.System.repository;
import com.Inventory.management.Inventory.Management.System.model.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    List<Asset> findByAssignedToIsNotNull();

    List<Asset> findByAssignedToIsNull();
}
