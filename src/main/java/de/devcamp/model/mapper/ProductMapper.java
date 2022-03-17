package de.devcamp.model.mapper;

import de.devcamp.model.dto.ProductResult;
import de.devcamp.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = ProductTransform.class )
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    ProductResult productToDto(Product product);
    Product dtoToProduct(ProductResult result);
    List<Product> mapToProduct(List<ProductResult> results);
    List<ProductResult> mapToProductResult(List<Product> products);
}
