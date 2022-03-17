package de.devcamp.service;

import de.devcamp.model.dto.ProductResult;
import de.devcamp.model.entity.Product;
import de.devcamp.model.mapper.ProductMapper;
import de.devcamp.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public List<ProductResult> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return ProductMapper.INSTANCE.mapToProductResult(products);
    }

    public ProductResult addProduct(ProductResult result) {
        Product product = ProductMapper.INSTANCE.dtoToProduct(result);
        Product savedProduct = productRepo.save(product);
        return ProductMapper.INSTANCE.productToDto(savedProduct);
    }

    public void deleteProductById(String id) {
        Optional<Product> product = productRepo.findById(new ObjectId(id));
        if (!product.isPresent()) {
            throw new IllegalArgumentException(String.format("Product with id %d doesn't exist", id));
        }
        productRepo.delete(product.get());
    }

    public ProductResult getProduct(String id) {
        Optional<Product> product = productRepo.findById(new ObjectId(id));
        if(!product.isPresent()) {
            throw new IllegalArgumentException(String.format("Product with id %d doesn't exist", id));
        }
        return ProductMapper.INSTANCE.productToDto(product.get());
    }
}