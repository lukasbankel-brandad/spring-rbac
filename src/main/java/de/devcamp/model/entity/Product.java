package de.devcamp.model.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private ObjectId id;

    @NonNull
    private String name;
    @NonNull
    private String description;
}
