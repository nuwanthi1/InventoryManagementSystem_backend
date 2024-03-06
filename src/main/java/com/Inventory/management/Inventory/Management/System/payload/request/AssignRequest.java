package com.Inventory.management.Inventory.Management.System.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignRequest {
    @NotBlank
    private String assetId;
    @NotBlank
    private String assignedTo;

  /*  public AssignRequest() {

    }

    public AssignRequest(String assetId, String assignedTo) {
        this.assetId = assetId;
        this.assignedTo = assignedTo;
    } */
}
