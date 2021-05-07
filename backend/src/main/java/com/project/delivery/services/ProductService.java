package com.project.delivery.services;

import com.project.delivery.dto.ProductDTO;
import com.project.delivery.entities.Product;
import com.project.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    public ProductDTO findOne(long id) {
        Optional<Product> product = repository.findById(id);
        return new ProductDTO(product.get());
    }

    @Transactional(readOnly = false)
    public ProductDTO insertOne(ProductDTO productDTO) throws Exception {
        List<Product> productList = repository.findByName(productDTO.getName());
        if (productList.size() != 0) throw new Exception("Já existe um produto com esse mesmo nome.");
        Product product = new Product(null, productDTO.getName(), productDTO.getDescription(), productDTO.getImageUri(), productDTO.getPrice());

        Product save = repository.save(product);
        return new ProductDTO(save);
    }
}
