package com.example.order.service;

import com.example.order.model.product.Product;
import com.example.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 목록
    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    // 상품 상세정보
    public Product findProductById(Long product_id) {
        return productRepository.findById(product_id).orElse(null);
    }

}
