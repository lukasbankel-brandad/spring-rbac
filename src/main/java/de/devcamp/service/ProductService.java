package de.devcamp.service;

import de.devcamp.model.dto.ProductResult;
import de.devcamp.model.entity.Product;
import de.devcamp.model.mapper.ProductMapper;
import de.devcamp.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return ProductMapper.INSTANCE.productToDto(product.get());
    }
}