package de.devcamp.model.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ProductResult {
    private String id;
    private String name;
    private String description;
}
