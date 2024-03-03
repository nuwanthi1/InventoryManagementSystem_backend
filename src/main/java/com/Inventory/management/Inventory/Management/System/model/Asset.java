package com.Inventory.management.Inventory.Management.System.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "assets")
public class Asset {
    @Id
    private String assetId;
    private String name;
    private String type;
    private int qty;
    private String assignedTo;


}
