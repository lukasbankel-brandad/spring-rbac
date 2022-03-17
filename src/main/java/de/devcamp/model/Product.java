package de.devcamp.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Product {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String description;
}
