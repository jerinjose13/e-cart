package com.hoiii.e_Cart.service;

import com.hoiii.e_Cart.entity.Product;
import com.hoiii.e_Cart.exception.ProductNotFoundException;
import com.hoiii.e_Cart.repository.ProductRepository;
import com.hoiii.e_Cart.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<Product> searchProducts(String name, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasMinPrice(minPrice))
                .and(ProductSpecification.hasMaxPrice(maxPrice));

        return productRepository.findAll(spec, pageable);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
            return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Integer id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found")));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product product_details) {

        return productRepository.findById(id).map(product -> {
            product.setName(product_details.getName());
            product.setPrice(product_details.getPrice());
            product.setCategory(product_details.getCategory());
            product.setStock(product_details.getStock());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));

    }

    public void deleteProduct(int id) {

        productRepository.deleteById(id);
    }


}
