package com.project.delivery.services;

import com.project.delivery.dto.ProductDTO;
import com.project.delivery.entities.Product;
import com.project.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> listProduct = repository.findAllByOrderByNameAsc();
        return listProduct.stream().map(item -> new ProductDTO(item)).collect(Collectors.toList());
    }
}
